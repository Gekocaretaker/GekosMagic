package com.gekocaretaker.gekosmagic.recipe.alchemy;

import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public record AlchemyItemRecipe(Identifier identifier, RegistryEntry<Item> from, Essence ingredient, RegistryEntry<Item> to) implements AlchemyRecipe<Item> {
    public static final Codec<AlchemyItemRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("identifier").forGetter(AlchemyItemRecipe::identifier),
            Registries.ITEM.getEntryCodec().fieldOf("from").forGetter(AlchemyItemRecipe::from),
            ModRegistries.ESSENCE.getCodec().fieldOf("essence").forGetter(AlchemyItemRecipe::ingredient),
            Registries.ITEM.getEntryCodec().fieldOf("to").forGetter(AlchemyItemRecipe::to)
    ).apply(instance, AlchemyItemRecipe::new));

    public static final AlchemyItemRecipe EMPTY = new AlchemyItemRecipe(null, null, null, null);

    @Override
    public RegistryEntry<Item> from() {
        return this.from;
    }

    @Override
    public Essence ingredient() {
        return this.ingredient;
    }

    @Override
    public RegistryEntry<Item> to() {
        return this.to;
    }

    @Override
    public Identifier id() {
        return this.identifier;
    }

    public static boolean recipeExists(AlchemyItemRecipe recipe) {
        return recipe != null && !recipe.equals(EMPTY);
    }
}
