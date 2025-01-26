package com.gekocaretaker.gekosmagic.entity.data;

import com.gekocaretaker.gekosmagic.entity.passive.GeckoVariant;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModTrackedDataHandlerRegistry {
    public static final TrackedDataHandler<RegistryEntry<GeckoVariant>> GECKO_VARIANT;

    public static void init() {}

    static {
        GECKO_VARIANT = TrackedDataHandler.create(GeckoVariant.PACKET_CODEC);

        TrackedDataHandlerRegistry.register(GECKO_VARIANT);
    }
}
