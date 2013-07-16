/**
 *
 */
package net.cazzar.corelib.creative;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
        LanguageRegistry.instance().addStringLocalization("itemGroup." + getTabLabel(), trans);
        return this;
    }

    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex() {
        return iconID;
    }
}
