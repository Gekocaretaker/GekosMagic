package com.gekocaretaker.gekosmagic.entity.passive;

import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.util.Identifier;

public record GeckoVariant(Identifier texture, RegistryEntry<Item> scale, boolean spawnsNaturally) {
    public static final Codec<GeckoVariant> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Identifier.CODEC.fieldOf("texture").forGetter(GeckoVariant::texture),
            Registries.ITEM.getEntryCodec().fieldOf("scale").forGetter(GeckoVariant::scale),
            Codec.BOOL.optionalFieldOf("spawns_naturally", true).forGetter(GeckoVariant::spawnsNaturally)
    ).apply(instance, GeckoVariant::new));

    public static final PacketCodec<RegistryByteBuf, RegistryEntry<GeckoVariant>> PACKET_CODEC = PacketCodecs.registryEntry(ModRegistryKeys.GECKO_VARIANT);

    public static final Codec<RegistryEntry<GeckoVariant>> ENTRY_CODEC = RegistryFixedCodec.of(ModRegistryKeys.GECKO_VARIANT);

    public static final PacketCodec<RegistryByteBuf, RegistryEntry<GeckoVariant>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(ModRegistryKeys.GECKO_VARIANT);

    public GeckoVariant(Identifier texture, RegistryEntry<Item> scale, boolean spawnsNaturally) {
        this.texture = texture.withPrefixedPath("textures/").withSuffixedPath(".png");
        this.scale = scale;
        this.spawnsNaturally = spawnsNaturally;
    }

    public Identifier texture() {
        return this.texture;
    }

    public RegistryEntry<Item> scale() {
        return this.scale;
    }

    public Item getScaleItem() {
        return this.scale.value();
    }
}
