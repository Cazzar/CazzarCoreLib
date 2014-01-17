package net.cazzar.corelib.client

import net.minecraft.entity.player.EntityPlayer
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.RenderPlayerEvent
import com.google.common.collect.ImmutableBiMap
import net.cazzar.corelib.client.util.ClientUtil

class ClientEvents {
  private[events] var tails: ImmutableBiMap[EntityPlayer, Nothing] = null

  @SubscribeEvent def playerRenderEvent(event: RenderPlayerEvent.Post) {
    val player: EntityPlayer = event.entityPlayer
    val mcPlayer: EntityPlayer = ClientUtil.mc.thePlayer
    if (tails.containsKey(player.func_146103_bH.getName)) {
      val tail: render.RenderTail = tails.get(player.func_146103_bH.getName)
      tail.doRender(player, player.posX - mcPlayer.posX, player.posY - mcPlayer.posY, player.posZ - mcPlayer.posZ, event.partialRenderTick, 1)
    }
  }

}
