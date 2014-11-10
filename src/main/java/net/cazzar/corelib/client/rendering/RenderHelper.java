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

package net.cazzar.corelib.client.rendering;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.model.obj.*;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.*;

/**
 * Some miscellaneous {@link net.minecraftforge.client.model.AdvancedModelLoader} functions
 */
public class RenderHelper {
    /**
     * Render a model using an {@link net.minecraft.util.IIcon}
     *
     * @param model the model to use
     * @param icon  the Icon that contains the UV map
     * @param tes   the {@link net.minecraft.client.renderer.Tessellator} instance to use
     */
    public static void renderWithIcon(@NotNull WavefrontObject model, @NotNull IIcon icon, @NotNull Tessellator tes) {
        for (GroupObject go : model.groupObjects) {
            for (Face f : go.faces) {
                Vertex n = f.faceNormal;
                tes.setNormal(n.x, n.y, n.z);
                for (int i = 0; i < f.vertices.length; i++) {
                    Vertex v = f.vertices[i];
                    TextureCoordinate t = f.textureCoordinates[i];
                    tes.addVertexWithUV(v.x, v.y, v.z,
                            icon.getInterpolatedU(t.u * 16),
                            icon.getInterpolatedV(t.v * 16));
                }
            }
        }
    }

    /**
     * Rotate the model along the specified axis
     *
     * @param model   the model to use
     * @param degrees the angle in degrees to rotate
     * @param x       rotate along the x
     * @param y       rotate along the y
     * @param z       rotate along the z
     */
    public static void rotateModel(@NotNull WavefrontObject model, double degrees, double x, double y, double z) {
        double c = cos(toRadians(degrees));
        double s = sin(toRadians(degrees));
        for (GroupObject go : model.groupObjects) {
            for (Face f : go.faces) {
                Vertex n = f.faceNormal;
                Vertex n1 = new Vertex(n.x, n.y, n.z);
                n.x = (float) ((x * x * (1 - c) + c) * n1.x + (x * y * (1 - c) - z * s) * n1.y + (x * z * (1 - c) + y * s) * n1.z);
                n.y = (float) ((y * x * (1 - c) + z * s) * n1.x + (y * y * (1 - c) + c) * n1.y + (y * z * (1 - c) - x * s) * n1.z);
                n.z = (float) ((x * z * (1 - c) - y * s) * n1.x + (y * z * (1 - c) + x * s) * n1.y + (z * z * (1 - c) + c) * n1.z);

                for (Vertex v : f.vertices) {
                    Vertex v1 = new Vertex(v.x, v.y, v.z);
                    v.x = (float) ((x * x * (1 - c) + c) * v1.x + (x * y * (1 - c) - z * s) * v1.y + (x * z * (1 - c) + y * s) * v1.z);
                    v.y = (float) ((y * x * (1 - c) + z * s) * v1.x + (y * y * (1 - c) + c) * v1.y + (y * z * (1 - c) - x * s) * v1.z);
                    v.z = (float) ((x * z * (1 - c) - y * s) * v1.x + (y * z * (1 - c) + x * s) * v1.y + (z * z * (1 - c) + c) * v1.z);
                }
            }
        }
    }

    /**
     * Scale the model along the x, y and z with the specified scales.
     *
     * @param model the model to use
     * @param x     the scale of the x
     * @param y     the scale of the y
     * @param z     the scale of the z
     */
    public static void scaleModel(@NotNull WavefrontObject model, double x, double y, double z) {
        for (GroupObject go : model.groupObjects) {
            for (Face f : go.faces) {
                Vertex n = f.faceNormal;
                Vertex n1 = new Vertex(n.x, n.y, n.z);

                n.x = (float) (x * n1.x);
                n.y = (float) (y * n1.y);
                n.z = (float) (z * n1.z);

                for (Vertex v : f.vertices) {
                    Vertex v1 = new Vertex(v.x, v.y, v.z);

                    v.x = (float) (x * v1.x);
                    v.y = (float) (y * v1.y);
                    v.z = (float) (z * v1.z);
                }
            }
        }
    }
}