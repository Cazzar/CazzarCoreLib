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

package net.cazzar.corelib.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;

@SuppressWarnings("UnusedDeclaration")
public class InventoryUtils {

    /**
     * Read the Items from the specified NBT data to an array
     *
     * @param items   the items to read
     * @param tagList the tag to read
     */
    public static void readItemStacksFromTag(ItemStack[] items,
                                             NBTTagList tagList) {
        for (int i = 0; i < tagList.tagCount(); i++) {
            final NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
            final int b = tag.getShort("Slot");
            items[b] = ItemStack.loadItemStackFromNBT(tag);
            if (tag.hasKey("Quantity")) {
                final NBTBase qtag = tag.getTag("Quantity");
                if (qtag instanceof NBTTagInt) items[b].stackSize = ((NBTTagInt) qtag).data;
                else if (qtag instanceof NBTTagShort)
                    items[b].stackSize = ((NBTTagShort) qtag).data;
            }
        }
    }

    /**
     * Write the items to a NBTTagList
     *
     * @param items the items to write
     *
     * @return the generated tag list
     */
    public static NBTTagList writeItemStacksToTag(ItemStack[] items) {
        return writeItemStacksToTag(items, 64);
    }

    /**
     * Write the items to a NBTTagList
     *
     * @param items       the items to write
     * @param maxQuantity the max stack size to write
     *
     * @return the generated tag list
     */
    public static NBTTagList writeItemStacksToTag(ItemStack[] items, @SuppressWarnings("SameParameterValue") int maxQuantity) {
        final NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < items.length; i++)
            if (items[i] != null) {
                final NBTTagCompound tag = new NBTTagCompound();
                tag.setShort("Slot", (short) i);
                items[i].writeToNBT(tag);

                if (maxQuantity > Short.MAX_VALUE) tag.setInteger("Quantity",
                        items[i].stackSize);
                else if (maxQuantity > Byte.MAX_VALUE)
                    tag.setShort("Quantity", (short) items[i].stackSize);

                tagList.appendTag(tag);
            }
        return tagList;
    }
}
