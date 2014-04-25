/*
 * Copyright (C) 2014 Cayde Dixon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.cazzar.corelib.client.sound;

import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;

public class CustomSound implements ISound {
    private float volume;
    private ResourceLocation resource;
    private boolean repeat;
    private int repeatDelay;
    private float pitch;
    private float xPos, yPos, zPos;

    public CustomSound(float volume, ResourceLocation resource, boolean repeat, int repratDelay, float pitch, float xPos, float yPos, float zPos) {
        this.volume = volume;
        this.resource = resource;
        this.repeat = repeat;
        this.repeatDelay = repratDelay;
        this.pitch = pitch;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    @Override
    public ResourceLocation getPositionedSoundLocation() {
        return resource;
    }

    @Override
    public boolean canRepeat() {
        return repeat;
    }

    @Override
    public int getRepeatDelay() {
        return repeatDelay;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public float getXPosF() {
        return xPos;
    }

    @Override
    public float getYPosF() {
        return yPos;
    }

    @Override
    public float getZPosF() {
        return zPos;
    }

    @Override
    public AttenuationType getAttenuationType() {
        return AttenuationType.LINEAR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomSound)) return false;

        CustomSound that = (CustomSound) o;

        return Float.compare(that.pitch, pitch) == 0 &&
                repeat == that.repeat &&
                repeatDelay == that.repeatDelay &&
                Float.compare(that.volume, volume) == 0 &&
                Float.compare(that.xPos, xPos) == 0 &&
                Float.compare(that.yPos, yPos) == 0 &&
                Float.compare(that.zPos, zPos) == 0 &&
                !(resource != null ? !resource.equals(that.resource) : that.resource != null);

    }

    @Override
    public int hashCode() {
        int result = (volume != +0.0f ? Float.floatToIntBits(volume) : 0);
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        result = 31 * result + (repeat ? 1 : 0);
        result = 31 * result + repeatDelay;
        result = 31 * result + (pitch != +0.0f ? Float.floatToIntBits(pitch) : 0);
        result = 31 * result + (xPos != +0.0f ? Float.floatToIntBits(xPos) : 0);
        result = 31 * result + (yPos != +0.0f ? Float.floatToIntBits(yPos) : 0);
        result = 31 * result + (zPos != +0.0f ? Float.floatToIntBits(zPos) : 0);
        return result;
    }
}
