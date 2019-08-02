package com.github.longboyy.varkbot;

import java.io.File;

import javax.security.auth.login.LoginException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.longboyy.varkbot.command.CommandHandler;
import com.github.longboyy.varkbot.command.CommandLineReader;
import com.github.longboyy.varkbot.command.CommandListener;
import com.github.longboyy.varkbot.command.commands.HelpCommand;
import com.github.longboyy.varkbot.command.commands.ReloadCommand;
import com.github.longboyy.varkbot.command.commands.StartPluginCommand;
import com.github.longboyy.varkbot.command.commands.StopPluginCommand;
import com.github.longboyy.varkbot.config.DefaultConfig;
import com.github.longboyy.varkbot.database.DBConnection;
import com.github.longboyy.varkbot.libs.views.ViewHandler;
import com.github.longboyy.varkbot.libs.yaml.YamlParser;
import com.github.longboyy.varkbot.plugin.PluginManager;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

public class VarkBot {

	//private static VarkBot instance = null;
	
	private Logger logger;
	private JDA jda;
	private CommandHandler cmdHandler;
	private DefaultConfig defaultConfig;
	private PluginManager pluginManager;
	private ViewHandler viewHandler;
	
	private DBConnection connection;
	
	public VarkBot() {
		logger = LogManager.getLogger("VarkBot");
		setupConfig();
		setupDatabase();
		setupJDA();
		pluginManager = new PluginManager(this);
		viewHandler = new ViewHandler(this);
		//pluginManager.executePlugin("Propaganda");
		cmdHandler = new CommandHandler(this);
		cmdHandler.registerCommand(new StartPluginCommand());
		cmdHandler.registerCommand(new StopPluginCommand());
		cmdHandler.registerCommand(new ReloadCommand());
		cmdHandler.registerCommand(new HelpCommand());
		//cmdHandler.registerCommand(new DebugCommand());
		jda.addEventListener(new CommandListener(logger, cmdHandler));
		startCmdReading();
	}
	
	
	/*
	public static VarkBot instance() {
		if(instance == null) {
			instance = new VarkBot();
		}
		
		return instance;
	}
	*/
	
	public Logger getLogger() {
		return logger;
	}
	
	public CommandHandler getCommandHandler() {
		return cmdHandler;
	}
	
	public JDA getJDA() {
		return jda;
	}
	
	public DefaultConfig getConfig() {
		return defaultConfig;
	}
	
	public DBConnection getConnection() {
		return connection;
	}
	
	public PluginManager getPluginManager() {
		return pluginManager;
	}
	
	public ViewHandler getViewHandler() {
		return viewHandler;
	}
	
	private void startCmdReading() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				new CommandLineReader(logger, cmdHandler).start();
			}
		}).start();
	}
	
	private void setupJDA() {
		try {
			jda = new JDABuilder().setToken(getConfig().getDiscordToken()).setGame(Game.watching(getConfig().getDiscordGame())).build();
		} catch (LoginException e) {
			logger.error("Error whilst connecting to discord.", e.getMessage());
			System.exit(0);
		}
		logger.info("Connected to discord.");
	}
	
	private void setupConfig() {
		YamlParser.setup();
		File file = new File("varkbot/config.yml");
		defaultConfig = new DefaultConfig(logger, file, "/config.yml");
		
		if(!file.exists()) {
			defaultConfig.saveDefaultConfig();
		}else {
			defaultConfig.reloadConfig();
		}
		
		
		logger.info("Loaded config.");
	}
	
	private void setupDatabase() {
		connection = new DBConnection(logger, getConfig().getDBUser(), getConfig().getDBPass(), getConfig().getDBHost(), 
				getConfig().getDBPort(), getConfig().getDBName(), 5, 10000, 600000, 1800000);
		
		logger.info("Setup database connection.");
	}
}
