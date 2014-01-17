package net.cazzar.corelib

import cpw.mods.fml.relauncher.{IFMLCallHook, IFMLLoadingPlugin}
import java.util
import java.io.File
import java.lang.reflect.Field

class CoreMod extends IFMLLoadingPlugin with IFMLCallHook {
  private var runtimeDeobfuscationEnabled: Boolean = true
  private var deobfuscationFileName: String = null
  private var mcLocation: File = null
  private final val FINGERPRINT: String = "B6:9D:73:36:FB:E4:C3:E9:72:79:EB:3E:E3:19:9F:00:9A:90:34:75".toLowerCase.replace(":", "")
  private var coremodLocation: File = null

  def getASMTransformerClass: Array[String] = Array()

  def getModContainerClass: String = "net.cazzar.corelib.ModContainer"

  def getSetupClass: String = getClass.getCanonicalName

  def getAccessTransformerClass: String = "net.cazzar.corelib.asm.CoreAccessTransformer"

  def call(): Void = {
    null
  }

  def injectData(data: util.Map[String, AnyRef]): Unit = {
    for (key <- data.keySet) {
      try {
        val f: Field = this.getClass.getDeclaredField(key)
        f.setAccessible(true)
        f.set(null, data.get(key))
      }
      catch {
        case ignored: Nothing => {
        }
        case e: Nothing => {
          LogHelper.coreLog.catching(e)
          LogHelper.coreLog.warn("Unable to set field: %s", key)
        }
      }
    }
  }
}
