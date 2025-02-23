package com.gekocaretaker.gekosmagic.recipe.serializer;

import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.recipe.ElixirRecipe;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.RecipeSerializer;

public class ElixirSerializer implements RecipeSerializer<ElixirRecipe> {
    public static final MapCodec<ElixirRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(
            Elixir.CODEC.fieldOf("from").forGetter(ElixirRecipe::from),
            Essence.REGISTRY_CODEC.fieldOf("essence").forGetter(ElixirRecipe::ingredient),
            Elixir.CODEC.fieldOf("to").forGetter(ElixirRecipe::to)
    ).apply(instance, ElixirRecipe::new));
    public static final PacketCodec<RegistryByteBuf, ElixirRecipe> PACKET_CODEC = PacketCodec.tuple(
            Elixir.PACKET_CODEC, ElixirRecipe::from,
            PacketCodecs.registryEntry(ModRegistryKeys.ESSENCE),
            ElixirRecipe::ingredient,
            Elixir.PACKET_CODEC,
            ElixirRecipe::to,
            ElixirRecipe::new
    );

    @Override
    public MapCodec<ElixirRecipe> codec() {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, ElixirRecipe> packetCodec() {
        return PACKET_CODEC;
    }
}
