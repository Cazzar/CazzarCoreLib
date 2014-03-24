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
