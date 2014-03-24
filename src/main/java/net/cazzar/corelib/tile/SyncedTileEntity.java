/*
 * Copyright (C) 2014 Cayde Dixon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.cazzar.corelib.tile;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * A automatically syncing tile entity class using NBT Data allowing you to add extra
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class SyncedTileEntity extends TileEntity {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Sync {
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        try {
            for (Field f : getClass().getDeclaredFields()) {
                if (f.getAnnotation(Sync.class) != null) {
                    f.setAccessible(true);
                    Class<?> t = f.getType();
                    if (t == Boolean.class || t == boolean.class) tag.setBoolean(f.getName(), f.getBoolean(this));
                    else if (t == String.class) tag.setString(f.getName(), (String) f.get(this));
                    else if (t == Double.class || t == double.class) tag.setDouble(f.getName(), f.getDouble(this));
                    else if (t == Integer.class || t == int.class) {
                        if (t.isArray())
                            tag.setIntArray(f.getName(), (int[]) f.get(this));
                        else
                            tag.setInteger(f.getName(), f.getInt(this));
                    } else if (t == Byte.class || t == byte.class) {
                        if (t.isArray())
                            tag.setByteArray(f.getName(), (byte[]) f.get(this));
                        else
                            tag.setByte(f.getName(), f.getByte(this));
                    } else if (t == Character.class || t == char.class) tag.setInteger(f.getName(), f.getChar(this));
                    else if (t == Float.class || t == float.class) tag.setFloat(f.getName(), f.getFloat(this));
                    else if (t == Long.class || t == long.class) tag.setLong(f.getName(), f.getLong(this));
                }
            }
        } catch (IllegalAccessException ignored) {
//            e.printStackTrace();
        }
        addExtraNBTToPacket(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
        //noinspection unchecked
        Set<String> keys = pkt.func_148857_g().func_150296_c();
        Map tagMap = ObfuscationReflectionHelper.getPrivateValue(NBTTagCompound.class, pkt.func_148857_g(), "tagMap", "field_74784_a");
        for (String key : keys) {
            try {
                Field f = this.getClass().getDeclaredField(key);
                f.set(tagMap.get(key), this);
            } catch (Exception ignored) {
//                e.printStackTrace();
            }
        }
        readExtraNBTFromPacket(pkt.func_148857_g());
    }

    /**
     * Add extra information to the Packet before it is sent
     *
     * @param tag the tag to write to
     */
    public abstract void addExtraNBTToPacket(NBTTagCompound tag);

    /**
     * Read the extra information off the packet when it is received from the server.
     *
     * @param tag the tag to read from
     */
    public abstract void readExtraNBTFromPacket(NBTTagCompound tag);
}
