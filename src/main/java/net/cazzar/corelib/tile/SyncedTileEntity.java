package net.cazzar.corelib.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * A automatically syncing tile entity class using NBT Data allowing you to add extra
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class SyncedTileEntity extends TileEntity {
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        addExtraNBTToPacket(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
        readExtraNBTFromPacket(pkt.func_148857_g());
    }

    /**
     * Add extra information to the Packet before it is sent
     * @param tag the tag to write to
     */
    public abstract void addExtraNBTToPacket(NBTTagCompound tag);

    /**
     * Read the extra information off the packet when it is received from the server.
     * @param tag the tag to read from
     */
    public abstract void readExtraNBTFromPacket(NBTTagCompound tag);
}
