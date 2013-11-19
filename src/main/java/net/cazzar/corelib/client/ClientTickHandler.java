/*
 * Copyright (C) 2013 cazzar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package net.cazzar.corelib.client;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.cazzar.corelib.lib.SoundSystemHelper;

import java.util.EnumSet;

/**
 * @Author: Cayde
 */
public class ClientTickHandler implements ITickHandler {
    /**
     * Called at the "start" phase of a tick
     * <p/>
     * Multiple ticks may fire simultaneously- you will only be called once with all the firing ticks
     *
     * @param type
     * @param tickData
     */
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        SoundSystemHelper.updateEntitySoundVelocities();

        /*if (ClientUtil.mc().theWorld != null && ClientUtil.mc().theWorld.playerEntities.size() > 0) {
            @SuppressWarnings("unchecked") List<AbstractClientPlayer> players = ClientUtil.mc().theWorld.playerEntities;

            for(AbstractClientPlayer player : players) {
                if (player == null) continue;

                if (true) {
                    //YAY! Reflection!
                    Field skin = ReflectionHelper.findField(AbstractClientPlayer.class, "locationSkin", "field_110312_d");
                    if (skin == null)
                        continue;

                    try {
                        ResourceLocation location = (ResourceLocation) skin.get(player);
                        ResourceLocation cheetah = new ResourceLocation("cazzarcore", "textures/entity/Cheetah.png");

                        if (!location.equals(cheetah)) {
                            skin.set(player, cheetah);
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }*/
    }

    /**
     * Called at the "end" phase of a tick
     * <p/>
     * Multiple ticks may fire simultaneously- you will only be called once with all the firing ticks
     *
     * @param type
     * @param tickData
     */
    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
    }

    /**
     * Returns the list of ticks this tick handler is interested in receiving at the minute
     */
    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    /**
     * A profiling label for this tick handler
     */
    @Override
    public String getLabel() {
        return "Cazzar Core Lib Tick Handler";
    }
}
