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

import com.google.common.collect.Lists;
import cpw.mods.fml.common.CertificateHelper;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.cazzar.corelib.asm.McpMappings;
import net.cazzar.corelib.lib.LogHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.security.CodeSource;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;

/**
 * The FML coremod for the plugin also containing information about Deobf and minecraft's running location
 */
@SuppressWarnings("CanBeFinal")
@IFMLLoadingPlugin.TransformerExclusions("net.cazzar.corelib.asm.*")
public class CoreMod implements IFMLLoadingPlugin, IFMLCallHook {
    @SuppressWarnings("FieldCanBeLocal")
    private static boolean runtimeDeobfuscationEnabled = true;
    private static String deobfuscationFileName = null;
    private static File mcLocation = null;
    private static final String FINGERPRINT = "B6:9D:73:36:FB:E4:C3:E9:72:79:EB:3E:E3:19:9F:00:9A:90:34:75".toLowerCase().replace(":", "");
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
        List<String> transformers = Lists.newArrayList();
        for (String mod : new String[]{"jukeboxreloaded", "voxelplayer", "locksplus", "openprinter"}) {
            if (getClass().getClassLoader().getResourceAsStream("assets/" + mod + "/asm.properties") != null) {
                BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("assets/" + mod + "/asm.properties")));
                String s;
                try {
                    while ((s = in.readLine()) != null) {
                        transformers.add(s);
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return transformers.toArray(new String[transformers.size()]);
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
                LogHelper.coreLog.warn("Unable to set field: %s", key);
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
