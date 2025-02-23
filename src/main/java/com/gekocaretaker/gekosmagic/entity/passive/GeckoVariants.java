package com.gekocaretaker.gekosmagic.entity.passive;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GeckoVariants {
    public static final RegistryKey<GeckoVariant> TOKAY = of("tokay");
    public static final RegistryKey<GeckoVariant> ORCHID = of("orchid");
    public static final RegistryKey<GeckoVariant> BLACK = of("black");
    public static final RegistryKey<GeckoVariant> SAND = of("sand");
    public static final RegistryKey<GeckoVariant> CAT = of("cat");
    public static final RegistryKey<GeckoVariant> INSURANCE = of("insurance");
    public static final RegistryKey<GeckoVariant> DEFAULT = TOKAY;

    public static void init() {}

    private static RegistryKey<GeckoVariant> of(String id) {
        return RegistryKey.of(ModRegistryKeys.GECKO_VARIANT, Gekosmagic.identify(id));
    }

    public static RegistryEntry.Reference<GeckoVariant> select(Random random, DynamicRegistryManager registries) {
        Stream<RegistryEntry.Reference<GeckoVariant>> entries = registries.get(ModRegistryKeys.GECKO_VARIANT).streamEntries();
        List<RegistryEntry.Reference<GeckoVariant>> list = new ArrayList<>();
        entries.forEach((entry) -> {
            if (entry.value().spawnsNaturally()) {
                list.add(entry);
            }
        });
        return Util.getRandom(list, random);
    }

    private GeckoVariants() {}
}
