package net.cazzar.CoreLib.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerChangedDimensionEvent extends PlayerEvent {

	public PlayerChangedDimensionEvent(EntityPlayer player) {
		super(player);
	}
}
