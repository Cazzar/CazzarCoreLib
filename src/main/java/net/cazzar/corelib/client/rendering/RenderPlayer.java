package net.cazzar.corelib.client.rendering;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * @Author: Cayde
 */
public class RenderPlayer extends net.minecraft.client.renderer.entity.RenderPlayer {
//    ModelCheetah cheetah;
    static ResourceLocation cheetahSkin = new ResourceLocation("cazzarcore", "textures/entity/Cheetah.png");

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        if (((EntityPlayer) par1Entity).func_146103_bH().getName().equals("cazzar")) {
            //return cheetahSkin;
        }
        return super.getEntityTexture(par1Entity);
    }

}
