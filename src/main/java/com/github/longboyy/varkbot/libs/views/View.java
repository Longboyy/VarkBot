package com.github.longboyy.varkbot.libs.views;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

public class View {
	/*
	 * A `View` is a way for users to interact with a MessageEmbed
	 */
	
	private MessageEmbed embed = null;
	
	private Message message = null;
	
	private List<ViewButton> buttons = new ArrayList<>();
	
	public Message getMessage() {
		return message;
	}
	
	public List<ViewButton> getButtons() {
		return buttons;
	}
	
	public void addButton(ViewButton viewButton) {
		buttons.add(viewButton);
	}
	
	public void removeButton(String unicodeEmoji) {
		for(ViewButton button : buttons) {
			if(button.getEmoji().equalsIgnoreCase(unicodeEmoji)) {
				buttons.remove(button);
				break;
			}
		}
	}
	
	public void setEmbed(MessageEmbed embed) {
		this.embed = embed;
	}
	
	public MessageEmbed getEmbed() {
		return embed;
	}
	
	public void construct(TextChannel channel) {
		if(message != null) {
			this.destruct();
		}
		
		channel.sendMessage(embed).queue((msg) -> {
			for(ViewButton button : buttons) {
				msg.addReaction(button.getEmoji()).queue();
			}
			message = msg;
		});
	}
	
	public void destruct() {
		message.delete().queue();
		message = null;
	}

	
	public void updateView() {
		if(message == null || embed == null) {
			return;
		}
		
		message.editMessage(embed).queue();
	}
}
