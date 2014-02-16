package net.cazzar.corelib.network;

import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.cazzar.corelib.network.packets.IPacket;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.corelib.util.CommonUtil;
import net.minecraft.network.NetHandlerPlayServer;

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
        switch (CommonUtil.getSide()) {
            case CLIENT:
                packet.handleClient(ClientUtil.mc().thePlayer);
                break;
            case SERVER:
                packet.handleServer(((NetHandlerPlayServer) ctx.channel().attr(NetworkRegistry.NET_HANDLER).get()).playerEntity);
                break;
        }
//        packet.read(bytes);
    }
}
