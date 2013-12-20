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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

/**
 * A stub class for a slider in minecraft
 */
@SuppressWarnings("UnusedDeclaration")
@SideOnly(Side.CLIENT)
public class GuiSlider extends GuiButton {
    /**
     * The value of this slider control.
     */
    public float sliderValue = 1.0F;
    /**
     * Is this slider control being dragged.
     */
    public boolean dragging;

    public GuiSlider(int id, int x, int y, int width, int height, String displayString, float sliderValue) {
        super(id, x, y, width, height, displayString);
        this.sliderValue = sliderValue;
    }

    /**
     * @return 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean par1) {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
        if (this.drawButton) {
            System.out.print(this.dragging);
            if (this.dragging) {
                this.sliderValue = (float) (par2 - (this.xPosition + 4)) / (float) (this.width - 8);

                if (this.sliderValue < 0.0F) {
                    this.sliderValue = 0.0F;
                }

                if (this.sliderValue > 1.0F) {
                    this.sliderValue = 1.0F;
                }

                //par1Minecraft.gameSettings.setOptionFloatValue(this.idFloat, this.sliderValue);
                //this.displayString = par1Minecraft.gameSettings.getKeyBinding(this.idFloat);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    /**
     * @return true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
        if (super.mousePressed(par1Minecraft, par2, par3)) {
            this.sliderValue = (float) (par2 - (this.xPosition + 4)) / (float) (this.width - 8);

            if (this.sliderValue < 0.0F) {
                this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F) {
                this.sliderValue = 1.0F;
            }

            //par1Minecraft.gameSettings.setOptionFloatValue(this.idFloat, this.sliderValue);
            //this.displayString = par1Minecraft.gameSettings.getKeyBinding(this.idFloat);
            this.dragging = true;
            return true;
        } else {
            this.dragging = false;
            return false;
        }
    }

    /**
     * Fired when the mouse is released
     * @param par1 the GUI X coordinate
     * @param par2 the GUI Y coordinate
     */
    @Override
    public void mouseReleased(int par1, int par2) {
        this.dragging = false;
    }
}
