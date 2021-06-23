package com.github.longboyy.varkbot.plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.longboyy.varkbot.VarkBot;

public class PluginManager {
	
	private VarkBot varkBot;
	
	private PluginService pluginService;
	private Map<String, VarkBotPlugin> plugins;
	private List<VarkBotPlugin> runningPlugins;

	public PluginManager(VarkBot varkBot) {
		this.varkBot = varkBot;
		
		this.plugins = new HashMap<String, VarkBotPlugin>();
		this.runningPlugins = new LinkedList<VarkBotPlugin>();
		this.pluginService = PluginService.getInstance(varkBot.getLogger());
		reloadPlugins();
		
		
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		    	shutDown();
		    }
		});
	}

	/**
	 * Starts a plugin and returns the plugin started
	 *
	 * @param pluginName Name of the plugin to start
	 * @return Created plugin instance
	 */
	public VarkBotPlugin executePlugin(String pluginName) {
		VarkBotPlugin plugin = getPlugin(pluginName);
		if (plugin == null) {
			varkBot.getLogger().warn("Plugin " + pluginName + " did not exist");
			return null;
		}
		// create new instance via reflection as we dont want to hand our original out
		try {
			plugin = (VarkBotPlugin) (plugin.getClass().getConstructors()[0].newInstance());
			plugin.setVarkBot(varkBot);
			plugin.loadConfig();
			plugin.setRunning(true);
			try {
				plugin.start();
			} catch (Exception e) {
				// user code is scary
				varkBot.getLogger().warn("Failed to start plugin", e);
				return null;
			}
			if (plugin.isFinished()) {
				// may have disabled itself already in the start method, in this case it must
				// report errors itself
				return null;
			}
			runningPlugins.add(plugin);
			return plugin;
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
			varkBot.getLogger().error("Failed to reinstantiate plugin " + plugin.getName(), e);
			return null;
		}
	}

	public synchronized List<String> getAvailablePlugins() {
		// map values are all lower case, so we use plugin.getName() instead to get
		// proper names
		List<String> pluginNames = new LinkedList<String>();
		for (VarkBotPlugin plugin : plugins.values()) {
			pluginNames.add(plugin.getName());
		}
		return pluginNames;
	}

	public VarkBotPlugin getPlugin(String name) {
		return plugins.get(name.toLowerCase());
	}
	
	public List<VarkBotPlugin> getRunningPlugins() {
		return new ArrayList<>(runningPlugins);
	}

	private void registerPlugin(VarkBotPlugin plugin) {
		
		Class<? extends VarkBotPlugin> pluginClass = plugin.getClass();
		VarkBotLoad pluginAnnotation = pluginClass.getAnnotation(VarkBotLoad.class);
		if (pluginAnnotation == null) {
			varkBot.getLogger()
					.warn("Plugin " + plugin.getClass().getName() + " had no VarkBotLoad annotation, it was ignored");
			return;
		}
		Constructor<?> constr = pluginClass.getConstructors()[0];
		if (constr.getParameterCount() != 0) {
			varkBot.getLogger()
					.warn("Plugin " + plugin.getClass().getName() + " had no default constructor, it was ignored");
			return;
		}
		constr.setAccessible(true);
		String name = pluginAnnotation.name();
		if (plugins.containsKey(name.toLowerCase())) {
			varkBot.getLogger().warn("Plugin " + name + " was already registered, did not register again");
			return;
		}
		varkBot.getLogger().info("Registering plugin " + name);
		plugins.put(name.toLowerCase(), plugin);
	}

	public void reloadPlugins() {
		pluginService.reloadJars();
		for (VarkBotPlugin plugin : pluginService.getAvailablePlugins()) {
			registerPlugin(plugin);
		}
		varkBot.getLogger().info("Loaded a total of " + plugins.size() + " plugin(s)");
	}

	/**
	 * Stops all plugins
	 */
	public void shutDown() {
		while (!runningPlugins.isEmpty()) {
			stopPlugin(runningPlugins.get(0));
		}
	}

	/**
	 * Shuts down the plugin with the given name
	 *
	 * @param name Name of the plugin to stop
	 * @return Whether a plugin was stopped
	 */
	public boolean stopPlugin(String name) {
		name = name.toLowerCase();
		Iterator<VarkBotPlugin> iterator = runningPlugins.iterator();
		while (iterator.hasNext()) {
			VarkBotPlugin plugin = iterator.next();
			if (plugin.getName().toLowerCase().equals(name)) {
				stopPlugin(plugin);
				return true;
			}
		}
		return false;
	}

	private void stopPlugin(VarkBotPlugin plugin) {
		varkBot.getLogger().info("Stopping plugin " + plugin.getName());
		try {
			plugin.stop();
		} catch (Exception e) {
			varkBot.getLogger().warn("Failed to stop plugin", e);
		}
		runningPlugins.remove(plugin);
	}
}
