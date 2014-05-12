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

/**
 *
 */
package net.cazzar.corelib.creative;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * A generic and simple creative tab.
 */
@SuppressWarnings("UnusedDeclaration")
public class GenericCreativeTab extends CreativeTabs {
    final Item icon;

    /**
     * A generic and simple creative tab.
     *
     * @param label the label for the itemGroup.<i>label</i>
     * @param icon  the Item/Block ID for
     */
    public GenericCreativeTab(String label, Item icon) {
        super(label);
        this.icon = icon;
    }

    @Override
    public Item getTabIconItem() {
        return icon;
    }
}
