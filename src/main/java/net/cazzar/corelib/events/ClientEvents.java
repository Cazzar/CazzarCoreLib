/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Cayde Dixon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
