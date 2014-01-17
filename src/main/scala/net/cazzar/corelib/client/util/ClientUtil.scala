package net.cazzar.corelib.client.util

import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.client.Minecraft
import cpw.mods.fml.common.FMLCommonHandler

object ClientUtil {
  /**
   * Get the minecraft instance
   *
   * @return the Minecraft instance
   */
  @SideOnly(Side.CLIENT) def mc: Minecraft = {
    Minecraft.getMinecraft
  }

  /**
   * Is this the client
   *
   * @return true if it is a client.
   */
  def isClient: Boolean = {
    FMLCommonHandler.instance.getSide.isClient
  }

  /**
   * Get if it is single player or not
   *
   * @return true if single player
   */
  @SuppressWarnings("UnusedDeclaration")
  @SideOnly(Side.CLIENT) def isSinglePlayer: Boolean = {
    mc.isSingleplayer
  }
}
