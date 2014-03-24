/*
 * Copyright (C) 2014 Cayde Dixon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
