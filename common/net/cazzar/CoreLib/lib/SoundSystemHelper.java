package net.cazzar.CoreLib.lib;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

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
	public static SoundManager getSoundManager() {
		return Minecraft.getMinecraft().sndManager;
	}
	
	public static SoundSystem getSoundSystem() {
		return SoundManager.sndSystem;
	}
	
	public static boolean isSoundEnabled() {
		return getSoundSystem() == null;
	}
	
	public static void registerCodecs() {
		//if (registeredCodecs)
		//	throw new RuntimeException(
		//			"We cannot register the codecs more than once!");
		//SoundSystemConfig.setCodec("mp3", CodecJLayerMP3.class);
	}
	
	
	public static void playRecord(String record, World world, float x, float y,
			float z, float volume, String identifier) {
		if (FMLCommonHandler.instance().getEffectiveSide().isServer()) return;
		if (getSoundSystem() == null) return;
		
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
		sndSystem.newStreamingSource(true, identifier, song.soundUrl,
				song.soundName, false, x, y, z, 2, f3 * 4.0F);
		
		sndSystem.setVolume(identifier, volume * Minecraft.getMinecraft().gameSettings.soundVolume);
		
		MinecraftForge.EVENT_BUS.post(new PlayStreamingSourceEvent(
				getSoundManager(), identifier, x, y, z));
		sndSystem.play(identifier);
	}
	
	public static void pause(String identifier) {
		if (FMLCommonHandler.instance().getEffectiveSide().isServer()) return;
		if (getSoundSystem() == null) return;
		
		getSoundSystem().pause(identifier);
	}
	
	public static void resume(String identifier) {
		if (FMLCommonHandler.instance().getEffectiveSide().isServer()) return;
		if (getSoundSystem() == null) return;
		
		getSoundSystem().play(identifier);
	}
	
	public static void stop(String identifier) {
		if (FMLCommonHandler.instance().getEffectiveSide().isServer()) return;
		if (getSoundSystem() == null) return;
		
		getSoundSystem().stop(identifier);
	}
	
	public static boolean isPlaying(String identifier) {
		if (FMLCommonHandler.instance().getEffectiveSide().isServer()) return false;
		if (getSoundSystem() == null) return false;
		
		return getSoundSystem().playing(identifier);
	}
}
