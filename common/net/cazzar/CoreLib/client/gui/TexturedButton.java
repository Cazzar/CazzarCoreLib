package net.cazzar.corelib.client.gui;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

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
        //this.gui.drawCreativeTabHoveringText(tooltip, x, y);
        try {
            ReflectionHelper.findMethod(GuiContainer.class, gui, new String[]{"drawCreativeTabHoveringText", "func_74190_a"}, String.class, int.class, int.class).invoke(gui, tooltip, x, y);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set the tooltip of the button
     *
     * @param tooltip the tooltip
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
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
     */
    public String getTooltip() {
        return tooltip;
    }
}