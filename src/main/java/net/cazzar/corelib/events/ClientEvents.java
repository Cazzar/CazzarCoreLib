package net.cazzar.corelib.events;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Maps;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.cazzar.corelib.client.rendering.RenderTail;
import net.cazzar.corelib.util.ClientUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.HashMap;

/**
 * The event handler for Clientside events
 */
public class ClientEvents {
    static ImmutableBiMap<String, RenderTail> tails;

    static {
        HashMap<String, RenderTail> tailHashMap = Maps.newHashMap();
        tailHashMap.put("cazzar", new RenderTail());
        tailHashMap.put("Speedy_Taco", new RenderTail());

        tails = ImmutableBiMap.copyOf(tailHashMap);
    }

    @SubscribeEvent
    public void playerRenderEvent(RenderPlayerEvent.Post event) {
        EntityPlayer player = event.entityPlayer;
        EntityPlayer mcPlayer = ClientUtil.mc().thePlayer;
        if (tails.containsKey(player.getGameProfile().getName())) {
            RenderTail tail = tails.get(player.getGameProfile().getName());
            tail.doRender(player, player.posX - mcPlayer.posX, player.posY - mcPlayer.posY, player.posZ - mcPlayer.posZ, event.partialRenderTick, 1);
        }
    }
}
