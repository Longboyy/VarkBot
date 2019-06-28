package com.github.longboyy.varkbot.command;

import com.github.longboyy.varkbot.VarkBot;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public abstract class Command {
	protected String identifier;
	protected int minArgs;
	protected int maxArgs;
	
	public Command(String identifier, int minArgs, int maxArgs) {
		this.identifier = identifier;
		this.minArgs = minArgs;
		this.maxArgs = maxArgs;
	}
	
	public String getIdentifer() {
		return identifier;
	}
	
	public int minArgs() {
		return minArgs;
	}
	
	public int maxArgs() {
		return maxArgs;
	}
	
	public abstract String getUsage();
	
	public abstract String getDescription();
	
	public abstract void execute(VarkBot varkBot, TextChannel channel, Member sender, String[] args);
	
	public abstract boolean hasPermission(Member sender);
}
