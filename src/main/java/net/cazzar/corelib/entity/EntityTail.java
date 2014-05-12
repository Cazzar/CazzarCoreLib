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
