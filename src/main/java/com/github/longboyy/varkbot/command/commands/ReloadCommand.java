package com.github.longboyy.varkbot.command.commands;

import com.github.longboyy.varkbot.VarkBot;
import com.github.longboyy.varkbot.command.Command;
import com.github.longboyy.varkbot.plugin.VarkBotPlugin;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class ReloadCommand extends Command {
	
	public ReloadCommand() {
		super("reload", 0, 0);
	}

	@Override
	public String getUsage() {
		return "reload";
	}

	@Override
	public String getDescription() {
		return "Reloads the config for all running plugins.";
	}

	@Override
	public void execute(VarkBot varkBot, TextChannel channel, Member sender, String[] args) {
		
		for(VarkBotPlugin plugin : varkBot.getPluginManager().getRunningPlugins()) {
			plugin.getConfig().reloadConfig();
		}
		
	}

	@Override
	public boolean hasPermission(Member sender) {
		return sender == null;
	}
	
}
