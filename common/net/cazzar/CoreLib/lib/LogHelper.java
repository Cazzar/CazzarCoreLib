package net.cazzar.CoreLib.lib;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {
	static Logger	logger;
	
	public static void init() {
		logger = Logger.getLogger(Reference.MOD_ID);
	}
	
	public static void log(Level level, String message) {
		logger.log(level, message);
	}
	
	/**
	 * @param string
	 */
	public static void logInfo(String string) {
		log(Level.INFO, string);
	}
	
}
