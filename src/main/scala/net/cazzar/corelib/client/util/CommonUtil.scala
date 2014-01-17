package net.cazzar.corelib.client.util

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.common.FMLCommonHandler
object CommonUtil {
  /**
   * Get the side that is currently
   * @return the effective side
   */
  def getSide: Side = FMLCommonHandler.instance.getSide

  @SuppressWarnings(Array("BooleanMethodIsAlwaysInverted")) def isServer: Boolean = getSide.isServer

  /**
   * Join an array of strings with the specified decimeter
   * @param delim the decimeter
   * @param args the string to join
   * @return the joined string
   */
  def join(@SuppressWarnings(Array("SameParameterValue")) delim: String, args: String*): String = {
    val sb: StringBuilder = new StringBuilder
    args.foreach((s) => sb.append(s).append(delim))
    sb.toString()
  }
}
