package net.cazzar.corelib.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;

public class InventoryUtils {

    /**
     * Read the items from NBT
     *
     * @param items   the items
     * @param tagList the NBT tag list
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
    public static NBTTagList writeItemStacksToTag(ItemStack[] items,
                                                  int maxQuantity) {
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
