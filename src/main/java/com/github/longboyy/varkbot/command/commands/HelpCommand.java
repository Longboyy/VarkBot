package com.github.longboyy.varkbot.command.commands;

import java.awt.Color;
import java.util.Collection;

import com.github.longboyy.varkbot.VarkBot;
import com.github.longboyy.varkbot.command.Command;
import com.github.longboyy.varkbot.command.CommandHandler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class HelpCommand extends Command {

	public HelpCommand() {
		super("help", 0, 0);
	}

	@Override
	public String getUsage() {
		return "help";
	}
	
	@Override
	public String getDescription() {
		return "Displays help for all commands.";
	}

	@Override
	public void execute(VarkBot varkBot, TextChannel channel, Member sender, String[] args) {
		Collection<Command> commands = varkBot.getCommandHandler().getRegisteredCommands();
		
		if(sender != null) {
			if(sender.getUser().isBot()) {
				return;
			}
			
			EmbedBuilder builder = new EmbedBuilder();
			builder.setTitle("Commands");
			for(Command command : commands) {
				if(command.hasPermission(sender)) {
					builder.addField(CommandHandler.COMMAND_CHAR+command.getUsage(), command.getDescription(), false);
				}
			}
			builder.setColor(Color.CYAN);
			channel.sendMessage(builder.build()).queue();
		}else {
			varkBot.getLogger().info("Commands:");
			for(Command command : commands) {
				if(command.hasPermission(null)) {
					varkBot.getLogger().info(command.getUsage() + " - " + command.getDescription());
				}
			}
		}
		
	}

	@Override
	public boolean hasPermission(Member sender) {
		return true;
	}

}
