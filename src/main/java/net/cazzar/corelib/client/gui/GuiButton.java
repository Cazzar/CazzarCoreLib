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

import com.google.common.collect.Lists;
import net.cazzar.corelib.client.gui.handler.IGUIAction;
import net.cazzar.corelib.client.gui.handler.IGUIAdvancedAction;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.util.List;

/**
 * @author Cayde
 */
public class GuiButton extends net.minecraft.client.gui.GuiButton {
    List<IGUIAction> basicActions = Lists.newLinkedList();
    List<IGUIAdvancedAction> advancedActions = Lists.newLinkedList();
    private GuiContainer owner;

    public GuiButton() {
        this(-2 , 0, 0, "");
    }

    public GuiButton(int id, int x, int y, String display) {
        super(id, x, y, display);
    }

    public GuiButton(int id, int xPosition, int yPosition, int width, int height, String s) {
        super(id, xPosition, yPosition, width, height, s);
    }

    public GuiButton setDisplayString(String displayString) {
        this.displayString = displayString;
        return this;
    }

    public GuiButton setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        return this;
    }

    public GuiButton addListener(IGUIAction action) {
        basicActions.add(action);
        return this;
    }

    public GuiButton addListener(IGUIAdvancedAction action) {
        advancedActions.add(action);
        return this;
    }

    @Override
    public void mouseReleased(int x, int y) {
        for (IGUIAction basicAction : basicActions) basicAction.click();
        for (IGUIAdvancedAction basicAction : advancedActions) basicAction.click(x, y);
    }

    public GuiContainer getOwner() {
        return owner;
    }

    public GuiButton setOwner(GuiContainer owner) {
        this.owner = owner;
        return this;
    }

    public GuiButton setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }
}
