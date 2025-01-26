package com.gekocaretaker.gekosmagic.recipe.alchemy;

import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public record AlchemyElixirRecipe(Identifier identifier, RegistryEntry<Elixir> from, Essence ingredient, RegistryEntry<Elixir> to) implements AlchemyRecipe<Elixir> {
    public static final Codec<AlchemyElixirRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("identifier").forGetter(AlchemyElixirRecipe::identifier),
            Elixir.CODEC.fieldOf("from").forGetter(AlchemyElixirRecipe::from),
            ModRegistries.ESSENCE.getCodec().fieldOf("essence").forGetter(AlchemyElixirRecipe::ingredient),
            Elixir.CODEC.fieldOf("to").forGetter(AlchemyElixirRecipe::to)
    ).apply(instance, AlchemyElixirRecipe::new));

    public static final AlchemyElixirRecipe EMPTY = new AlchemyElixirRecipe(null, null, null, null);

    @Override
    public RegistryEntry<Elixir> from() {
        return this.from;
    }

    @Override
    public Essence ingredient() {
        return this.ingredient;
    }

    @Override
    public RegistryEntry<Elixir> to() {
        return this.to;
    }

    @Override
    public Identifier id() {
        return this.identifier;
    }

    public static boolean recipeExists(AlchemyElixirRecipe recipe) {
        return recipe != null && !recipe.equals(EMPTY);
    }
}