package net.cazzar.corelib.lib;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {
    private Logger logger;
    private boolean setup;
    private String identifier;
    public static LogHelper corelog = new LogHelper();

    public void config(String string, Object... args) {
        log(Level.CONFIG, string, args);
    }

    public void fine(String string, Object... args) {
        log(Level.FINE, string, args);
    }

    public void finer(String string, Object... args) {
        log(Level.FINER, string, args);
    }

    public void finest(String string, Object... args) {
        log(Level.FINEST, string, args);
    }

    public void info(String string, Object... args) {
        log(Level.INFO, string, args);
    }

    public void init() {
        setup = true;
        logger = Logger.getLogger(identifier);
    }

    public void log(Level level, String message, Object... args) {
        if (!setup) init();
        logger.log(level, String.format(message, args));
    }

    public void severe(String string, Object... args) {
        log(Level.SEVERE, string, args);
    }

    public void warning(String string, Object... args) {
        log(Level.WARNING, string, args);
    }

    public LogHelper() {
        identifier = Reference.MOD_ID;
    }

    public LogHelper(String identifier) {
        this.identifier = identifier;
    }
}
