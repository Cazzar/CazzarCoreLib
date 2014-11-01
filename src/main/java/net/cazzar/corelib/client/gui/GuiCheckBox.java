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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class GuiCheckBox extends GuiButton {
    final ResourceLocation texture = new ResourceLocation("cazzarcore", "textures/gui/test.png");

    boolean checked;

    public GuiCheckBox() {
        super();
    }

    public GuiCheckBox(int id, int xPosition, int yPosition, String display) {
        this(id, xPosition, yPosition, display, false);
    }

    public GuiCheckBox(int id, int xPosition, int yPosition, String display, boolean checked) {
        super(id, xPosition, yPosition, display);

        this.checked = checked;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        mc.renderEngine.bindTexture(texture);
        final Tessellator tess = Tessellator.instance;

        drawTexturedModalRect(xPosition, yPosition, checked ? 0 : 22, 0, 22, 22);
        drawString(mc.fontRendererObj, displayString, xPosition + 23, yPosition + 7, 0x404040);
    }


    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (mouseX > xPosition && mouseX < (xPosition + 22)
                && mouseY > yPosition && mouseY < (yPosition + 22)) {
            //Toggle check state
            this.checked = !this.checked;
            playPressSound(mc.getSoundHandler());
        }
        return false;
    }

    public void setOnClicked() {
    }
}

