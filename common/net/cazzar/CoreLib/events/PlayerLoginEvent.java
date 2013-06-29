package net.cazzar.CoreLib.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerLoginEvent extends PlayerEvent {

	public PlayerLoginEvent(EntityPlayer player) {
		super(player);
	}
}
