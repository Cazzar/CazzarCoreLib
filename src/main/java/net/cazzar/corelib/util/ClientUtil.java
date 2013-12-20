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

/**
 *
 */
package net.cazzar.corelib.util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

/**
 * @author Cayde
 */
public class ClientUtil {
    /**
     * Get the minecraft instance
     *
     * @return the Minecraft instance
     */
    @SideOnly(Side.CLIENT)
    public static Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    /**
     * Is this the client
     *
     * @return true if it is a client.
     */
    public static boolean isClient() {
        return FMLCommonHandler.instance().getSide().isClient();
    }

    /**
     * Get if it is single player or not
     *
     * @return true if single player
     */
    @SuppressWarnings("UnusedDeclaration")
    @SideOnly(Side.CLIENT)
    public static boolean isSinglePlayer() {
        return mc().isSingleplayer();
    }
}
