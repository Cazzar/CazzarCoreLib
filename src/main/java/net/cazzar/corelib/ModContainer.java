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

package net.cazzar.corelib;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.cazzar.corelib.client.ClientTickHandler;
import net.cazzar.corelib.client.RenderEventHandler;
import net.cazzar.corelib.client.rendering.RenderPlayer;
import net.cazzar.corelib.events.PlayerTracker;
import net.cazzar.corelib.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.util.Arrays;

@SuppressWarnings("UnusedDeclaration")
public class ModContainer extends DummyModContainer {
    static ModMetadata meta;

    public ModContainer() {
        super(meta = new ModMetadata());

        meta.authorList = Arrays.asList("cazzar");
        meta.description = "The core library for cazzar's mods";
        meta.modId = Reference.MOD_ID;
        meta.name = "Cazzar Core Lib";
        meta.url = "http://www.cazzar.net/";
        meta.version = getVersionFromJar();
    }

    public String getVersionFromJar() {
        String version = getClass().getPackage().getImplementationVersion();
        return version.isEmpty() ? "UNKNOWN" : version;
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Subscribe
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        GameRegistry.registerPlayerTracker(new PlayerTracker());

        MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
        TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);

        RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderPlayer());
        //new Recipe(Item.feather).cross(Item.appleGold).setProduces(new ItemStack(Item.diamond, 64)).setRecipe();
    }

    @Subscribe
    public void init(FMLInitializationEvent event) {

    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event) {

    }
}
