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

import cpw.mods.fml.common.Loader;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;

/**
 * VersionHelper Based off Equivalent-Exchange-3's version helper Made generic for other mods's use
 *
 * @author pahimar, modified by cazzar.
 */
public class VersionHelper implements Runnable {
    // The (publicly available) remote version number authority file
    private String REMOTE_VERSION_XML_FILE;

    public static Properties remoteVersionProperties = new Properties();


    public enum Result {
        UNINITIALIZED,
        CURRENT,
        OUTDATED,
        ERROR,
        FINAL_ERROR,
        MC_VERSION_NOT_FOUND
    }

    public static final int VERSION_CHECK_ATTEMPTS = 3;

    // Var to hold the result of the remote version check, initially set to
    // uninitialized
    private static Result result = Result.UNINITIALIZED;
    public static String remoteVersion = null;
    public static String remoteUpdateLocation = null;

    String modName;
    String modVersion;

    public VersionHelper(String modName, String modVersion, String versionFile) {
        this.REMOTE_VERSION_XML_FILE = versionFile;
        this.modName = modName;
        this.modVersion = modVersion;
    }

    /**
     * Checks the version of the currently running instance of the mod against the remote version authority, and sets
     * the result of the check appropriately
     */
    public void checkVersion() {

        InputStream remoteVersionRepoStream = null;
        result = Result.UNINITIALIZED;

        try {
            final URL remoteVersionURL = new URL(REMOTE_VERSION_XML_FILE);
            remoteVersionRepoStream = remoteVersionURL.openStream();
            remoteVersionProperties.loadFromXML(remoteVersionRepoStream);

            final String remoteVersionProperty = remoteVersionProperties
                    .getProperty(Loader.instance().getMCVersionString());

            if (remoteVersionProperty != null) {
                final String[] remoteVersionTokens = remoteVersionProperty
                        .split("\\|");

                if (remoteVersionTokens.length >= 2) {
                    remoteVersion = remoteVersionTokens[0];
                    remoteUpdateLocation = remoteVersionTokens[1];
                } else result = Result.ERROR;

                if (remoteVersion != null)
                    if (remoteVersion.equalsIgnoreCase(getVersionForCheck())) result = Result.CURRENT;
                    else result = Result.OUTDATED;

            } else result = Result.MC_VERSION_NOT_FOUND;
        } catch (final Exception e) {
        } finally {
            if (result == Result.UNINITIALIZED) result = Result.ERROR;

            try {
                if (remoteVersionRepoStream != null)
                    remoteVersionRepoStream.close();
            } catch (final Exception ex) {
            }
        }
    }

    public void execute() {
        new Thread(this).start();
    }

    public static Result getResult() {

        return result;
    }

    public String getResultMessage() {

        if (result == Result.UNINITIALIZED) return "Remote version check failed to initialize properly";
        else if (result == Result.CURRENT) {
            String returnString = "Currently using the most up to date version (@REMOTE_MOD_VERSION@) of JukeboxReloaded for @MINECRAFT_VERSION@";
            returnString = returnString.replace("@REMOTE_MOD_VERSION@",
                    remoteVersion);
            returnString = returnString.replace("@MINECRAFT_VERSION@", Loader
                    .instance().getMCVersionString());
            return returnString;
        } else if (result == Result.OUTDATED && remoteVersion != null
                && remoteUpdateLocation != null) {
            String returnString = "A new @MOD_NAME@ version exists (@REMOTE_MOD_VERSION@) for @MINECRAFT_VERSION@. Get it here: @MOD_UPDATE_LOCATION@";
            returnString = returnString.replace("@MOD_NAME@", modName);
            returnString = returnString.replace("@REMOTE_MOD_VERSION@",
                    remoteVersion);
            returnString = returnString.replace("@MINECRAFT_VERSION@", Loader
                    .instance().getMCVersionString());
            returnString = returnString.replace("@MOD_UPDATE_LOCATION@",
                    remoteUpdateLocation);
            return returnString;
        } else if (result == Result.OUTDATED && remoteVersion != null
                && remoteUpdateLocation != null) {
            String returnString = "A new @MOD_NAME@ version exists (@REMOTE_MOD_VERSION@) for @MINECRAFT_VERSION@. Get it here: @MOD_UPDATE_LOCATION@";
            returnString = returnString.replace("@MOD_NAME@", modName);
            returnString = returnString.replace("@REMOTE_MOD_VERSION@",
                    remoteVersion);
            returnString = returnString.replace("@MINECRAFT_VERSION@", Loader
                    .instance().getMCVersionString());
            returnString = returnString.replace("@MOD_UPDATE_LOCATION@",
                    remoteUpdateLocation);
            return returnString;
        } else if (result == Result.ERROR)
            return "Error while connecting to remote version authority file; trying again";
        else if (result == Result.FINAL_ERROR)
            return "Version check stopping after three unsuccessful connection attempts";
        else if (result == Result.MC_VERSION_NOT_FOUND) {
            String returnString = "Unable to find a version of @MOD_NAME@ for @MINECRAFT_VERSION@ in the remote version authority";
            returnString = returnString.replace("@MOD_NAME@", modName);
            returnString = returnString.replace("@MINECRAFT_VERSION@", Loader
                    .instance().getMCVersionString());
            return returnString;
        } else {
            result = Result.ERROR;
            return "Error while connecting to remote version authority file; trying again";
        }
    }

    public String getResultMessageForClient() {

        String returnString = "A new @MOD_NAME@ version exists (@REMOTE_MOD_VERSION@) for @MINECRAFT_VERSION@. Get it here: @MOD_UPDATE_LOCATION@";
        returnString = returnString.replace("@MOD_NAME@",
                Colours.YELLOW.toString() + modName
                        + Colours.RESET.toString());
        returnString = returnString.replace("@REMOTE_MOD_VERSION@",
                Colours.YELLOW.toString() + VersionHelper.remoteVersion
                        + Colours.RESET.toString());
        returnString = returnString.replace("@MINECRAFT_VERSION@",
                Colours.YELLOW.toString()
                        + Loader.instance().getMCVersionString()
                        + Colours.RESET.toString());
        returnString = returnString.replace("@MOD_UPDATE_LOCATION@",
                Colours.YELLOW.toString()
                        + VersionHelper.remoteUpdateLocation
                        + Colours.RESET.toString());
        return returnString;
    }

    private String getVersionForCheck() {

        final String[] versionTokens = modVersion.split(" ");

        if (versionTokens.length >= 1) return versionTokens[0];
        else return modVersion;
    }

    public void logResult() {

        if (result == Result.CURRENT || result == Result.OUTDATED) LogHelper.coreLog.log(Level.INFO,
                getResultMessage());
        else LogHelper.coreLog.log(Level.WARNING, getResultMessage());
    }

    @Override
    public void run() {

        int count = 0;

        LogHelper.coreLog
                .log(Level.INFO,
                        "Initializing remote version check against remote version authority, located at "
                                + REMOTE_VERSION_XML_FILE);

        try {
            while (count < VERSION_CHECK_ATTEMPTS - 1
                    && (result == Result.UNINITIALIZED || result == Result.ERROR)) {

                checkVersion();
                count++;
                logResult();

                if (result == Result.UNINITIALIZED || result == Result.ERROR)
                    Thread.sleep(10000);
            }

            if (result == Result.ERROR) {
                result = Result.FINAL_ERROR;
                logResult();
            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }

    }
}