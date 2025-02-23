package com.gekocaretaker.gekosmagic.registry;

import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.entity.passive.GeckoVariant;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;

public class ModRegistries {
    public static final Registry<Elixir> ELIXIR = FabricRegistryBuilder
            .createSimple(ModRegistryKeys.ELIXIR)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
    public static final Registry<Essence> ESSENCE = FabricRegistryBuilder
            .createSimple(ModRegistryKeys.ESSENCE)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static void init() {
        //DynamicRegistries.registerSynced(ModRegistryKeys.ESSENCE, Essence.CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(ModRegistryKeys.GECKO_VARIANT, GeckoVariant.CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
    }

    private ModRegistries() {}
}
