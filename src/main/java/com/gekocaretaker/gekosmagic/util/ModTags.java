package com.gekocaretaker.gekosmagic.util;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static final TagKey<Item> COMMON_BERRIES = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "foods/berries"));
    public static final TagKey<Item> GLOW_BERRIES = TagKey.of(RegistryKeys.ITEM, Gekosmagic.identify("foods/glow_berries"));

    public static void init() {}

    private ModTags() {}
}
