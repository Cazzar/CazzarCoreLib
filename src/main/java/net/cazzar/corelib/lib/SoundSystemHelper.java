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

package net.cazzar.corelib.lib;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import paulscode.sound.SoundSystem;

import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class SoundSystemHelper {

    private static List<Entity> entitiesPlayingMusic = Lists.newArrayList();

    /**
     * Get the Minecraft sound handler
     *
     * @return the SoundHandler instance
     */
    public static SoundHandler getSoundHandler() {
        return mc().func_147118_V();
    }

    /**
     * play a record at the specific location
     *
     * @param record the record name
     * @param x      the x pos
     * @param y      the y pos
     * @param z      the z pos
     */
    public static void playRecord(World world, String record, int x, int y, int z) {
        world.playRecord(record, x, y, z);
    }

    /**
     * stop any sounds playing at the coords.
     *
     * @param world       the world that it is playing in
     */
    public static void stop(RenderGlobal world, ChunkCoordinates chunkCoordinates) {
        ISound sound = getSoundForChunkCoordinates(world, chunkCoordinates);
        if (sound != null)
            getSoundHandler().func_147683_b(sound);
    }

    /**
     * Check if something is playing at the coords
     *
     *
     * @param world       the world that it is playing in
     *
     * @param coordinates the coordinates of the "player"
     * @return if the {@link SoundSystem} is playing with that identifier.
     */
    public static boolean isPlaying(RenderGlobal world, ChunkCoordinates coordinates) {
        ISound sound = getSoundForChunkCoordinates(world, coordinates);
        return sound != null && getSoundHandler().func_147692_c(sound);
    }

    /**
     * Register a record
     *
     * @param s the record in domain:name.ext format to register
     */
    @SideOnly(Side.CLIENT)
    public static void registerRecord(String s) {
        //    getSoundManager().soundPoolStreaming.addSound(s);
    }

    @SideOnly(Side.CLIENT)
    public static ISound getSoundForChunkCoordinates(RenderGlobal world, ChunkCoordinates coords) {
        return (ISound) world.field_147593_P.get(coords);
    }
}
