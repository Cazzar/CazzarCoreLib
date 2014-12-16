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


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Method;
import java.util.List;

//import net.minecraft.client.gui.GuiButton;

@SuppressWarnings("UnusedDeclaration")
@SideOnly(Side.CLIENT)
public class TexturedButton extends GuiButton {

    private static Method drawCreativeTabText;
    private ResourceLocation textureFile;
    private int xOffset, yOffset, yOffsetForDisabled,
            xOffsetForDisabled, xOffsetForHovered, yOffsetForHovered;
//    private final GuiContainer gui;
    private List<String> tooltip;

    public TexturedButton() {
        super();
    }

    /**
     * A simple textured button for GUIs
     *
     * @param gui                the GUI that the button is on, normally <i>this</i>
     * @param id                 the button ID
     * @param xPosition          the X position on the GUI
     * @param yPosition          The Y position on the GUI
     * @param width              The button's width
     * @param height             The button's height
     * @param textureFile        The Button's texture file {@link net.minecraft.util.ResourceLocation}
     * @param xOffset            the X offset for enabled
     * @param yOffset            the Y offset for enabled
     * @param xOffsetForDisabled the X offset for Disabled
     * @param yOffsetForDisabled the Y offset for disabled
     * @param xOffsetForHovered  the X offset for when the button is hovered over
     * @param yOffsetForHovered  the Y offset for when the button is hovered over.'
     *
     * @deprecated Use the new system, setters.
     */
    @Deprecated
    public TexturedButton(@NotNull GuiContainer gui, int id, int xPosition, int yPosition, int width, int height, ResourceLocation textureFile, int xOffset, int yOffset, int xOffsetForDisabled, int yOffsetForDisabled, int xOffsetForHovered, int yOffsetForHovered) {
        super(id, xPosition, yPosition, width, height, "");
//        this.gui = gui;
        setOwner(gui);
        this.textureFile = textureFile;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xOffsetForDisabled = xOffsetForDisabled;
        this.yOffsetForDisabled = yOffsetForDisabled;
        this.xOffsetForHovered = xOffsetForHovered;
        this.yOffsetForHovered = yOffsetForHovered;
        this.tooltip = Lists.newArrayList();
    }

    @Override
    //drawButton
    public void drawButton(@NotNull Minecraft mc, int mouseX, int mouseY) {
        if (visible) {
            mc.renderEngine.bindTexture(textureFile);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            //field_146128_h = xPos
            //field_146129_i = yPos
            //field_146120_f = width
            //field_146121_g = height
//            field_146123_n = x >= xPosition && y >= yPosition && x < xPosition + width && y < yPosition + height;
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;


            switch (getHoverState(hovered)) {
                case 0:
                    // Disabled
                    drawTexturedModalRect(xPosition, yPosition, xOffsetForDisabled, yOffsetForDisabled, width, height);
                    break;
                case 1:
                    // not hovering
                    drawTexturedModalRect(xPosition, yPosition, xOffset, yOffset, width, height);
                    break;
                case 2:
                    // hovering
                    drawTexturedModalRect(xPosition, yPosition, xOffsetForHovered, yOffsetForHovered, width, height);
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
            if (drawCreativeTabText == null)
                drawCreativeTabText = ReflectionHelper.findMethod(GuiScreen.class, getOwner(),
                        new String[]{"drawHoveringText", "func_146283_a"},
                        List.class, int.class, int.class);

            drawCreativeTabText.invoke(getOwner(), tooltip, x, y);
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
        return width;
    }

    /**
     * Gets the height of the button.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the tooltip of the button.
     *
     * @return the tooltip
     * @deprecated {@link #getTooltipList()}
     */
    @Deprecated
    public String getTooltip() {
        return Joiner.on('\n').join(tooltip);
    }

    /**
     * Gets the tooltip of the button.
     *
     * @return the tooltip
     */
    public List<String> getTooltipList() {
        return tooltip;
    }

    /**
     * Set the tooltip of the button
     *
     * @param tooltip the new tooltip
     */
    public void setTooltip(String tooltip) {
        this.tooltip = Lists.newArrayList(tooltip.split("\n"));
    }

    public void setTooltip(String... tooltip) {
        this.tooltip = Lists.newArrayList(tooltip);
    }

    @NotNull
    public TexturedButton setDisabledOffsets(int x, int y) {
        xOffsetForDisabled = x;
        yOffsetForDisabled = y;
        return this;
    }
    @NotNull
    public TexturedButton setHoveredOffsets(int x, int y) {
        xOffsetForHovered = x;
        yOffsetForHovered = y;
        return this;
    }
    @NotNull
    public TexturedButton setOffsets(int x, int y) {
        xOffset = x;
        yOffset = y;
        return this;
    }
    @NotNull
    public TexturedButton setTexture(ResourceLocation newTex) {
        this.textureFile = newTex;
        return this;
    }
}
