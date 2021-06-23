package com.github.longboyy.varkbot.config;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.github.longboyy.varkbot.libs.yaml.ConfigSection;
import com.github.longboyy.varkbot.libs.yaml.config.YAMLFileConfig;

public class DefaultConfig extends YAMLFileConfig {

	public DefaultConfig(Logger logger, File file, String defaultConfigPath) {
		super(logger, file, defaultConfigPath);
	}
	
	public String getDiscordToken() {
		ConfigSection section = config.getConfigSection("discord");
		return section.getString("token");
	}
	
	public String getDiscordGame() {
		ConfigSection section = config.getConfigSection("discord");
		return section.getString("game");
	}
	
	public String getDBUser() {
		ConfigSection section = config.getConfigSection("database");
		return section.getString("user");
	}
	
	public String getDBPass() {
		ConfigSection section = config.getConfigSection("database");
		return section.getString("pass");
	}
	
	public String getDBHost() {
		ConfigSection section = config.getConfigSection("database");
		return section.getString("host");
	}
	
	public String getDBName() {
		ConfigSection section = config.getConfigSection("database");
		return section.getString("databaseName");
	}
	
	public int getDBPort() {
		ConfigSection section = config.getConfigSection("database");
		return section.getInt("port");
	}

}
