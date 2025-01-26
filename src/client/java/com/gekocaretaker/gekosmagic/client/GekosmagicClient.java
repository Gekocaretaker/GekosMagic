package com.gekocaretaker.gekosmagic.client;

import com.gekocaretaker.gekosmagic.block.ModBlocks;
import com.gekocaretaker.gekosmagic.block.entity.AlchemyStandBlockEntity;
import com.gekocaretaker.gekosmagic.client.gui.screen.ingame.AlchemyStandScreen;
import com.gekocaretaker.gekosmagic.client.render.entity.GeckoRenderer;
import com.gekocaretaker.gekosmagic.client.render.entity.model.GeckoEntityModel;
import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.EssenceContainer;
import com.gekocaretaker.gekosmagic.entity.ModEntities;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.network.EssenceContainerPayload;
import com.gekocaretaker.gekosmagic.network.EssenceIndexPayload;
import com.gekocaretaker.gekosmagic.resource.EssenceDataLoader;
import com.gekocaretaker.gekosmagic.screen.AlchemyStandScreenHandler;
import com.gekocaretaker.gekosmagic.screen.ModScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.math.ColorHelper;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class GekosmagicClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(GeckoEntityModel.GECKO, GeckoEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GeckoEntityModel.GECKO_COLLAR, GeckoEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.GECKO, GeckoRenderer::new);
        EntityRendererRegistry.register(ModEntities.ELIXIR, FlyingItemEntityRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ALCHEMY_STAND_BLOCK, RenderLayer.getCutout());

        HandledScreens.register(ModScreenHandlerTypes.ALCHEMY_STAND, AlchemyStandScreen::new);

        PayloadTypeRegistry.playC2S().register(EssenceContainerPayload.ID, EssenceContainerPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(EssenceIndexPayload.ID, EssenceIndexPayload.CODEC);

        ClientPlayNetworking.registerGlobalReceiver(EssenceContainerPayload.ID, (payload, context) -> {
            if (context.client().world.getBlockEntity(payload.pos()) instanceof AlchemyStandBlockEntity) {
                if (context.client().player.currentScreenHandler instanceof AlchemyStandScreenHandler screenHandler &&
                    screenHandler.getBlockEntity().getPos().equals(payload.pos())) {
                    screenHandler.setEssences((ArrayList<EssenceContainer>) payload.essenceContainers());
                }
            }
        });

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(EssenceDataLoader.INSTANCE);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return tintIndex > 0 ? -1 : ColorHelper.Argb.fullAlpha(((ElixirContentsComponent) stack.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT)).getColor());
        }, ModItems.ELIXIR, ModItems.SPLASH_ELIXIR, ModItems.LINGERING_ELIXIR, ModItems.BUTTERED_ELIXIR, ModItems.UNINTERESTING_ELIXIR, ModItems.DIFFUSING_ELIXIR);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return tintIndex > 0 ? -1 : ElixirContentsComponent.EFFECTLESS_COLOR;
        }, ModItems.CLEAR_ELIXIR, ModItems.BLAND_ELIXIR);
    }
}
