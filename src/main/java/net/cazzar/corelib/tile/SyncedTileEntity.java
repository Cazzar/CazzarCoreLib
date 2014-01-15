package net.cazzar.corelib.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class SyncedTileEntity extends TileEntity {
    @Override
    public Packet func_145844_m() {
        NBTTagCompound tag = new NBTTagCompound();
        func_145841_b(tag);
        return new S35PacketUpdateTileEntity(field_145851_c, field_145848_d, field_145849_e, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        func_145839_a(pkt.func_148857_g());
    }
}
