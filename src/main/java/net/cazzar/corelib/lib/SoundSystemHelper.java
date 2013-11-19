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
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.corelib.util.CommonUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemRecord;
import net.minecraftforge.client.event.sound.PlayStreamingEvent;
import net.minecraftforge.client.event.sound.PlayStreamingSourceEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import paulscode.sound.SoundSystem;

import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class SoundSystemHelper {

    private static List<Entity> entitiesPlayingMusic = Lists.newArrayList();

    /**
     * Get the Minecraft sound system manager
     *
     * @return the sound manager
     */
    public static SoundManager getSoundManager() {
        return Minecraft.getMinecraft().sndManager;
    }

    /**
     * get the sound system
     *
     * @return the Minecraft sound system
     */
    public static SoundSystem getSoundSystem() {
        return getSoundManager().sndSystem;
    }

    /**
     * get if the sound system is enabled or not
     *
     * @return if the sound system is enabled.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isSoundEnabled() {
        return !CommonUtil.isServer() && getSoundSystem() != null;
    }

    /**
     * play a record at the specific location
     *
     * @param record     the record name
     * @param x          the x pos
     * @param y          the y pos
     * @param z          the z pos
     * @param volume     the volume to play at
     * @param identifier the identifier to play at.
     */
    public static void playRecord(String record, float x, float y, float z, float volume, String identifier) {
        if (!isSoundEnabled()) return;

        SoundSystem sndSystem = getSoundSystem();

        ItemRecord itemrecord = ItemRecord.getRecord(record);
        if (itemrecord == null) return;

        Minecraft.getMinecraft().ingameGUI.setRecordPlayingMessage(itemrecord.getRecordTitle());
        if (sndSystem.playing(identifier)) sndSystem.stop(identifier);

        SoundPoolEntry song = getSoundManager().soundPoolStreaming.getRandomSoundFromSoundPool(record);
        song = SoundEvent.getResult(new PlayStreamingEvent(getSoundManager(), song, identifier, x, y, z));

        if (song == null) return;
        if (sndSystem.playing("BgMusic")) sndSystem.stop("BgMusic");

        float f3 = 16.0F * volume;
        sndSystem.newStreamingSource(true, identifier, song.getSoundUrl(), song.getSoundName(), false, x, y, z, 2, f3 * 4.0F);

        sndSystem.setVolume(identifier, volume * Minecraft.getMinecraft().gameSettings.soundVolume);

        MinecraftForge.EVENT_BUS.post(new PlayStreamingSourceEvent(getSoundManager(), identifier, x, y, z));
        sndSystem.play(identifier);
    }

    /**
     * pause the steaming identifier
     *
     * @param identifier the identifier to pause
     */
    public static void pause(String identifier) {
        if (!isSoundEnabled())

            getSoundSystem().pause(identifier);
    }

    /**
     * Resume the identifier
     *
     * @param identifier the identifier to resume
     */
    public static void resume(String identifier) {
        if (!isSoundEnabled()) return;
        getSoundSystem().play(identifier);
    }

    /**
     * Stop the identifier playing
     *
     * @param identifier the identifier to stop
     */
    public static void stop(String identifier) {
        if (!isSoundEnabled()) return;
        getSoundSystem().stop(identifier);
    }

    /**
     * Check if a identifier is playing
     *
     * @param identifier the identifier to check.
     * @return if the {@link SoundSystem} is playing with that identifier.
     */
    public static boolean isPlaying(String identifier) {
        return isSoundEnabled() && getSoundSystem().playing(identifier);
    }

    public static boolean isBackgroundMusicPlaying() {
        return isPlaying("BgMusic");
    }

    public static void stopBackgroundMusicIfPlaying() {
        if (isBackgroundMusicPlaying()) stop("BgMusic");
    }

    /**
     * Register a record
     *
     * @param s the record in domain:name.ext format to register
     */
    @SideOnly(Side.CLIENT)
    public static void registerRecord(String s) {
        getSoundManager().soundPoolStreaming.addSound(s);
    }

    /**
     * Play a record at the Entity
     */
    public static void playRecordAtEntity(Entity entity, String recordName, float volume) {
        //The sound name, the entity, the volume, the pitch, priority
        //getSoundManager().playEntitySound("streaming." + recordName, entity, 0.5F * ClientUtil.mc().gameSettings.soundVolume, 0, true);
        if (!isSoundEnabled()) {
            return;
        }
        String s1 = getEntityChannel(entity);

        if (getSoundSystem().playing(s1)) {
            getSoundManager().updateSoundLocation(entity);
        } else {
            if (getSoundSystem().playing(s1)) {
                getSoundSystem().stop(s1);
            }

            if (recordName != null) {
                SoundPoolEntry soundpoolentry = getSoundManager().soundPoolStreaming.getRandomSoundFromSoundPool(recordName);

                if (soundpoolentry != null && volume > 0.0F) {
                    float f2 = 16.0F;

                    if (volume > 1.0F) {
                        f2 *= volume;
                    }

                    getSoundSystem().newSource(true, s1, soundpoolentry.getSoundUrl(), soundpoolentry.getSoundName(), false, (float) entity.posX, (float) entity.posY, (float) entity.posZ, 2, f2);
                    //getSoundSystem().setLooping(s1, true);
                    //getSoundSystem().setPitch(s1, 0);

                    if (volume > 1.0F) {
                        volume = 1.0F;
                    }

                    getSoundSystem().setVolume(s1, volume * ClientUtil.mc().gameSettings.soundVolume);
                    getSoundSystem().setVelocity(s1, (float) entity.motionX, (float) entity.motionY, (float) entity.motionZ);
                    getSoundSystem().play(s1);
                }
            }
        }


        entitiesPlayingMusic.add(entity);
    }

    public static synchronized void updateEntitySoundVelocities() {
        for (Entity e : entitiesPlayingMusic) {
            //getSoundManager().updateSoundLocation(e);
            String s = getEntityChannel(e);

            if (getSoundSystem().playing(s)) {
                getSoundSystem().setPosition(s, (float) e.posX, (float) e.posY, (float) e.posZ);
                getSoundSystem().setVelocity(s, (float) e.motionX, (float) e.motionY, (float) e.motionZ);
            } else {
                entitiesPlayingMusic.remove(e);
            }
        }
    }

    public static String getEntityChannel(Entity entity) {
        return "entity_" + entity.entityId;
    }
}
