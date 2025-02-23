package com.gekocaretaker.gekosmagic.recipe.serializer;

import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.recipe.BasicAlchemyRecipe;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.RecipeSerializer;

public class BasicAlchemySerializer implements RecipeSerializer<BasicAlchemyRecipe> {
    public static final MapCodec<BasicAlchemyRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(
            Elixir.CODEC.fieldOf("from").forGetter(BasicAlchemyRecipe::from),
            Essence.REGISTRY_CODEC.fieldOf("essence").forGetter(BasicAlchemyRecipe::ingredient),
            ElixirContentsComponent.CODEC.fieldOf("to").forGetter(BasicAlchemyRecipe::to),
            Codec.STRING.optionalFieldOf("translation", "default").forGetter(BasicAlchemyRecipe::translation)
    ).apply(instance, BasicAlchemyRecipe::new));
    public static final PacketCodec<RegistryByteBuf, BasicAlchemyRecipe> PACKET_CODEC = PacketCodec.tuple(
            Elixir.PACKET_CODEC, BasicAlchemyRecipe::from,
            PacketCodecs.registryEntry(ModRegistryKeys.ESSENCE),
            BasicAlchemyRecipe::ingredient,
            ElixirContentsComponent.PACKET_CODEC,
            BasicAlchemyRecipe::to,
            PacketCodecs.STRING,
            BasicAlchemyRecipe::translation,
            BasicAlchemyRecipe::new
    );

    @Override
    public MapCodec<BasicAlchemyRecipe> codec() {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, BasicAlchemyRecipe> packetCodec() {
        return PACKET_CODEC;
    }
}
