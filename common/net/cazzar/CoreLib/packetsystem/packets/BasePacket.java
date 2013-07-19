/*
 * Copyright (C) 2013 cazzar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package net.cazzar.corelib.packetsystem.packets;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import net.cazzar.corelib.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

import java.util.HashMap;
import java.util.Map.Entry;

public abstract class BasePacket {
    public static class ProtocolException extends Exception {

        private static final long serialVersionUID = 4758559873161416283L;

        public ProtocolException() {
        }

        public ProtocolException(String message) {
            super(message);
        }

        public ProtocolException(String message, Throwable cause) {
            super(message, cause);
        }

        public ProtocolException(Throwable cause) {
            super(cause);
        }
    }

    private static HashMap<Integer, Class<? extends BasePacket>> idMap = new HashMap<Integer, Class<? extends BasePacket>>();

    private static int getNextPacketID() {
        return idMap.size() - 1;
    }

    public static BasePacket constructPacket(int packetId)
            throws ProtocolException, InstantiationException,
            IllegalAccessException {
        final Class<? extends BasePacket> clazz = idMap.get(Integer
                .valueOf(packetId));
        if (clazz == null) throw new ProtocolException("Unknown Packet Id!");
        else return clazz.newInstance();
    }

    public abstract void execute(EntityPlayer player, Side side)
            throws ProtocolException;

    public final int getPacketId() {
        for (Entry<Integer, Class<? extends BasePacket>> entry : idMap
                .entrySet()) {
            if (entry.getValue() == getClass()) return entry.getKey();
        }
        throw new RuntimeException("Packet " + getClass().getSimpleName()
                + " is missing a mapping!");
    }

    public final Packet makePacket() {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeByte(getPacketId());
        write(out);
        return PacketDispatcher.getPacket(Reference.CHANNEL_NAME,
                out.toByteArray());
    }

    public abstract void read(ByteArrayDataInput in);

    public void sendToAllInDimension(int dimID) {
        PacketDispatcher.sendPacketToAllInDimension(makePacket(), dimID);
    }

    public void sendToAllInDimension(Player player) {
        PacketDispatcher.sendPacketToPlayer(makePacket(), player);
    }

    public void sendToAllPlayers() {
        PacketDispatcher.sendPacketToAllPlayers(makePacket());
    }

    public void sendToServer() {
        PacketDispatcher.sendPacketToServer(makePacket());
    }

    public void register() {
        if (idMap.containsValue(getClass()))
            throw new RuntimeException(String.format(
                    "The packet \"%s\" is already registered", getClass()
                    .getSimpleName()));

        int id = getNextPacketID();
        idMap.put(id, getClass());
    }

    public abstract void write(ByteArrayDataOutput out);

    public BasePacket() {
    }
}
