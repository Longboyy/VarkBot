package com.github.longboyy.varkbot.libs.views;

import java.util.ArrayList;
import java.util.List;

import com.github.longboyy.varkbot.VarkBot;

import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ViewHandler extends ListenerAdapter {
	
	private VarkBot varkBot;
	
	private List<View> views = new ArrayList<>();
	
	public ViewHandler(VarkBot varkBot) {
		this.varkBot = varkBot;
		varkBot.getJDA().addEventListener(this);
	}
	
	public void addView(View view) {
		views.add(view);
		varkBot.getLogger().info("View added | " + views.size());
	}
	
	public void removeView(View view) {
		views.remove(view);
		view.destruct();
	}
	
	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
		if(event.getUser().isBot()) {
			return;
		}
		
		View view = null;
		
		for(View v : views) {
			//varkBot.getLogger().info(v.getMessage().getId() + " | " + event.getMessageId());
			
			if(v.getMessage().getId().equalsIgnoreCase(event.getMessageId())) {
				view = v;
				break;
			}
		}
		
		if(view == null) {
			return;
		}
		
		for(ViewButton button : view.getButtons()) {
			if(button.getEmoji().equalsIgnoreCase(event.getReactionEmote().getName())) {
				if(button.hasPermission(event.getMember())) {
					button.execute(event.getMember());
				}
			}
		}
		
		event.getReaction().removeReaction(event.getUser()).queue();
	}

}
