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

package net.cazzar.corelib.lib;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.*;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import paulscode.sound.SoundSystem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static net.cazzar.corelib.util.ClientUtil.mc;

@SuppressWarnings("UnusedDeclaration")
public class SoundSystemHelper {

    private static List<Entity> entitiesPlayingMusic = Lists.newArrayList();

    /**
     * Get the Minecraft sound handler
     *
     * @return the SoundHandler instance
     */
    public static SoundHandler getSoundHandler() {
        return mc().getSoundHandler();
    }

    public static SoundManager getSoundManager() {
        return ObfuscationReflectionHelper.getPrivateValue(SoundHandler.class, getSoundHandler(), "sndManager", "field_147694_f");
    }

    public static SoundSystem getSoundSystem() {
        return ObfuscationReflectionHelper.getPrivateValue(SoundManager.class, getSoundManager(), "sndSystem", "field_148620_e");
    }

    /**
     * play a record at the specific location
     *
     * @param record the record name
     * @param x      the x pos
     * @param y      the y pos
     * @param z      the z pos
     */
    public static void playRecord(World world, String record, int x, int y, int z, String identifier) {
        ItemRecord itemrecord = ItemRecord.getRecord(record);

        ResourceLocation resource;
        if (itemrecord == null) {
            return;
        }

        mc().ingameGUI.setRecordPlayingMessage(itemrecord.getRecordNameLocal());
        resource = itemrecord.getRecordResource(record);
        if (resource == null) return;

        if (getSoundSystem().playing(identifier))
            getSoundSystem().stop(identifier);


        SoundEventAccessorComposite sound = getSoundHandler().getSound(resource);
        float f1 = 16F;

        SoundPoolEntry soundpoolentry = sound.func_148720_g();

        SoundCategory soundcategory = sound.getSoundCategory();
        float volume = (float) MathHelper.clamp_double((double)f1 * soundpoolentry.getVolume() * (double)mc().gameSettings.getSoundLevel(soundcategory), 0.0D, 1.0D);
        float pitch = (float) MathHelper.clamp_double((double)f1 * soundpoolentry.getVolume() * (double)mc().gameSettings.getSoundLevel(soundcategory), 0.0D, 1.0D);
        ResourceLocation resourcelocation = soundpoolentry.getSoundPoolEntryLocation();

        getSoundSystem().newStreamingSource(false, identifier, getURLForSoundResource(resourcelocation), resourcelocation.toString(),false, x, y, z, ISound.AttenuationType.LINEAR.getTypeInt(), f1);
        getSoundSystem().setPitch(identifier, pitch);
        getSoundSystem().setVolume(identifier, volume);
        getSoundSystem().play(identifier);
    }

    /**
     * stop any sounds playing at the coords.
     *
     * @param identifier the identifier of the playing sound
     */
    public static void stop(String identifier) {
        getSoundSystem().stop(identifier);
    }

    /**
     * stop any sounds playing at the coords.
     *
     * @param world the world that it is playing in
     */
    public static void stop(RenderGlobal world, ChunkCoordinates chunkCoordinates) {
        ISound sound = getSoundForChunkCoordinates(world, chunkCoordinates);
        if (sound != null)
            getSoundSystem().stop(getChannel(sound));
//            getSoundHandler().stopSound(sound);
    }

    /**
     * Check if something is playing at the coords
     *
     * @param identifier the identifier of the playing sound
     * @return if the {@link SoundSystem} is playing with that identifier.
     */
    public static boolean isPlaying(String identifier) {
        return getSoundSystem().playing(identifier);
    }

    /**
     * Check if something is playing at the coords
     *
     * @param world       the world that it is playing in
     * @param coordinates the coordinates of the "player"
     * @return if the {@link SoundSystem} is playing with that identifier.
     */
    public static boolean isPlaying(RenderGlobal world, ChunkCoordinates coordinates) {
        ISound sound = getSoundForChunkCoordinates(world, coordinates);
        return sound != null && getSoundSystem().playing(getChannel(sound));
        //        return sound != null && getSoundHandler().isSoundPlaying(sound);
    }

    public static String getChannel(ISound sound) {
        HashBiMap<String, ISound> playingSounds = ObfuscationReflectionHelper.getPrivateValue(SoundManager.class, getSoundManager(), "playingSounds", "field_148629_h");
        return playingSounds.inverse().get(sound);
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

    public static URL getURLForSoundResource(ResourceLocation location){
        Method m = ReflectionHelper.findMethod(SoundManager.class, getSoundManager(), new String[]{"getURLForSoundResource", "func_148612_a"}, ResourceLocation.class);
        try {
            return (URL) m.invoke(getSoundManager(), location);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public static ISound getSoundForChunkCoordinates(RenderGlobal world, ChunkCoordinates coords) {
        Map<ChunkCoordinates, ISound> mapSoundPositions = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, world, "field_147593_P", "mapSoundPositions");
        return mapSoundPositions.get(coords);
    }
}
