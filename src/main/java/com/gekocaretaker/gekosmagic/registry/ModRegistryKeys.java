package com.gekocaretaker.gekosmagic.registry;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.entity.passive.GeckoVariant;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class ModRegistryKeys {
    public static final RegistryKey<Registry<Elixir>> ELIXIR = of("elixir");
    public static final RegistryKey<Registry<Essence>> ESSENCE = of("essence");
    public static final RegistryKey<Registry<GeckoVariant>> GECKO_VARIANT = of("gecko_variant");

    private static <T> RegistryKey<Registry<T>> of(String id) {
        return RegistryKey.ofRegistry(Gekosmagic.identify(id));
    }

    public static void init() {}

    private ModRegistryKeys() {}
}
