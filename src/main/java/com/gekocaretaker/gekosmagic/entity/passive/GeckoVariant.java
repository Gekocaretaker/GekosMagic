package com.gekocaretaker.gekosmagic.entity.passive;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

public record GeckoVariant(Identifier texture, Item scale) {
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<GeckoVariant>> PACKET_CODEC = PacketCodecs.registryEntry(ModRegistryKeys.GECKO_VARIANT);
    public static final RegistryKey<GeckoVariant> TOKAY = of("tokay");
    public static final RegistryKey<GeckoVariant> ORCHID = of("orchid");
    public static final RegistryKey<GeckoVariant> BLACK = of("black");
    public static final RegistryKey<GeckoVariant> SAND = of("sand");
    public static final RegistryKey<GeckoVariant> CAT = of("cat");
    public static final RegistryKey<GeckoVariant> INSURANCE = of("insurance");

    private static final List<RegistryKey<GeckoVariant>> SPAWN_VARIANTS = new ArrayList<>() {{
        add(TOKAY);
        add(ORCHID);
        add(BLACK);
        add(SAND);
        add(CAT);
    }};

    public Identifier texture() {
        return this.texture;
    }

    public Item scale() {
        return this.scale;
    }

    public static RegistryKey<GeckoVariant> getRandomSpawnable(Random random) {
        int position = random.nextInt(SPAWN_VARIANTS.size());
        return SPAWN_VARIANTS.get(position);
    }

    public static RegistryKey<GeckoVariant> addSpawnVariant(RegistryKey<GeckoVariant> variant) {
        SPAWN_VARIANTS.add(variant);
        return variant;
    }

    public static void init() {
        register(TOKAY, "textures/entity/gecko/tokay.png", ModItems.TOKAY_GECKO_SCALE);
        register(ORCHID, "textures/entity/gecko/orchid.png", ModItems.ORCHID_GECKO_SCALE);
        register(BLACK, "textures/entity/gecko/black.png", ModItems.BLACK_GECKO_SCALE);
        register(SAND, "textures/entity/gecko/sand.png", ModItems.SAND_GECKO_SCALE);
        register(CAT, "textures/entity/gecko/cat.png", ModItems.CAT_GECKO_SCALE);
        register(INSURANCE, "textures/entity/gecko/insurance.png", ModItems.TOKAY_GECKO_SCALE);
    }

    private static RegistryKey<GeckoVariant> of(String id) {
        return RegistryKey.of(ModRegistryKeys.GECKO_VARIANT, Gekosmagic.identify(id));
    }

    private static GeckoVariant register(RegistryKey<GeckoVariant> key, String textureId, Item scaleItem) {
        return Registry.register(ModRegistries.GECKO_VARIANT, key, new GeckoVariant(Gekosmagic.identify(textureId), scaleItem));
    }
}
