package net.cazzar.corelib.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * A basic event for the Player logging out.
 */
public class PlayerLogoutEvent extends PlayerEvent {
    /**
     * A basic event for the Player logging out.
     */
    public PlayerLogoutEvent(EntityPlayer player) {
        super(player);
    }

}
