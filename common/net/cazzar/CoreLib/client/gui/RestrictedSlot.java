package net.cazzar.corelib.client.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * @Author: Cayde
 */
public class RestrictedSlot extends Slot {
    List<Class<? extends Item>> allowed;

    public RestrictedSlot(IInventory par1IInventory, int index, int xPosition, int yPosition, List<Class<? extends Item>> allowed) {
        super(par1IInventory, index, xPosition, yPosition);
        this.allowed = allowed;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        for (Class type : allowed) {
            if (itemStack.getItem().getClass().isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }
}

