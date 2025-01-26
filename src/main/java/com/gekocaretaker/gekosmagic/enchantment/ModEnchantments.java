package com.gekocaretaker.gekosmagic.enchantment;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ModEnchantments {
    public static final RegistryKey<Enchantment> FAST_REEL = of("fast_reel");

    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Gekosmagic.identify(id));
    }

    public static void init() {}

    private ModEnchantments() {}
}
