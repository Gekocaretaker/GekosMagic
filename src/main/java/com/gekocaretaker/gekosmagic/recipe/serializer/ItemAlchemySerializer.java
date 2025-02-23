package com.gekocaretaker.gekosmagic.recipe.serializer;

import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.recipe.ItemAlchemyRecipe;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;

public class ItemAlchemySerializer implements RecipeSerializer<ItemAlchemyRecipe> {
    public static final MapCodec<ItemAlchemyRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(
            Registries.ITEM.getEntryCodec().fieldOf("from").forGetter(ItemAlchemyRecipe::from),
            Essence.REGISTRY_CODEC.fieldOf("essence").forGetter(ItemAlchemyRecipe::ingredient),
            Registries.ITEM.getEntryCodec().fieldOf("to").forGetter(ItemAlchemyRecipe::to)
    ).apply(instance, ItemAlchemyRecipe::new));
    public static final PacketCodec<RegistryByteBuf, ItemAlchemyRecipe> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.registryEntry(RegistryKeys.ITEM),
            ItemAlchemyRecipe::from,
            PacketCodecs.registryEntry(ModRegistryKeys.ESSENCE),
            ItemAlchemyRecipe::ingredient,
            PacketCodecs.registryEntry(RegistryKeys.ITEM),
            ItemAlchemyRecipe::to,
            ItemAlchemyRecipe::new
    );

    @Override
    public MapCodec<ItemAlchemyRecipe> codec() {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, ItemAlchemyRecipe> packetCodec() {
        return PACKET_CODEC;
    }
}
