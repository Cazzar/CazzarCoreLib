package net.cazzar.corelib.entity;

import net.cazzar.corelib.lib.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * @Author: Cayde
 */
public class EntityTail extends Entity {
    public EntityPlayer owner;

    public EntityTail(World par1World) {
        super(par1World);
    }

    public EntityTail(EntityPlayer player) {
        super(player.worldObj);
        owner = player;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbtTagCompound) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbtTagCompound) {
    }

    @Override
    public boolean writeToNBTOptional(NBTTagCompound par1NBTTagCompound) {
        return false; //no saving for you!
    }

    public void updatePos() {
        posX = owner.posX;
        posY = owner.posY;
        posZ = owner.posZ;

        motionX = owner.motionX;
        motionY = owner.motionY;
        motionZ = owner.motionZ;

        rotationPitch = owner.rotationPitch;
        rotationYaw = owner.rotationYaw;
    }

    @Override
    public void onUpdate() {
        if (owner == null || !owner.isEntityAlive() || owner.worldObj != worldObj) {
            setDead();
            return;
        }

        updatePos();
    }

    @Override
    public void setDead() {
        LogHelper.coreLog.info("Entity dead!");
        super.setDead();
//        ClientRenderTickHandler.tails.remove(owner);
    }
}
