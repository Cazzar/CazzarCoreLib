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

package net.cazzar.corelib.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

import static java.util.Collections.addAll;

/**
 * A custom record for simple registration
 */
@SuppressWarnings("UnusedDeclaration")
public class ItemCustomRecord extends ItemRecord {
    final String recordInfo;
    final String[] details;
    String domain = "minecraft";
    EnumRarity rarity = null;

    /**
     * Initialise the ItemCustomRecord class
     *
     * @param recordFile the <i>domain:file.format</i> format for the record location
     * @param recordInfo the name of the record
     * @param ext        the file extension
     * @param details    the extra lore for the record
     */
    public ItemCustomRecord(String recordFile, String ext, String recordInfo, String... details) {
        this(recordFile, recordInfo, details);
    }

    public ItemCustomRecord(String file, String name, String... details) {
        super(file);
        setUnlocalizedName("record");
        this.recordInfo = name;
        this.details = details;
//        if (ClientUtil.isClient()) SoundSystemHelper.registerRecord(recordFile + '.' + ext);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer par2EntityPlayer,
                               List list, boolean par4) {
        list.add(getRecordNameLocal());
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

    /**
     * Retrieves the resource location of the sound to play for this record.
     *
     * @param name The name of the record to play
     *
     * @return The resource location for the audio, null to use default.
     */
    @Override
    public ResourceLocation getRecordResource(String name) {
        return new ResourceLocation(domain, "records." + recordName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getRecordNameLocal() {
        return recordInfo;
    }

    /**
     * Set the rarity of the
     *
     * @param rarity the new rarity of the record.
     */
    @SideOnly(Side.CLIENT)
    public void setRarity(EnumRarity rarity) {
        this.rarity = rarity;
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
//        super.registerIcons(par1IconRegister);
        itemIcon = par1IconRegister.registerIcon(domain + ":records." + recordName);
    }

    public ItemCustomRecord setDomain(String domain) {
        if (domain.indexOf(':') == -1) this.domain = domain;
        else this.domain = domain.substring(0, domain.indexOf(':'));

        return this;
    }
}
