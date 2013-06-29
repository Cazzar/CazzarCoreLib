package net.cazzar.CoreLib.lib;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;

import cpw.mods.fml.common.Loader;

/**
 * VersionHelper
 * Based off Equivalent-Exchange-3's version helper
 * Made generic for other mods's use
 * 
 * @author pahimar, modified by cazzar.
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class VersionHelper implements Runnable {
	
	// private static VersionHelper instance = new VersionHelper();
	
	// The (publicly available) remote version number authority file
	private String				REMOTE_VERSION_XML_FILE;					// =
																			// "https://bitbucket.org/cazzar/mod-updates/raw/master/JukeboxReloaded.xml";
																			
	public static Properties	remoteVersionProperties	= new Properties();
	
	/*// All possible results of the remote version number check
	public static final byte	UNINITIALIZED			= 0;
	public static final byte	CURRENT					= 1;
	public static final byte	OUTDATED				= 2;
	public static final byte	ERROR					= 3;
	public static final byte	FINAL_ERROR				= 4;
	public static final byte	MC_VERSION_NOT_FOUND	= 5;
	*/
	
	public enum Result {
		UNINITIALIZED,
		CURRENT,
		OUTDATED,
		ERROR,
		FINAL_ERROR,
		MC_VERSION_NOT_FOUND
	}
	
	public static final int		VERSION_CHECK_ATTEMPTS	= 3;
	
	// Var to hold the result of the remote version check, initially set to
	// uninitialized
	private static Result		result					= Result.UNINITIALIZED;
	public static String		remoteVersion			= null;
	public static String		remoteUpdateLocation	= null;
	
	String						modName;
	String						modVersion;
	
	public VersionHelper(String modName, String modVersion, String versionFile) {
		this.REMOTE_VERSION_XML_FILE = versionFile;
		this.modName = modName;
		this.modVersion = modVersion;
	}
	
	/***
	 * Checks the version of the currently running instance of the mod against
	 * the remote version authority, and sets the result of the check
	 * appropriately
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
				}
				else result = Result.ERROR;
				
				if (remoteVersion != null)
					if (remoteVersion.equalsIgnoreCase(getVersionForCheck())) result = Result.CURRENT;
					else result = Result.OUTDATED;
				
			}
			else result = Result.MC_VERSION_NOT_FOUND;
		}
		catch (final Exception e) {}
		finally {
			if (result == Result.UNINITIALIZED) result = Result.ERROR;
			
			try {
				if (remoteVersionRepoStream != null)
					remoteVersionRepoStream.close();
			}
			catch (final Exception ex) {}
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
		}
		else if (result == Result.OUTDATED && remoteVersion != null
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
		}
		else if (result == Result.OUTDATED && remoteVersion != null
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
		}
		else if (result == Result.ERROR) return "Error while connecting to remote version authority file; trying again";
		else if (result == Result.FINAL_ERROR) return "Version check stopping after three unsuccessful connection attempts";
		else if (result == Result.MC_VERSION_NOT_FOUND) {
			String returnString = "Unable to find a version of @MOD_NAME@ for @MINECRAFT_VERSION@ in the remote version authority";
			returnString = returnString.replace("@MOD_NAME@", modName);
			returnString = returnString.replace("@MINECRAFT_VERSION@", Loader
					.instance().getMCVersionString());
			return returnString;
		}
		else {
			result = Result.ERROR;
			return "Error while connecting to remote version authority file; trying again";
		}
	}
	
	public String getResultMessageForClient() {
		
		String returnString = "A new @MOD_NAME@ version exists (@REMOTE_MOD_VERSION@) for @MINECRAFT_VERSION@. Get it here: @MOD_UPDATE_LOCATION@";
		returnString = returnString.replace("@MOD_NAME@",
				Colours.TEXT_COLOUR_PREFIX_YELLOW + modName
						+ Colours.TEXT_COLOUR_PREFIX_WHITE);
		returnString = returnString.replace("@REMOTE_MOD_VERSION@",
				Colours.TEXT_COLOUR_PREFIX_YELLOW + VersionHelper.remoteVersion
						+ Colours.TEXT_COLOUR_PREFIX_WHITE);
		returnString = returnString.replace("@MINECRAFT_VERSION@",
				Colours.TEXT_COLOUR_PREFIX_YELLOW
						+ Loader.instance().getMCVersionString()
						+ Colours.TEXT_COLOUR_PREFIX_WHITE);
		returnString = returnString.replace("@MOD_UPDATE_LOCATION@",
				Colours.TEXT_COLOUR_PREFIX_YELLOW
						+ VersionHelper.remoteUpdateLocation
						+ Colours.TEXT_COLOUR_PREFIX_WHITE);
		return returnString;
	}
	
	private String getVersionForCheck() {
		
		final String[] versionTokens = modVersion.split(" ");
		
		if (versionTokens.length >= 1) return versionTokens[0];
		else return modVersion;
	}
	
	public void logResult() {
		
		if (result == Result.CURRENT || result == Result.OUTDATED) LogHelper.log(Level.INFO,
				getResultMessage());
		else LogHelper.log(Level.WARNING, getResultMessage());
	}
	
	@Override
	public void run() {
		
		int count = 0;
		
		LogHelper
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
		}
		catch (final InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}