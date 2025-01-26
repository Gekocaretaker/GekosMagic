package com.gekocaretaker.gekosmagic.screen;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlerTypes {
    public static ScreenHandlerType<AlchemyStandScreenHandler> ALCHEMY_STAND = new ExtendedScreenHandlerType<>(AlchemyStandScreenHandler::new, BlockPos.PACKET_CODEC.cast());

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, Gekosmagic.identify(id), new ScreenHandlerType(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void init() {
        Registry.register(Registries.SCREEN_HANDLER, Gekosmagic.identify("alchemy_stand"), ALCHEMY_STAND);
    }

    private ModScreenHandlerTypes() {}
}
