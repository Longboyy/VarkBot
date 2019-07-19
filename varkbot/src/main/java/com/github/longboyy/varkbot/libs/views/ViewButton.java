package com.github.longboyy.varkbot.libs.views;

import net.dv8tion.jda.core.entities.Member;

public abstract class ViewButton {

	private String unicodeEmoji;
	
	public ViewButton(String unicodeEmoji) {
		this.unicodeEmoji = unicodeEmoji;
	}
	
	public String getEmoji() {
		return unicodeEmoji;
	}
	
	public abstract void execute(Member member);
	
	public abstract boolean hasPermission(Member member);
	
}
