package com.github.longboyy.varkbot.command.commands;

import com.github.longboyy.varkbot.VarkBot;
import com.github.longboyy.varkbot.command.Command;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class DebugCommand extends Command {

	public DebugCommand() {
		super("debug", 0, 0);
	}

	@Override
	public String getUsage() {
		return "debug";
	}

	@Override
	public String getDescription() {
		return "Does dumb stuff";
	}

	@Override
	public void execute(VarkBot varkBot, TextChannel channel, Member sender, String[] args) {
		if(channel != null && sender != null) {
			channel.sendMessage("Shiver me tendies").queue();
		}
		
		varkBot.getLogger().info("Shiver me tendies");
	}

	@Override
	public boolean hasPermission(Member sender) {
		return true;
	}

}
