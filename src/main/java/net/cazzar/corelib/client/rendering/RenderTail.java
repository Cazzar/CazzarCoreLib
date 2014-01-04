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
 * @Author: Cayde
 */
public class RenderTail extends RenderEntity {
    //    ModelTail model = new ModelTail();
    IModelCustom modelCustom;

    public RenderTail() {
        modelCustom = AdvancedModelLoader.loadModel(new ResourceLocation("cazzarcore:model/tail.obj"));
        shadowSize = 0.0F;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float par8, float scale) {
        glPushMatrix();
//        glEnable(GL_BLEND);
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glTranslatef((float)x, (float)y, (float)z);
        glScalef(0.15F, 0.15F, 0.15F);
        glRotated(-entity.rotationYaw, 0, 1, 0);
//        LogHelper.coreLog.info("%s", par8);

        if (entity == mc().thePlayer) glTranslatef(0F, -4F, -0.5F); else glTranslatef(0F, 4F, -0.5F);

        mc().renderEngine.bindTexture(new ResourceLocation("cazzarcore:tail-map.png"));
        modelCustom.renderAll();

//        glDisable(GL_BLEND);
        glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return AbstractClientPlayer.locationStevePng;
    }
}
