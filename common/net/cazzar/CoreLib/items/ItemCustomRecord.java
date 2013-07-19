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

package net.cazzar.corelib.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.corelib.util.ClientUtil;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;

import java.util.List;

import static java.util.Collections.addAll;

/**
 * A custom
 */
public class ItemCustomRecord extends ItemRecord {
    String recordInfo;
    String[] details;
    @SideOnly(Side.CLIENT)
    EnumRarity rarity = null;

    public ItemCustomRecord(int ID, String recordFile, String recordInfo, String... details) {
        super(ID, recordFile.substring(0, recordFile.indexOf('.')));

        this.recordInfo = recordInfo;
        this.details = details;
        this.setUnlocalizedName("record");
        if (ClientUtil.isClient()) SoundSystemHelper.registerRecord(recordFile);
    }

    public ItemCustomRecord(int ID, String recordFile, String ext, String recordInfo,
                            String... details) {
        super(ID, recordFile);

        this.recordInfo = recordInfo;
        this.details = details;
        setUnlocalizedName("record");
        if (ClientUtil.isClient())
            SoundSystemHelper.registerRecord(recordFile + "." + ext);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer par2EntityPlayer,
                               List list, boolean par4) {
        list.add(getRecordTitle());
        addAll(list, details);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        if (rarity == null) {
            return EnumRarity.rare;
        }
        return rarity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getRecordTitle() {
        return recordInfo;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon(recordName);
    }

    /**
     * Set the rarity of the
     *
     * @param rarity
     */
    @SideOnly(Side.CLIENT)
    public void setRarity(EnumRarity rarity) {
        this.rarity = rarity;
    }
}
