package com.github.longboyy.varkbot.libs.yaml.config;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.github.longboyy.varkbot.libs.yaml.ConfigSection;

public class PluginConfig extends YAMLFileConfig {

	public PluginConfig(Logger logger, File file) {
		super(logger, file);
	}

	public ConfigSection getConfig() {
		return config;
	}
}
