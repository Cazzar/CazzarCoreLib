/**
 * 
 */
package net.cazzar.corelib.util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**           0
 * @author Cayde
 *
 */
public class CommonUtil {
	public static Side getSide() {
		return FMLCommonHandler.instance().getSide();
	}
    public static boolean isServer(){
        return getSide().isServer();
    }
}
