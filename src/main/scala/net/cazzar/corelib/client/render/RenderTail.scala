package net.cazzar.corelib.client.render

import net.minecraft.util.ResourceLocation
import net.minecraft.client.renderer.entity.RenderEntity
import net.minecraftforge.client.model.{IModelCustom, AdvancedModelLoader}
import org.lwjgl.opengl.GL11._
import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.entity.Entity
import net.cazzar.corelib.client.util.ClientUtil._

/**
 * @Author: Cayde
 */
object RenderTail {
  private[rendering] var tex: ResourceLocation = new ResourceLocation("cazzarcore:textures/tail-map.png")
  private[rendering] var obj: ResourceLocation = new ResourceLocation("cazzarcore:model/tail.obj")
}

class RenderTail extends RenderEntity {
  def this() {
    this()
    modelCustom = AdvancedModelLoader.loadModel(RenderTail.obj)
    shadowSize = 0.0F
  }

  @Override override def doRender(entity: Entity, x: Double, y: Double, z: Double, par8: Float, scale: Float) {
    glPushMatrix()
    glTranslated(x, y, z)
    glScalef(0.15F, 0.15F, 0.15F)
    glRotated(-entity.rotationYaw, 0, 1, 0)
    if (entity == mc.thePlayer) glTranslatef(0F, -4F, -0.5F)
    else glTranslatef(0F, 4F, -0.5F)
    if (entity.isSneaking) {
      val f: Float = if (entity == mc.thePlayer) -1 else 1
      glTranslatef(0, 0, -1F)
      glRotatef(30F, 1, 0, 0)
    }
    mc.renderEngine.bindTexture(RenderTail.tex)
    modelCustom.renderAll()
    glPopMatrix()
  }

  @Override protected override def getEntityTexture(entity: Entity): ResourceLocation = {
    AbstractClientPlayer.locationStevePng
  }

  private[rendering] var modelCustom: IModelCustom = null
}


