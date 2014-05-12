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
