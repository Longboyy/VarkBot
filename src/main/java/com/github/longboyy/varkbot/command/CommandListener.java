package com.github.longboyy.varkbot.command;

import org.apache.logging.log4j.Logger;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class CommandListener extends ListenerAdapter {

	private Logger logger;
	private CommandHandler cmdHandler;
	
	public CommandListener(Logger logger, CommandHandler cmdHandler) {
		this.logger = logger;
		this.cmdHandler = cmdHandler;
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if(event.isWebhookMessage() || event.getAuthor().isBot()) {
			return;
		}
		
		cmdHandler.handle(event.getChannel(), event.getMember(), event.getMessage().getContentRaw());
	}
	
}
