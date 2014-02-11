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
