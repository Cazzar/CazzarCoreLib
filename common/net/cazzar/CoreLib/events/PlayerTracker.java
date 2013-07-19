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

package net.cazzar.corelib.events;

import cpw.mods.fml.common.IPlayerTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

/**
 * A handler for the Player events.
 */
public class PlayerTracker implements IPlayerTracker {

    @Override
    public void onPlayerLogin(EntityPlayer player) {
        MinecraftForge.EVENT_BUS.post(new PlayerLoginEvent(player));
    }

    @Override
    public void onPlayerLogout(EntityPlayer player) {
        MinecraftForge.EVENT_BUS.post(new PlayerLogoutEvent(player));
    }

    @Override
    public void onPlayerChangedDimension(EntityPlayer player) {
        MinecraftForge.EVENT_BUS.post(new PlayerChangedDimensionEvent(player));
    }

    @Override
    public void onPlayerRespawn(EntityPlayer player) {
        MinecraftForge.EVENT_BUS.post(new PlayerRespawnEvent(player));
    }
}
