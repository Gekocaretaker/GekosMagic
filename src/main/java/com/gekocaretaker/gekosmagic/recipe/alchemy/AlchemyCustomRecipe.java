package com.gekocaretaker.gekosmagic.recipe.alchemy;

import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.elixir.Elixirs;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public record AlchemyCustomRecipe(Identifier identifier, RegistryEntry<Elixir> from, Essence ingredient, ElixirContentsComponent contents) implements AlchemyRecipe<Elixir> {
    public static final Codec<AlchemyCustomRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("identifier").forGetter(AlchemyCustomRecipe::identifier),
            Elixir.CODEC.fieldOf("from").forGetter(AlchemyCustomRecipe::from),
            ModRegistries.ESSENCE.getCodec().fieldOf("essence").forGetter(AlchemyCustomRecipe::ingredient),
            ElixirContentsComponent.CODEC.fieldOf("to").forGetter(AlchemyCustomRecipe::contents)
    ).apply(instance, AlchemyCustomRecipe::new));

    public static final AlchemyCustomRecipe EMPTY = new AlchemyCustomRecipe(null, null, null, null);

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
        return Elixirs.WATER;
    }

    @Override
    public ElixirContentsComponent contents() {
        return this.contents;
    }

    @Override
    public Identifier id() {
        return this.identifier;
    }

    public static boolean recipeExists(AlchemyCustomRecipe recipe) {
        return recipe != null && !recipe.equals(EMPTY);
    }
}
