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
package net.cazzar.corelib;


import net.cazzar.corelib.asm.McpMappings;
import net.cazzar.corelib.lib.LogHelper;
import net.minecraftforge.fml.common.CertificateHelper;
import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.security.CodeSource;
import java.security.cert.Certificate;
import java.util.Map;

/**
 * The FML coremod for the plugin also containing information about Deobf and minecraft's running location
 */
@SuppressWarnings("CanBeFinal")
@IFMLLoadingPlugin.TransformerExclusions({"net.cazzar.corelib.asm.*", "net.cazzar.corelib.asm.transformers.*"})
public class CoreMod implements IFMLLoadingPlugin, IFMLCallHook {
    private static final String FINGERPRINT = "B6:9D:73:36:FB:E4:C3:E9:72:79:EB:3E:E3:19:9F:00:9A:90:34:75".toLowerCase().replace(":", "");
    @SuppressWarnings("FieldCanBeLocal")
    private static boolean runtimeDeobfuscationEnabled = true;
    private static String deobfuscationFileName = null;
    private static File mcLocation = null;
    private static File coremodLocation = null;

    /**
     * Get the deobf file name
     *
     * @return the Deobf data filename
     */
    public static String getDeobfuscationFileName() {
        return deobfuscationFileName;
    }

    /**
     * Get if runtime deobfuscation is enabled
     *
     * @return if runtime deobf is enabled
     */
    public static boolean getRuntimeDeobfuscationEnabled() {
        return runtimeDeobfuscationEnabled;
    }

    /**
     * Get the directory Minecraft is in
     *
     * @return the minecraft directory
     */
    @SuppressWarnings("UnusedDeclaration")
    public static File getMcLocation() {
        return mcLocation;
    }

    public static File getCoremodLocation() {
        return coremodLocation;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];// {
//                "net.cazzar.corelib.asm.transformers.BlockTransformer"
//        };
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
                LogHelper.coreLog.catching(e);
                LogHelper.coreLog.warn("Unable to set field: {}", key);
            }
        }
    }

    @Override
    public String getAccessTransformerClass() {
        return "net.cazzar.corelib.asm.CoreAccessTransformer";
    }

    @Override
    public Void call() throws Exception {
        CodeSource source = getClass().getProtectionDomain().getCodeSource();

        if (source.getLocation().getProtocol().equals("jar")) {
            Certificate[] certificates = source.getCertificates();
            if (certificates == null)
                throw new RuntimeException("CazzarCoreLib is not signed and has been compromised, please get it from http://www.cazzar.net/");

            for (Certificate certificate : certificates) {
                String fingerprint = CertificateHelper.getFingerprint(certificate);
                if (fingerprint.equals(FINGERPRINT)) {
                    LogHelper.coreLog.info("Found a valid CazzarCoreLib fingerprint");
                } else
                    throw new RuntimeException("CazzarCoreLib has been compromised, please get a new version from http://www.cazzar.net/");
            }
        }

        McpMappings.instance();
        return null;
    }

}
