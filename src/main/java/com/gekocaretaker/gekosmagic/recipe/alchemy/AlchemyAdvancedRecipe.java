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

public record AlchemyAdvancedRecipe(Identifier identifier, ElixirContentsComponent fromContents, Essence ingredient, ElixirContentsComponent toContents) implements AlchemyRecipe<Elixir> {
    public static final Codec<AlchemyAdvancedRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Identifier.CODEC.fieldOf("identifier").forGetter(AlchemyAdvancedRecipe::identifier),
        ElixirContentsComponent.CODEC.fieldOf("from").forGetter(AlchemyAdvancedRecipe::fromContents),
        ModRegistries.ESSENCE.getCodec().fieldOf("essence").forGetter(AlchemyAdvancedRecipe::ingredient),
        ElixirContentsComponent.CODEC.fieldOf("to").forGetter(AlchemyAdvancedRecipe::toContents)
    ).apply(instance, AlchemyAdvancedRecipe::new));

    public static final AlchemyAdvancedRecipe EMPTY = new AlchemyAdvancedRecipe(null, null, null, null);

    @Override
    public RegistryEntry<Elixir> from() {
        return Elixirs.WATER;
    }

    @Override
    public ElixirContentsComponent fromContents() {
        return this.fromContents;
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
    public ElixirContentsComponent toContents() {
        return this.toContents;
    }

    @Override
    public Identifier id() {
        return this.identifier;
    }

    public static boolean recipeExists(AlchemyAdvancedRecipe recipe) {
        return recipe != null && !recipe.equals(EMPTY);
    }
}
