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
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import java.util.List;

/**
 * 0
 *
 * @author Cayde
 */
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
     *
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
     *
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
}
