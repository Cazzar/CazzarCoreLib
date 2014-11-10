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

package net.cazzar.corelib.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import org.jetbrains.annotations.NotNull;

public class InventoryUtils {

    /**
     * Read the items from NBT
     *
     * @param items   the items
     * @param tagList the NBT tag list
     */
    public static void readItemStacksFromTag(@NotNull ItemStack[] items, @NotNull NBTTagList tagList) {
        for (int i = 0; i < tagList.tagCount(); i++) {
            final NBTTagCompound tag = tagList.getCompoundTagAt(i);
            final int b = tag.getShort("Slot");
            items[b] = ItemStack.loadItemStackFromNBT(tag);
            if (tag.hasKey("Quantity")) {
                final NBTBase qtag = tag.getTag("Quantity");
                if (qtag instanceof NBTTagInt) items[b].stackSize = ((NBTTagInt) qtag).func_150287_d();
                else if (qtag instanceof NBTTagShort)
                    items[b].stackSize = ((NBTTagShort) qtag).func_150289_e();
            }
        }
    }

    /**
     * Create a {@link NBTTagList} of the items
     *
     * @param items the list of items
     *
     * @return the generated tag list
     */
    public static NBTTagList writeItemStacksToTag(ItemStack[] items) {
        return writeItemStacksToTag(items, 64);
    }

    /**
     * Create a {@link NBTTagList} of the items
     *
     * @param items       the list of items
     * @param maxQuantity the max stack size.
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
