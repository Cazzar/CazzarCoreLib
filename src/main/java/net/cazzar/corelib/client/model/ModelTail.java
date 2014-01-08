// Date: 16/11/2013 12:00:51 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX


package net.cazzar.corelib.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelTail extends ModelBase {
    //fields
    ModelRenderer tailBase;
    ModelRenderer tailMiddle;
    ModelRenderer tailTip;

    public ModelTail() {
        textureWidth = 64;
        textureHeight = 32;

        tailBase = new ModelRenderer(this, 54, 0);
        tailBase.addBox(-1F, 0F, -1F, 2, 3, 2);
        tailBase.setRotationPoint(0F, 9F, 1F);
        tailBase.setTextureSize(64, 32);
        tailBase.mirror = true;
        setRotation(tailBase, 0.7656236F, 0F, 0F);

        tailMiddle = new ModelRenderer(this, 46, 0);
        tailMiddle.addBox(-1F, 0F, -1F, 2, 7, 2);
        tailMiddle.setRotationPoint(0F, 10.9F, 3F);
        tailMiddle.setTextureSize(64, 32);
        tailMiddle.mirror = true;
        setRotation(tailMiddle, 1.289222F, 0F, 0F);

        tailTip = new ModelRenderer(this, 38, 0);
        tailTip.addBox(-1F, 0F, -1F, 2, 3, 2);
        tailTip.setRotationPoint(0F, 13F, 9.3F);
        tailTip.setTextureSize(64, 32);
        tailTip.mirror = true;
        setRotation(tailTip, 1.745329F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        tailBase.render(f5);
        tailMiddle.render(f5);
        tailTip.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}