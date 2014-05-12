/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Cayde Dixon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Sync {
    }
}
