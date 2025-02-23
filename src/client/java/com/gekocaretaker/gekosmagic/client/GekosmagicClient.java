package com.gekocaretaker.gekosmagic.client;

import com.gekocaretaker.gekosmagic.client.color.ModColorProviders;
import com.gekocaretaker.gekosmagic.client.gui.ModScreens;
import com.gekocaretaker.gekosmagic.client.network.ModClientPayloads;
import com.gekocaretaker.gekosmagic.client.render.ModRenders;
import com.gekocaretaker.gekosmagic.client.resource.ModClientResourceReloaders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class GekosmagicClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModClientPayloads.init();
        ModClientResourceReloaders.init();
        ModColorProviders.init();
        ModRenders.init();
        ModScreens.init();
    }
}
