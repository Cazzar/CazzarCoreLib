package net.cazzar.corelib

import cpw.mods.fml.common.{LoadController, ModMetadata, DummyModContainer}
import java.util
import com.google.common.eventbus.{Subscribe, EventBus}
import net.minecraftforge.common.MinecraftForge
import cpw.mods.fml.common.event.FMLPreInitializationEvent

class ModContainer extends DummyModContainer {

  def getVersionFromJar: String = {
    val version: String = getClass.getPackage.getImplementationVersion
    if (version == null || version.isEmpty ) "UNKNOWN" else version
  }

  //  private[corelib] var meta: ModMetadata = null
  def this() = {
    this(ModMetadata)
    getMetadata.authorList = util.Arrays asList "cazzar"
    getMetadata.description = "The core library for cazzar's mods"
    getMetadata.modId = lib.Reference.MOD_ID
    getMetadata.name = "Cazzar Core Lib"
    getMetadata.url = "http://www.cazzar.net"
    getMetadata.version = getVersionFromJar
  }

  override def registerBus(bus: EventBus, controller: LoadController): Boolean = {
    bus.register(this)
    true
  }

  @Subscribe def preInit(event: FMLPreInitializationEvent) {
    if (event.getSide.isClient) {
      MinecraftForge.EVENT_BUS.register(ClientEvents)
    }
  }
}
