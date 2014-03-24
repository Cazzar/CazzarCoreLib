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

package net.cazzar.corelib.client.rendering;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import static net.cazzar.corelib.util.ClientUtil.mc;
import static org.lwjgl.opengl.GL11.*;

/**
 * The remderer for the tail entity on certain players
 */
public class RenderTail extends RenderEntity {
    static ResourceLocation tex = new ResourceLocation("cazzarcore:textures/tail-map.png");
    IModelCustom modelCustom;

    public RenderTail() {
        ResourceLocation location = new ResourceLocation("cazzarcore:model/tail.obj");
        modelCustom = AdvancedModelLoader.loadModel(location);
        shadowSize = 0.0F;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float par8, float scale) {
        glPushMatrix();

        glTranslatef((float) x, (float) y, (float) z);
        glScalef(0.15F, 0.15F, 0.15F);
        glRotated(-entity.rotationYaw, 0, 1, 0);

        if (entity == mc().thePlayer) glTranslatef(0F, -4F, -0.5F);
        else glTranslatef(0F, 4F, -0.5F);

        if (entity.isSneaking()) {
            glTranslatef(0, 0, -1F);
            glRotatef(30F, 1, 0, 0);
        }

        mc().renderEngine.bindTexture(tex);
        modelCustom.renderAll();

        glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return AbstractClientPlayer.locationStevePng;
    }
}
