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

package net.cazzar.corelib.client.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A custom slot that basically allows for restrictive slots
 */
public class RestrictedSlot extends Slot {
    List<Class<? extends Item>> allowed;

    /**
     * @param par1IInventory The inventory relating to the slot
     * @param index          the index of the slot
     * @param xPosition      the x position of the slot
     * @param yPosition      the position of the slot
     * @param allowed        the List that is allowed for the slot itself.
     */
    public RestrictedSlot(@NotNull IInventory par1IInventory, int index, int xPosition, int yPosition,  @NotNull List<Class<? extends Item>> allowed) {
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

