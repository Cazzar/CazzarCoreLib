package net.cazzar.corelib.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class TexturedButton extends GuiButton {

    private final ResourceLocation	textureFile;
    private final int				xOffset, yOffset, yOffsetForDisabled,
            xOffsetForDisabled, xOffsetForHovered, yOffsetForHovered;
    private final GuiContainer gui;
    private String tooltip;

    public TexturedButton(GuiContainer gui, int id, int xPosition, int yPosition, int width,
                          int height, ResourceLocation textureFile, int xOffset, int yOffset,
                          int xOffsetForDisabled, int yOffsetForDisabled,
                          int xOffsetForHovered, int yOffsetForHovered) {

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
    public void drawButton(Minecraft mc, int x, int y) {
        if (drawButton) {
            // mc.renderEngine.bindTexture(textureFile);
            mc.renderEngine.func_110577_a(textureFile);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            field_82253_i = x >= xPosition && y >= yPosition
                    && x < xPosition + width && y < yPosition + height;

            // int hoverStatus = this.getHoverState(this.field_82253_i);
            // Args: x, y, xOffset, yOffset, Width, Height

            switch (getHoverState(field_82253_i)) {
                case 0:
                    // Disabled
                    drawTexturedModalRect(xPosition, yPosition,
                            xOffsetForDisabled, yOffsetForDisabled, width,
                            height);
                    break;
                case 1:
                    // not hovering
                    drawTexturedModalRect(xPosition, yPosition, xOffset,
                            yOffset, width, height);
                    break;
                case 2:
                    // hovering
                    drawTexturedModalRect(xPosition, yPosition,
                            xOffsetForHovered, yOffsetForHovered, width, height);

                    //drawToolTip(x, y);
                    break;
            }

            // this.drawTexturedModalRect(this.xPosition, this.yPosition,
            // this.xOffset, this.yOffset, this.width, this.height);


        }
    }

    public void drawToolTip(int x, int y) {
        this.gui.drawCreativeTabHoveringText(tooltip, x, y);
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTooltip() {
        return tooltip;
    }
}