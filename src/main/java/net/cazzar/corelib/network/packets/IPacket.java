package net.cazzar.corelib.network.packets;

import io.netty.buffer.ByteBuf;

public interface IPacket {
    public abstract void read(ByteBuf in);
    public abstract void write(ByteBuf out);
}
