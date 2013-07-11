/**
 * 
 */
package net.cazzar.corelib.util;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Cayde
 *
 */
@SideOnly(Side.CLIENT)
public class ClientUtil {
	public static Minecraft mc() {
		return Minecraft.getMinecraft();
	}
	
	public static boolean isClient() {
		return FMLCommonHandler.instance().getSide().isClient();
	}
	
	public static boolean isSinglePlayer() {
		return mc().isSingleplayer();
	}
}
