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
package net.cazzar.corelib;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.cazzar.corelib.asm.McpMappings;
import net.cazzar.corelib.lib.LogHelper;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.logging.Level;

@SuppressWarnings("CanBeFinal")
@IFMLLoadingPlugin.TransformerExclusions("net.cazzar.corelib.asm.*")
public class CoreMod implements IFMLLoadingPlugin, IFMLCallHook {
    @SuppressWarnings("FieldCanBeLocal")
    private static boolean runtimeDeobfuscationEnabled = true;
    private static String deobfuscationFileName = null;
    private static File mcLocation = null;

    public static String getDeobfuscationFileName() {
        return deobfuscationFileName;
    }

    public static boolean getRuntimeDeobfuscationEnabled() {
        return runtimeDeobfuscationEnabled;
    }

    public static File getMcLocation() {
        return mcLocation;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                //"net.cazzar.corelib.asm.CoreLibAccessTransformer"
                //"net.cazzar.corelib.asm.SrgAccessTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return "net.cazzar.corelib.ModContainer";
    }

    @Override
    public String getSetupClass() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public void injectData(Map<String, Object> data) {
        for (String key : data.keySet()) {
            try {
                Field f = this.getClass().getDeclaredField(key);
                f.setAccessible(true);
                f.set(null, data.get(key));
            } catch (NoSuchFieldException ignored) {
            } catch (IllegalAccessException e) {
                LogHelper.coreLog.log(Level.WARNING, e, "Unable to set field: %s", key);
            }
        }
    }

    @Override
    public Void call() throws Exception {
        McpMappings.instance();
        return null;
    }
}
