package com.github.longboyy.varkbot.plugin;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.github.longboyy.varkbot.VarkBot;
import com.github.longboyy.varkbot.libs.yaml.config.PluginConfig;

/**
 * A parent class for all plugins which intend to use the Angelia API.
 * Additionally every plugin needs to have the {@link AngeliaLoad} annotation.
 * Specifying a constructor for your plugin class is not allowed, just leave the
 * default constructor alone
 *
 */
public abstract class VarkBotPlugin<T extends PluginConfig> {

	protected boolean running = false;
	protected boolean finished = false;
	protected VarkBot varkBot;
	protected T config;
	private File dataFolder;

	protected VarkBotPlugin() {
	}

	/**
	 * @return This plugins YAML config file
	 */
	public T getConfig() {
		return config;
	}
	
	/**
	 * @return VarkBot instance
	 */
	public VarkBot getVarkBot() {
		return varkBot;
	}

	/**
	 * @return Folder in which this plugins data (like its YAML config) is kept
	 */
	public File getDataFolder() {
		return dataFolder;
	}

	/**
	 * @return Optional description of the plugin as specified in its annotation
	 */
	public String getDescription() {
		VarkBotLoad pluginAnnotation = getPluginAnnotation();
		if (pluginAnnotation == null) {
			return null;
		}
		return pluginAnnotation.description();
	}

	/**
	 * @return Unique identifying name
	 */
	public String getName() {
		VarkBotLoad pluginAnnotation = getPluginAnnotation();
		if (pluginAnnotation == null) {
			return null;
		}
		return pluginAnnotation.name();
	}

	private VarkBotLoad getPluginAnnotation() {
		Class<? extends VarkBotPlugin> pluginClass = this.getClass();
		return pluginClass.getAnnotation(VarkBotLoad.class);
	}

	/**
	 * @return Version of the plugin
	 */
	public String getVersion() {
		VarkBotLoad pluginAnnotation = getPluginAnnotation();
		if (pluginAnnotation == null) {
			return null;
		}
		return pluginAnnotation.version();
	}

	/**
	 * @return Whether execution of this plugin is completly done
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Checks whether the plugin is actively running. If the plugin is not running,
	 * it may either be paused or completly finished.
	 * 
	 * @return Whether this plugin is actively running
	 */
	public boolean isRunning() {
		return running;
	}

	void loadConfig() {
		config.reloadConfig();
	}
	
	void setVarkBot(VarkBot varkBot) {
		String name = getName();
		if(name == null) {
			throw new IllegalStateException("Plugin was initialized without valid name");
		}
		this.varkBot = varkBot;
		this.dataFolder = new File("plugins", name);
		this.config = createConfig(varkBot.getLogger(), new File(dataFolder, "config.yml"));
	}

	/**
	 * Sets whether this plugin is actively running
	 * 
	 * @param running New running state
	 */
	void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * Called automatically when the plugin is started, put all your setup in here.
	 * Assume that all your parameters and the YAML config are already fully loaded
	 * at this point
	 */
	public abstract void start();

	/**
	 * Called automatically when the plugin is stopped, do any eventual tear down in this method
	 */
	public abstract void stop();
	
	protected abstract T createConfig(Logger logger, File file);
}
