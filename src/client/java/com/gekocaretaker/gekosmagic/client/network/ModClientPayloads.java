package com.gekocaretaker.gekosmagic.client.network;

import com.gekocaretaker.gekosmagic.block.entity.AlchemyStandBlockEntity;
import com.gekocaretaker.gekosmagic.elixir.EssenceContainer;
import com.gekocaretaker.gekosmagic.network.EssenceContainerPayload;
import com.gekocaretaker.gekosmagic.network.EssenceIndexPayload;
import com.gekocaretaker.gekosmagic.screen.AlchemyStandScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class ModClientPayloads {
    public static void init() {
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
    }

    private ModClientPayloads() {}
}
