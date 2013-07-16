package net.cazzar.corelib.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * A basic event for the Player respawning.
 */
public class PlayerRespawnEvent extends PlayerEvent {
    /**
     * A basic event for the Player respawning.
     */
    public PlayerRespawnEvent(EntityPlayer player) {
        super(player);
    }
}
