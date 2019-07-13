package com.github.longboyy.varkbot.command;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.github.longboyy.varkbot.VarkBot;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandHandler {

	public static final String COMMAND_CHAR = ";";
	
	private Logger logger;
	private VarkBot varkBot;
	
	private Map<String, Command> commands = new HashMap<>();
	
	public CommandHandler(VarkBot varkBot) {
		this.logger = varkBot.getLogger();
		this.varkBot = varkBot;
	}
	
	public synchronized void registerCommand(Command command) {
		String identifier = command.getIdentifer();
		commands.put(identifier, command);
	}
	
	public synchronized void unregisterCommand(Command command) {
		String identifier = command.getIdentifer();
		if(commands.containsKey(identifier)) {
			commands.remove(identifier);
		}
	}
	
	public synchronized void handle(TextChannel channel, Member sender, String message) {
		if(message == null) {
			return;
		}
		
		if(message.length() == 0) {
			return;
		}
		
		String[] parts = message.split(" ");
		String cmd = parts[0];
		String[] args = parts.length > 1 ? Arrays.copyOfRange(parts, 1, parts.length) : new String[0];
		
		if(sender != null) {
			if(!parts[0].substring(0, 1).equals(COMMAND_CHAR)) {
				return;
			}
			
			parts[0] = parts[0].substring(1, parts[0].length());
			cmd = parts[0];
		}
		
		if(!commands.containsKey(cmd)) {
			return;
		}
		
		Command command = commands.get(cmd);
		
		if(args.length < command.minArgs() || args.length > command.maxArgs()) {
			logger.info("Incorrect number of args");
			if(channel != null) {
				channel.sendMessage("Usage:\n_"+command.getUsage()+" - "+command.getDescription()+"_").queue();
			}
			return;
		}
		
		if(sender != null && channel != null && !command.hasPermission(sender)) {
			channel.sendMessage(sender.getAsMention() + " **You don't have permission to use this command.**").queue();
			return;
		}
		
		command.execute(varkBot, channel, sender, args);
	}
	
	public synchronized Collection<Command> getRegisteredCommands() {
		return commands.values();
	}
	
}
