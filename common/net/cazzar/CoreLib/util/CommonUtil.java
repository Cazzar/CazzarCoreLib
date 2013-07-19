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
    public static Side getSide() {
        return FMLCommonHandler.instance().getSide();
    }

    public static boolean isServer() {
        return getSide().isServer();
    }

    public static String join(String delim, String... args) {
        StringBuilder sb = new StringBuilder(args[0]);
        for (int i = 1; i < args.length; i++) {
            sb.append(delim);
            sb.append(args[i]);
        }

        return sb.toString();
    }

    @SuppressWarnings("UnusedAssignment")
    public static <T> T arrayPopLast(T[] array) {
        List<T> arr = Lists.newArrayList(array);
        T last = arr.get(arr.size() - 1);
        arr.remove(arr.size() - 1);
        array = (T[]) arr.toArray();

        return last;
    }
}
