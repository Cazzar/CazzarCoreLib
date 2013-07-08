package net.cazzar.corelib;

import java.util.Arrays;

import net.cazzar.corelib.events.PlayerTracker;
import net.cazzar.corelib.lib.Reference;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModContainer extends DummyModContainer {
	static ModMetadata meta;
	public ModContainer() {
		super(meta = new ModMetadata());
		
		meta.authorList = Arrays.asList("cazzar");
		meta.description = "The core library for cazzar's mods";
		meta.modId = Reference.MOD_ID;
		meta.name = "Cazzar Core Lib";
		meta.url = "http://www.cazzar.net/";
		meta.version = "@VERSION@";
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
	
	@Subscribe
	public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerPlayerTracker(new PlayerTracker());
	}
	
	@Subscribe
	public void init(FMLInitializationEvent event) {
		
	}
	
	@Subscribe
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
