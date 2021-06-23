package com.github.longboyy.varkbot.command.commands;

import com.github.longboyy.varkbot.VarkBot;
import com.github.longboyy.varkbot.command.Command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;

import javax.swing.*;

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
			//channel.sendMessage("Shiver me tendies").queue();
			EmbedBuilder b = new EmbedBuilder();

			b.setTitle("Test?");
			b.addField("Test Field", "Hello there", false);

			ActionRow row1 = ActionRow.of(
					Button.primary("testButton1", "Button 1")
			);
			ActionRow row2 = ActionRow.of(
					Button.danger("testButton2", "Button 2").asDisabled()
			);

			channel.sendMessageEmbeds(b.build()).setActionRows(row1, row2).queue();
		}
		
		varkBot.getLogger().info("Shiver me tendies");
		
	}

	@Override
	public boolean hasPermission(Member sender) {
		return true;
	}

}
