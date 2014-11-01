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

package net.cazzar.corelib.lib;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cazzar.corelib.client.sound.CustomSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import paulscode.sound.SoundSystem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;

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
        return getSoundHandler().sndManager;
//        return ObfuscationReflectionHelper.getPrivateValue(SoundHandler.class, getSoundHandler(), "sndManager", "field_147694_f");
    }

    public static SoundSystem getSoundSystem() {
        return getSoundManager().sndSystem;
//        return ObfuscationReflectionHelper.getPrivateValue(SoundManager.class, getSoundManager(), "sndSystem", "field_148620_e");
    }

    /**
     * play a record at the specific location
     *
     * @param record the record name
     * @param x      the x pos
     * @param y      the y pos
     * @param z      the z pos
     */
    public static void playRecord(ItemRecord record, int x, int y, int z, String identifier) {

        ResourceLocation resource;
        if (record == null) {
            stop(identifier);
            return;
        }

        mc().ingameGUI.setRecordPlayingMessage(record.getRecordNameLocal());
        resource = record.getRecordResource("records." + record.recordName);
        if (resource == null) return;

        if (getSoundSystem().playing(identifier))
            getSoundSystem().stop(identifier);


        SoundEventAccessorComposite sound = getSoundHandler().getSound(resource);
        float f1 = 16F;

        if (sound == null) {
            return;
        }

        SoundPoolEntry soundpoolentry = sound.func_148720_g();

        SoundCategory soundcategory = sound.getSoundCategory();
        float volume = (float) MathHelper.clamp_double((double) f1 * soundpoolentry.getVolume() * (double) mc().gameSettings.getSoundLevel(soundcategory), 0.0D, 1.0D);
        float pitch = (float) MathHelper.clamp_double((double) f1 * soundpoolentry.getVolume() * (double) mc().gameSettings.getSoundLevel(soundcategory), 0.0D, 1.0D);


        //noinspection unchecked
        CustomSound customSound = new CustomSound(volume, resource, false, 0, pitch, x, y, z);
        //Do not break the game
        if (getSoundManager().delayedSounds.containsKey(customSound)) {
            getSoundManager().delayedSounds.remove(customSound); // do not break the game, just trod along all twe want.
        }

        getSoundManager().playDelayedSound(customSound, 1);
    }

    public static String getIdentifierForRecord(ItemRecord record, int x, int y, int z) {
        ResourceLocation resource = record.getRecordResource("records." + record.recordName);
        if (resource == null) return null;

        SoundEventAccessorComposite sound = getSoundHandler().getSound(resource);
        float f1 = 16F;

        if (sound == null) {
            return null;
        }

        SoundPoolEntry soundpoolentry = sound.func_148720_g();

        SoundCategory soundcategory = sound.getSoundCategory();
        float volume = (float) MathHelper.clamp_double((double) f1 * soundpoolentry.getVolume() * (double) mc().gameSettings.getSoundLevel(soundcategory), 0.0D, 1.0D);
        float pitch = (float) MathHelper.clamp_double((double) f1 * soundpoolentry.getVolume() * (double) mc().gameSettings.getSoundLevel(soundcategory), 0.0D, 1.0D);
//        ResourceLocation resourcelocation = soundpoolentry.getSoundPoolEntryLocation();

        // I know the type.
        //noinspection unchecked
        HashBiMap<String, ISound> playingSounds = (HashBiMap<String, ISound>) getSoundManager().playingSounds;
        BiMap<ISound, String> inverse = playingSounds.inverse();

        return inverse.get(new CustomSound(volume, resource, false, 0, pitch, x, y, z));
    }

    /**
     * stop any sounds playing at the coords.
     *
     * @param identifier the identifier of the playing sound
     */
    public static void stop(String identifier) {
        getSoundSystem().stop(identifier);
        getSoundManager().playingSounds.remove(identifier);
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
        return getSoundSystem() != null && getSoundSystem().playing(identifier);
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

    public static URL getURLForSoundResource(ResourceLocation location) {
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
//        Map<ChunkCoordinates, ISound> mapSoundPositions = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, world, "field_147593_P", "mapSoundPositions");
        return (ISound) world.mapSoundPositions.get(coords);
    }

    public static boolean isSoundEnabled() {
        return Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS) == 0 || getSoundSystem() != null || getSoundManager() != null;
    }
}
