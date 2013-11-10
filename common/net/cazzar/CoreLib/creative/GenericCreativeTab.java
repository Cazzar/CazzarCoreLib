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

/**
 *
 */
package net.cazzar.corelib.creative;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cazzar.corelib.util.ClientUtil;
import net.minecraft.creativetab.CreativeTabs;

/**
 * A generic and simple creative tab.
 */
public class GenericCreativeTab extends CreativeTabs {
    int iconID;

    /**
     * A generic and simple creative tab.
     *
     * @param label  the label for the itemGroup.<i>label</i>
     * @param iconID the Item/Block ID for
     */
    public GenericCreativeTab(String label, int iconID) {
        super(label);
        this.iconID = iconID;
    }

    /**
     * Set a simple translation for the {@link GenericCreativeTab}
     *
     * @param trans the translation
     *
     * @return the tab for easy chaining setup.
     */
    public GenericCreativeTab setTranslation(String trans) {
        if (ClientUtil.isClient())
            LanguageRegistry.instance().addStringLocalization("itemGroup." + getTabLabel(), trans);
        return this;
    }

    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex() {
        return iconID;
    }
}
