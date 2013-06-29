package net.cazzar.CoreLib.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerRespawnEvent extends PlayerEvent {

	public PlayerRespawnEvent(EntityPlayer player) {
		super(player);
	}
}
