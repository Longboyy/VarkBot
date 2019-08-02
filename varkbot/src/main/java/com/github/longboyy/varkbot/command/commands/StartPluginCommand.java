package com.github.longboyy.varkbot.command.commands;

import java.util.List;

import com.github.longboyy.varkbot.VarkBot;
import com.github.longboyy.varkbot.command.Command;
import com.github.longboyy.varkbot.plugin.VarkBotPlugin;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class StartPluginCommand extends Command {

	public StartPluginCommand() {
		super("startplugin", 1, 1);
	}

	@Override
	public String getUsage() {
		return "startplugin [plugin_name]";
	}

	@Override
	public String getDescription() {
		return "Starts a plugin with the given name";
	}

	@Override
	public void execute(VarkBot varkBot, TextChannel channel, Member sender, String[] args) {
		String pluginName = args[0].toLowerCase();
		
		//varkBot.getLogger().info("Attempting to load " + pluginName);
		
		if(varkBot.getPluginManager().getPlugin(pluginName) != null) {
			VarkBotPlugin plugin = varkBot.getPluginManager().executePlugin(pluginName);
			if(plugin != null) {
				varkBot.getLogger().info("Loaded " + plugin.getName() + " " + plugin.getVersion());
			}
		}
		
	}

	@Override
	public boolean hasPermission(Member sender) {
		return sender == null;
	}

}
