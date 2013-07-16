package net.cazzar.corelib.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * A basic event for the Player logging in.
 */
public class PlayerLoginEvent extends PlayerEvent {

    /**
     * A basic event for the Player logging in.
     */
    public PlayerLoginEvent(EntityPlayer player) {
        super(player);
    }
}
