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
     * <p>Remove the last element of an array and return it</p>
     * Honestly you should use a {@link java.util.Queue}
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
