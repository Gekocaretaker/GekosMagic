package com.gekocaretaker.gekosmagic.client.render;

import com.gekocaretaker.gekosmagic.block.ModBlocks;
import com.gekocaretaker.gekosmagic.block.entity.ModBlockEntityTypes;
import com.gekocaretaker.gekosmagic.client.render.block.AlchemyStandBlockEntityRenderer;
import com.gekocaretaker.gekosmagic.client.render.entity.GeckoRenderer;
import com.gekocaretaker.gekosmagic.client.render.entity.model.GeckoEntityModel;
import com.gekocaretaker.gekosmagic.entity.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

@Environment(EnvType.CLIENT)
public class ModRenders {
    public static void init() {
        EntityModelLayerRegistry.registerModelLayer(GeckoEntityModel.GECKO, GeckoEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GeckoEntityModel.GECKO_COLLAR, GeckoEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.GECKO, GeckoRenderer::new);
        EntityRendererRegistry.register(ModEntities.ELIXIR, FlyingItemEntityRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ALCHEMY_STAND_BLOCK, RenderLayer.getCutout());
    }

    private ModRenders() {}
}
