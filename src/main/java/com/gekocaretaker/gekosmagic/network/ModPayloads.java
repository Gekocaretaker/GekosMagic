package com.gekocaretaker.gekosmagic.network;

import com.gekocaretaker.gekosmagic.block.entity.AlchemyStandBlockEntity;
import com.gekocaretaker.gekosmagic.screen.AlchemyStandScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModPayloads {
    public static void init() {
        PayloadTypeRegistry.playS2C().register(EssenceContainerPayload.ID, EssenceContainerPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(EssenceIndexPayload.ID, EssenceIndexPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(EssenceIndexPayload.ID, (payload, context) -> {
            if (context.player().getWorld().getBlockEntity(payload.pos()) instanceof AlchemyStandBlockEntity) {
                if (context.player().currentScreenHandler instanceof AlchemyStandScreenHandler screenHandler
                        && screenHandler.getBlockEntity().getPos().equals(payload.pos())) {
                    ((AlchemyStandBlockEntity) context.player().getWorld().getBlockEntity(payload.pos())).setSelectedIndex(payload.index());
                }
            }
        });
    }

    private ModPayloads() {}
}
