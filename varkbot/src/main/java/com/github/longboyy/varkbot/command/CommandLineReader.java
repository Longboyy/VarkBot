package com.github.longboyy.varkbot.command;

import java.io.Console;

import org.apache.logging.log4j.Logger;

public class CommandLineReader {

	private Logger logger;
	
	private CommandHandler cmdHandler;
	
	public CommandLineReader(Logger logger, CommandHandler cmdHandler) {
		this.logger = logger;
		this.cmdHandler = cmdHandler;
	}
	
	public void start() {
		Console c = System.console();
		if(c == null) {
			return;
		}
		
		while(true) {
			String msg = c.readLine();
			
			cmdHandler.handle(null, null, msg);
		}
	}
	
}
