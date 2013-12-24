package net.cazzar.corelib.client.rendering;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.cazzar.corelib.entity.EntityTail;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static net.cazzar.corelib.util.ClientUtil.mc;

/**
 * @Author: Cayde
 */
public class ClientRenderTickHandler implements ITickHandler {
    public static HashMap<EntityPlayer, EntityTail> tails = Maps.newHashMap();

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        if (type.equals(EnumSet.of(TickType.RENDER))) {
            if (mc().theWorld != null) {
                preRenderTick(mc(), mc().theWorld, (Float) tickData[0]);
            }
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        if (type.equals(EnumSet.of(TickType.RENDER))) {
            if (mc().theWorld != null) {
                renderTick(mc(), mc().theWorld, (Float)tickData[0]); //only ingame
            }
        }
    }

    private void renderTick(Minecraft mc, World world, Float renderTick) {
        for (Map.Entry<EntityPlayer, EntityTail> map : ClientRenderTickHandler.tails.entrySet()) {
            map.getValue().updatePos();
        }
    }
    private void preRenderTick(Minecraft mc, World world, Float renderTick) {

    }

    @Override
    public EnumSet<TickType> ticks() {

        return EnumSet.of(TickType.RENDER, TickType.PLAYER);
    }

    @Override
    public String getLabel() {
        return null;
    }
}
