package net.cazzar.corelib.client.rendering;

import net.cazzar.corelib.client.model.ModelCheetah;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * @Author: Cayde
 */
public class RenderPlayer extends net.minecraft.client.renderer.entity.RenderPlayer {
    ModelCheetah cheetah;
    static ResourceLocation cheetahSkin = new ResourceLocation("cazzarcore:Cheetah");

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        if (((EntityPlayer) par1Entity).username.equals("cazzar")) {
            return cheetahSkin;
        }
        return super.getEntityTexture(par1Entity);
    }

    @Override
    protected void renderSpecials(AbstractClientPlayer clientPlayer, float par2) {
        super.renderSpecials(clientPlayer, par2);

    }
}
