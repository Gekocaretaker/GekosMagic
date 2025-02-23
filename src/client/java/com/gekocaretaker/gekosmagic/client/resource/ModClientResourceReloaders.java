package com.gekocaretaker.gekosmagic.client.resource;

import com.gekocaretaker.gekosmagic.resource.EssenceAssetLoader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

@Environment(EnvType.CLIENT)
public class ModClientResourceReloaders {
    public static void init() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(EssenceAssetLoader.INSTANCE);
    }

    private ModClientResourceReloaders() {}
}
