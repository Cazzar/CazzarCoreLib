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

package net.cazzar.corelib.client.gui;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SuppressWarnings("UnusedDeclaration")
@SideOnly(Side.CLIENT)
public class TexturedButton extends GuiButton {

    private final ResourceLocation textureFile;
    private final int xOffset, yOffset, yOffsetForDisabled,
            xOffsetForDisabled, xOffsetForHovered, yOffsetForHovered;
    private final GuiContainer gui;
    private String tooltip;

    /**
     * A simple textured button for GUIs
     *
     * @param gui                the GUI that the button is on, normally {@link this}
     * @param id                 the button ID
     * @param xPosition          the X position on the GUI
     * @param yPosition          The Y position on the GUI
     * @param width              The button's width
     * @param height             The button's height
     * @param textureFile        The Button's texture file {@link ResourceLocation}
     * @param xOffset            the X offset for enabled
     * @param yOffset            the Y offset for enabled
     * @param xOffsetForDisabled the X offset for Disabled
     * @param yOffsetForDisabled the Y offset for disabled
     * @param xOffsetForHovered  the X offset for when the button is hovered over
     * @param yOffsetForHovered  the Y offset for when the button is hovered over.
     */
    public TexturedButton(GuiContainer gui, int id, int xPosition, int yPosition, int width, int height, ResourceLocation textureFile, int xOffset, int yOffset, int xOffsetForDisabled, int yOffsetForDisabled, int xOffsetForHovered, int yOffsetForHovered) {
        super(id, xPosition, yPosition, width, height, "");
        this.gui = gui;
        this.textureFile = textureFile;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xOffsetForDisabled = xOffsetForDisabled;
        this.yOffsetForDisabled = yOffsetForDisabled;
        this.xOffsetForHovered = xOffsetForHovered;
        this.yOffsetForHovered = yOffsetForHovered;
        this.tooltip = "";
    }

    @Override
    //drawButton
    public void func_146112_a(Minecraft mc, int x, int y) {
        if (field_146125_m) {
            mc.renderEngine.bindTexture(textureFile);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            //field_146128_h = xPos
            //field_146129_i = yPos
            //field_146120_f = width
            //field_146121_g = height
            field_146123_n = x >= field_146128_h && y >= field_146129_i && x < field_146128_h + field_146120_f && y < field_146129_i + field_146121_g;

            switch (func_146114_a(field_146123_n)) {
                case 0:
                    // Disabled
                    drawTexturedModalRect(field_146128_h, field_146129_i, xOffsetForDisabled, yOffsetForDisabled, field_146120_f, field_146121_g);
                    break;
                case 1:
                    // not hovering
                    drawTexturedModalRect(field_146128_h, field_146129_i, xOffset, yOffset, field_146120_f, field_146121_g);
                    break;
                case 2:
                    // hovering
                    drawTexturedModalRect(field_146128_h, field_146129_i, xOffsetForHovered, yOffsetForHovered, field_146120_f, field_146121_g);
                    break;
            }
        }
    }

    /**
     * Draw the tooltip text at the x and y coordinates.
     *
     * @param x the x pos of the cursor
     * @param y the y pos of the cursor
     */
    public void drawToolTip(int x, int y) {
        try {
            ReflectionHelper.findMethod(GuiContainer.class, gui, new String[]{"drawCreativeTabHoveringText", "func_74190_a"}, String.class, int.class, int.class).invoke(gui, tooltip, x, y);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the width of the button
     *
     * @return The width
     */
    public int getWidth() {
        return field_146120_f;
    }

    /**
     * Gets the height of the button.
     *
     * @return the height
     */
    public int getHeight() {
        return field_146121_g;
    }

    /**
     * Gets the tooltip of the button.
     *
     * @return the tooltip
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * Set the tooltip of the button
     *
     * @param tooltip the new tooltip
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }
}