package com.gekocaretaker.gekosmagic.recipe.serializer;

import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.recipe.AdvancedAlchemyRecipe;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.RecipeSerializer;

public class AdvancedAlchemySerializer implements RecipeSerializer<AdvancedAlchemyRecipe> {
    public static final MapCodec<AdvancedAlchemyRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(
            ElixirContentsComponent.CODEC.fieldOf("from").forGetter(AdvancedAlchemyRecipe::from),
            Essence.REGISTRY_CODEC.fieldOf("essence").forGetter(AdvancedAlchemyRecipe::ingredient),
            ElixirContentsComponent.CODEC.fieldOf("to").forGetter(AdvancedAlchemyRecipe::to),
            Codec.STRING.optionalFieldOf("translation", "default").forGetter(AdvancedAlchemyRecipe::translation),
            Codec.STRING.optionalFieldOf("required_translation", "default").forGetter(AdvancedAlchemyRecipe::requiredTranslation)
    ).apply(instance, AdvancedAlchemyRecipe::new));
    public static final PacketCodec<RegistryByteBuf, AdvancedAlchemyRecipe> PACKET_CODEC = PacketCodec.tuple(
            ElixirContentsComponent.PACKET_CODEC,
            AdvancedAlchemyRecipe::from,
            PacketCodecs.registryEntry(ModRegistryKeys.ESSENCE),
            AdvancedAlchemyRecipe::ingredient,
            ElixirContentsComponent.PACKET_CODEC,
            AdvancedAlchemyRecipe::to,
            PacketCodecs.STRING,
            AdvancedAlchemyRecipe::translation,
            PacketCodecs.STRING,
            AdvancedAlchemyRecipe::requiredTranslation,
            AdvancedAlchemyRecipe::new
    );

    @Override
    public MapCodec<AdvancedAlchemyRecipe> codec() {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, AdvancedAlchemyRecipe> packetCodec() {
        return PACKET_CODEC;
    }
}
