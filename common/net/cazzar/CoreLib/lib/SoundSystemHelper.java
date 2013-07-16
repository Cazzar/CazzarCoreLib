package net.cazzar.corelib.lib;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cazzar.corelib.util.CommonUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.item.ItemRecord;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlayStreamingEvent;
import net.minecraftforge.client.event.sound.PlayStreamingSourceEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import paulscode.sound.SoundSystem;

public class SoundSystemHelper {

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
        if (CommonUtil.isServer()) return false;
        return getSoundSystem() != null;
    }

    /**
     * play a record at the specific location
     *
     * @param record     the record name
     * @param world      the world
     * @param x          the x pos
     * @param y          the y pos
     * @param z          the z pos
     * @param volume     the volume to play at
     * @param identifier the identifier to play at.
     */
    public static void playRecord(String record, World world, float x, float y,
                                  float z, float volume, String identifier) {
        if (!isSoundEnabled()) return;

        SoundSystem sndSystem = getSoundSystem();

        ItemRecord itemrecord = ItemRecord.getRecord(record);
        if (itemrecord == null) return;

        Minecraft.getMinecraft().ingameGUI.setRecordPlayingMessage(itemrecord
                .getRecordTitle());
        if (sndSystem.playing(identifier)) sndSystem.stop(identifier);

        SoundPoolEntry song = getSoundManager().soundPoolStreaming
                .getRandomSoundFromSoundPool(record);
        song = SoundEvent.getResult(new PlayStreamingEvent(getSoundManager(),
                song, identifier, x, y, z));

        if (song == null) return;
        if (sndSystem.playing("BgMusic")) sndSystem.stop("BgMusic");

        float f3 = 16.0F;
        sndSystem.newStreamingSource(true, identifier, song.func_110457_b(),
                song.func_110458_a(), false, x, y, z, 2, f3 * 4.0F);

        sndSystem.setVolume(identifier, volume * Minecraft.getMinecraft().gameSettings.soundVolume);

        MinecraftForge.EVENT_BUS.post(new PlayStreamingSourceEvent(
                getSoundManager(), identifier, x, y, z));
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
     * @param identifier the identifier to check
     */
    public static boolean isPlaying(String identifier) {
        if (!isSoundEnabled()) return false;
        return getSoundSystem().playing(identifier);
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
}
