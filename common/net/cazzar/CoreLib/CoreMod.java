/**
 *
 */
package net.cazzar.corelib;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.cazzar.corelib.asm.McpMappings;
import net.cazzar.corelib.asm.SrgAccessTransformer;
import net.cazzar.corelib.lib.LogHelper;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.logging.Level;

public class CoreMod implements IFMLLoadingPlugin, IFMLCallHook {
    private static final boolean runtimeDeobfuscationEnabled = true;
    private static final String deobfuscationFileName = null;
    private static final File mcLocation = null;

    public static String getDeobfuscationFileName() {
        return deobfuscationFileName;
    }

    @Override
    public String[] getLibraryRequestClass() {
        return null;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                "net.cazzar.corelib.asm.CoreLibAccessTransformer",
                "net.cazzar.corelib.asm.SrgAccessTransformer"
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
                Field modifiers = f.getClass().getDeclaredField("modifiers");
                modifiers.setAccessible(true);
                modifiers.setInt(f, f.getModifiers() & ~Modifier.FINAL);
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

        new SrgAccessTransformer("cazzarcorelib_at.cfg");
        return null;
    }
}
