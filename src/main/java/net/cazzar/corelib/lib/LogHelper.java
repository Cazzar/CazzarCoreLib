/*
 * Copyright (C) 2013 cazzar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package net.cazzar.corelib.lib;

import cpw.mods.fml.common.FMLLog;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {
    private Logger logger;
    private boolean setup;
    private String identifier;
    public static LogHelper coreLog = new LogHelper();

    /**
     * Log the passed string at level CONFIG
     *
     * @param string the string (format acceptable) to log
     * @param args   the params to String.format
     */
    public void config(String string, Object... args) {
        log(Level.CONFIG, string, args);
    }

    /**
     * Log the passed string at level FINE
     *
     * @param string the string (format acceptable) to log
     * @param args   the params to String.format
     */
    public void fine(String string, Object... args) {
        log(Level.FINE, string, args);
    }

    /**
     * Log the passed string at level FINER
     *
     * @param string the string (format acceptable) to log
     * @param args   the params to String.format
     */
    public void finer(String string, Object... args) {
        log(Level.FINER, string, args);
    }

    /**
     * Log the passed string at level FINEST
     *
     * @param string the string (format acceptable) to log
     * @param args   the params to String.format
     */
    public void finest(String string, Object... args) {
        log(Level.FINEST, string, args);
    }

    /**
     * Log the passed string at level INFO
     *
     * @param string the string (format acceptable) to log
     * @param args   the params to String.format
     */
    public void info(String string, Object... args) {
        log(Level.INFO, string, args);
    }

    /**
     * Init the logger
     */
    public void init() {
        setup = true;
        logger = Logger.getLogger(identifier);
    }

    /**
     * Log the passed string at the passed level
     *
     * @param level   the level to log at
     * @param message the string (format acceptable) to log
     * @param args    the params to String.format
     */
    public void log(Level level, String message, Object... args) {
        if (!setup) init();
        logger.log(level, String.format(message, args));
    }

    /**
     * Log the passed string at level SEVERE
     *
     * @param string the string (format acceptable) to log
     * @param args   the params to String.format
     */
    public void severe(String string, Object... args) {
        log(Level.SEVERE, string, args);
    }

    /**
     * Log the passed string at level WARNING
     *
     * @param string the string (format acceptable) to log
     * @param args   the params to String.format
     */
    public void warning(String string, Object... args) {
        log(Level.WARNING, string, args);
    }


    private LogHelper() {
        this(Reference.MOD_ID);
    }

    /**
     * Create a LogHelper for the passed logger
     *
     * @param identifier the identifier for the Log helper
     */
    public LogHelper(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Log an throwable
     *
     * @param logLevel the level to log at
     * @param e        the exception
     * @param message  the message
     * @param params   the args
     */
    public void log(Level logLevel, Throwable e, String message, Object... params) {
        Logger old = logger.getParent();
        logger.setParent(FMLLog.getLogger());
        logger.log(logLevel, String.format(message, params), e);
        logger.setParent(old);
    }

}
