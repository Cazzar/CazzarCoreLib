/**
 * 
 */
package net.cazzar.corelib.creative;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GenericCreativeTab extends CreativeTabs {
	int iconID;
	
	public GenericCreativeTab(String label, int iconID) {
		super(label);
		this.iconID = iconID;
	}
	
	public GenericCreativeTab setTranslation(String trans) {
		LanguageRegistry.instance().addStringLocalization("itemGroup." + getTabLabel(), trans);
		return this; 
	}
	
    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex()
    {
        return iconID;
    }
}
