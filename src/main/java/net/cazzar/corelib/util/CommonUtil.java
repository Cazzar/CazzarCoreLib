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

/**
 *
 */
package net.cazzar.corelib.util;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

/**
 * 0
 *
 * @author Cayde
 */
@SuppressWarnings("ClassWithoutConstructor")
public class CommonUtil {


    /**
     * Get the side that is currently
     *
     * @return the effective side
     */
    public static Side getSide() {
        return FMLCommonHandler.instance().getSide();
    }

    @SuppressWarnings({"BooleanMethodIsAlwaysInverted", "UnusedDeclaration"})
    public static boolean isServer() {
        return getSide().isServer();
    }

    /**
     * Join an array of strings with the specified decimeter
     *
     * @param delim the decimeter
     * @param args  the string to join
     * @return the joined string
     */
    public static String join(@SuppressWarnings("SameParameterValue") String delim, String... args) {
        StringBuilder sb = new StringBuilder(args[0]);
        for (int i = 1; i < args.length; i++) {
            sb.append(delim);
            sb.append(args[i]);
        }

        return sb.toString();
    }

    /**
     * <p>Remove the last element of an array and return it</p> Honestly you should use a {@link java.util.Queue}
     *
     * @param array the array to manipulate
     * @param <T>   the type of the array
     * @return the last element of the array
     */
    @SuppressWarnings("UnusedAssignment")
    public static <T> T arrayPopLast(T[] array) {
        List<T> arr = Lists.newArrayList(array);
        T last = arr.get(arr.size() - 1);
        arr.remove(arr.size() - 1);
        //noinspection unchecked
        array = (T[]) arr.toArray();

        return last;
    }

/*
    @SuppressWarnings({"OverlyComplexMethod", "OverlyLongMethod"})
    public static int getTextureID(IBlockAccess world, int x, int y, int z, int side) {
        boolean[] bitMatrix = new boolean[8];
        Block block = world.getBlock(x, y, z);

        if (side == 0 || side == 1) {
            bitMatrix[0] = world.getBlock(x - 1, y, z - 1) == block;
            bitMatrix[1] = world.getBlock(x, y, z - 1) == block;
            bitMatrix[2] = world.getBlock(x + 1, y, z - 1) == block;
            bitMatrix[3] = world.getBlock(x - 1, y, z) == block;
            bitMatrix[4] = world.getBlock(x + 1, y, z) == block;
            bitMatrix[5] = world.getBlock(x - 1, y, z + 1) == block;
            bitMatrix[6] = world.getBlock(x, y, z + 1) == block;
            bitMatrix[7] = world.getBlock(x + 1, y, z + 1) == block;
        } else if (side == 2 || side == 3) {
            bitMatrix[0] = world.getBlock(x + (side == 2 ? 1 : -1), y + 1, z) == block;
            bitMatrix[1] = world.getBlock(x, y + 1, z) == block;
            bitMatrix[2] = world.getBlock(x + (side == 3 ? 1 : -1), y + 1, z) == block;
            bitMatrix[3] = world.getBlock(x + (side == 2 ? 1 : -1), y, z) == block;
            bitMatrix[4] = world.getBlock(x + (side == 3 ? 1 : -1), y, z) == block;
            bitMatrix[5] = world.getBlock(x + (side == 2 ? 1 : -1), y - 1, z) == block;
            bitMatrix[6] = world.getBlock(x, y - 1, z) == block;
            bitMatrix[7] = world.getBlock(x + (side == 3 ? 1 : -1), y - 1, z) == block;
        } else if (side == 4 || side == 5) {
            bitMatrix[0] = world.getBlock(x, y + 1, z + (side == 5 ? 1 : -1)) == block;
            bitMatrix[1] = world.getBlock(x, y + 1, z) == block;
            bitMatrix[2] = world.getBlock(x, y + 1, z + (side == 4 ? 1 : -1)) == block;
            bitMatrix[3] = world.getBlock(x, y, z + (side == 5 ? 1 : -1)) == block;
            bitMatrix[4] = world.getBlock(x, y, z + (side == 4 ? 1 : -1)) == block;
            bitMatrix[5] = world.getBlock(x, y - 1, z + (side == 5 ? 1 : -1)) == block;
            bitMatrix[6] = world.getBlock(x, y - 1, z) == block;
            bitMatrix[7] = world.getBlock(x, y - 1, z + (side == 4 ? 1 : -1)) == block;
        }

        int idBuilder = 0;

        for (int i = 0; i <= 7; i++)
            idBuilder = idBuilder + (bitMatrix[i] ? (i == 0 ? 1 : (i == 1 ? 2 : (i == 2 ? 4 : (i == 3 ? 8 : (i == 4 ? 16 : (i == 5 ? 32 : (i == 6 ? 64 : 128))))))) : 0);

        return idBuilder > 255 || idBuilder < 0 ? 0 : textureRefByID[idBuilder];
    }

    private static int[] textureRefByID = {0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13,
                                                 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15,
                                                 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14,
                                                 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17, 22, 26, 16, 16, 20, 20,
                                                 16, 16, 28, 28, 21, 21, 46, 42, 21, 21, 43, 38, 4, 4, 5, 5, 4, 4, 5, 5,
                                                 9, 9, 30, 12, 9, 9, 30, 12, 16, 16, 20, 20, 16, 16, 28, 28, 25, 25, 45,
                                                 37, 25, 25, 40, 32, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15,
                                                 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0,
                                                 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2,
                                                 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17,
                                                 22, 26, 7, 7, 24, 24, 7, 7, 10, 10, 29, 29, 44, 41, 29, 29, 39, 33, 4,
                                                 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9, 9, 30, 12, 7, 7, 24, 24, 7, 7,
                                                 10, 10, 8, 8, 36, 35, 8, 8, 34, 11};
*/

}
