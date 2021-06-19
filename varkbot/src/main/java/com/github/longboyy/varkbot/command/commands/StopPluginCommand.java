package com.github.longboyy.varkbot.command.commands;

import com.github.longboyy.varkbot.VarkBot;
import com.github.longboyy.varkbot.command.Command;
import com.github.longboyy.varkbot.plugin.VarkBotPlugin;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class StopPluginCommand extends Command {

	public StopPluginCommand() {
		super("stopplugin", 1, 1);
	}

	@Override
	public String getUsage() {
		return "stopplugin [plugin_name]";
	}

	@Override
	public String getDescription() {
		return "Stops a plugin with the given name";
	}

	@Override
	public void execute(VarkBot varkBot, TextChannel channel, Member sender, String[] args) {
		String pluginName = args[0].toLowerCase();
		
		//varkBot.getLogger().info("Attempting to load " + pluginName);
		
		if(varkBot.getPluginManager().getPlugin(pluginName) != null) {
			boolean success = varkBot.getPluginManager().stopPlugin(pluginName);
			if(success) {
				varkBot.getLogger().info("Stopped " + pluginName);
			}else {
				varkBot.getLogger().info("Failed to stop " + pluginName);
			}
		}
		
	}

	@Override
	public boolean hasPermission(Member sender) {
		return sender == null;
	}

}