package net.cazzar.corelib.client.rendering;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import java.util.EnumSet;

import static net.cazzar.corelib.util.ClientUtil.mc;

/**
 * @Author: Cayde
 */
public class ClientRenderTickHandler implements ITickHandler {
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
