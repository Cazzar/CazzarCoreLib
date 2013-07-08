package net.cazzar.corelib.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.IPlayerTracker;

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
