/*
 * Copyright (C) 2013 cazzar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package net.cazzar.corelib.client;

/**
 * @Author: Cayde
 */
public class RenderEventHandler {
    /*@ForgeSubscribe
    public void prePlayerRender(RenderPlayerEvent.Pre event) {
        if (event.entityPlayer.username.equals("cazzar")) {
            //This will be fun!

            Field modelBiped = ReflectionHelper.findField(event.renderer.getClass(), "modelBipedMain", "field_77109_a");
            if (modelBiped == null)
                return;

            try {
                if (!(modelBiped.get(event.renderer) instanceof ModelCheetah))
                    modelBiped.set(event.renderer, new ModelCheetah());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        Field modelBiped = ReflectionHelper.findField(event.renderer.getClass(), "modelBipedMain", "field_77109_a");

        try {
            LogHelper.coreLog.info(modelBiped.get(event.renderer).getClass().getCanonicalName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }*/
}
