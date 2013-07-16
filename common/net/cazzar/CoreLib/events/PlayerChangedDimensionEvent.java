package net.cazzar.corelib.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * A basic event for the Player changing dimensions
 */
public class PlayerChangedDimensionEvent extends PlayerEvent {
    /**
     * A basic event for the Player changing dimensions
     */
    public PlayerChangedDimensionEvent(EntityPlayer player) {
        super(player);
    }
}
