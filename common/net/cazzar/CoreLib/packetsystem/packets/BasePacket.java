package net.cazzar.corelib.packetsystem.packets;

import java.util.HashMap;
import java.util.Map.Entry;

import net.cazzar.corelib.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public abstract class BasePacket {
	public static class ProtocolException extends Exception {
		
		private static final long	serialVersionUID	= 4758559873161416283L;
		
		public ProtocolException() {}
		
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
	
	private static HashMap<Integer, Class<? extends BasePacket>>	idMap	= new HashMap<Integer, Class<? extends BasePacket>>();
	
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
	
	public BasePacket() {}
}
