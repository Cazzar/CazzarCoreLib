package net.cazzar.corelib.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public interface IPacket {
    public void read(ByteBuf in);

    public void handleClient(EntityPlayer player);

    public void handleServer(EntityPlayer player);

    public void write(ByteBuf out);
}
