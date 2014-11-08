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
    public ResourceLocation getSoundLocation() {
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
