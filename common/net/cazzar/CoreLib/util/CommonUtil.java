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
