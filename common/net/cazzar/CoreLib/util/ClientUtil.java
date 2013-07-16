/**
 *
 */
package net.cazzar.corelib.util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

/**
 * @author Cayde
 */
@SideOnly(Side.CLIENT)
public class ClientUtil {
    /**
     * Get the minecraft instance
     *
     * @return the Minecraft instance
     */
    public static Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    /**
     * Is this the client
     *
     * @return true if it is a client.
     */
    public static boolean isClient() {
        return FMLCommonHandler.instance().getSide().isClient();
    }

    public static boolean isSinglePlayer() {
        return mc().isSingleplayer();
    }
}
