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

package net.cazzar.corelib;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.client.FMLFileResourcePack;
import cpw.mods.fml.client.FMLFolderResourcePack;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import net.cazzar.corelib.lib.Reference;

import java.io.File;
import java.net.URISyntaxException;
import java.security.cert.Certificate;
import java.util.Arrays;

/**
 * The basic container for the mod information
 */
@SuppressWarnings("UnusedDeclaration")
public class ModContainer extends DummyModContainer {
    static ModMetadata meta;

    /**
     * Initialize the mod
     */
    public ModContainer() {
        super(meta = new ModMetadata());

        meta.authorList = Arrays.asList("cazzar");
        meta.description = "The core library for cazzar's mods";
        meta.modId = Reference.MOD_ID;
        meta.name = "Cazzar Core Lib";
        meta.dependants = Arrays.asList((ArtifactVersion) new DefaultArtifactVersion("jukeboxreloaded", true));
        meta.url = "http://www.cazzar.net/";
        meta.version = getVersionFromJar();
    }

    @Override
    public Certificate getSigningCertificate() {
        Certificate[] certificates = getClass().getProtectionDomain().getCodeSource().getCertificates();
        return certificates != null ? certificates[0] : null;
    }

    /**
     * Use the java package information to get the version of the mod
     *
     * @return the mod's version
     */
    public String getVersionFromJar() {
        String version = getClass().getPackage().getImplementationVersion();
        return version == null || version.isEmpty() ? "UNKNOWN" : version;
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Subscribe
    public void preInit(FMLPreInitializationEvent event) {
        if (event.getSide().isClient()) {
//            MinecraftForge.EVENT_BUS.register(new ClientEvents());
        }
    }

    @Subscribe
    public void init(FMLInitializationEvent event) {

    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Override
    public File getSource() {
        if (CoreMod.getCoremodLocation() == null) {
            File f;
            try {
                f = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            } catch (URISyntaxException e) {
                f = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            }

            f = f.getParentFile().getParentFile().getParentFile().getParentFile();
            return f;
        }
        return CoreMod.getCoremodLocation();
    }

    @Override
    public Class<?> getCustomResourcePackClass() {
        if (getSource() == null) return FMLFolderResourcePack.class;
        return !getSource().isDirectory() ? FMLFileResourcePack.class : FMLFolderResourcePack.class;
    }
}
