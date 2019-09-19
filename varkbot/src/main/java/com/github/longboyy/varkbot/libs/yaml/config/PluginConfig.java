package com.github.longboyy.varkbot.libs.yaml.config;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.github.longboyy.varkbot.libs.yaml.ConfigSection;

public abstract class PluginConfig extends YAMLFileConfig {

	public PluginConfig(Logger logger, File file) {
		super(logger, file);
	}
	
	public PluginConfig(Logger logger, File file, String defaultPath) {
		super(logger, file, defaultPath);
		if(defaultPath != null && !defaultPath.isEmpty() && !this.configFile.exists()) {
			this.saveDefaultConfig();
			this.reloadConfig();
		}
	}

	public ConfigSection getConfig() {
		return config;
	}
	
	@Override
	public void reloadConfig() {
		super.reloadConfig();
		this.setup();
	}
	
	public abstract void setup();
}
