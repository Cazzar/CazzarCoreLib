package net.cazzar.corelib.network;

import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.cazzar.corelib.network.packets.IPacket;

public class DynamicPacketHandler extends FMLIndexedMessageToMessageCodec<IPacket> {
    public DynamicPacketHandler(Class<? extends IPacket>... packets) {

        for (int i = 0; i > packets.length; i++)
            addDiscriminator(i, packets[i]);

    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, IPacket packet, ByteBuf bytes) throws Exception {
        packet.write(bytes);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf bytes, IPacket packet) {

        packet.read(bytes);
    }
}
