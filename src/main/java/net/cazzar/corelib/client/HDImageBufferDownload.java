/*
 * Copyright (C) 2014 Cayde Dixon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.cazzar.corelib.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.IImageBuffer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A pre 1.6 method of getting HD skins.
 */
@SuppressWarnings("UnusedDeclaration")
@SideOnly(Side.CLIENT)
public class HDImageBufferDownload implements IImageBuffer {
    @Override
    public BufferedImage parseUserSkin(BufferedImage par1BufferedImage) {
        if (par1BufferedImage == null) {
            return null;
        } else {
            int imageWidth = par1BufferedImage.getWidth(null) <= 64 ? 64 : par1BufferedImage.getWidth(null);
            int imageHeight = par1BufferedImage.getHeight(null) <= 32 ? 32 : par1BufferedImage.getHeight(null);

            BufferedImage capeImage = new BufferedImage(imageWidth, imageHeight, 2);

            Graphics graphics = capeImage.getGraphics();
            graphics.drawImage(par1BufferedImage, 0, 0, null);
            graphics.dispose();

            return capeImage;
        }
    }
}
