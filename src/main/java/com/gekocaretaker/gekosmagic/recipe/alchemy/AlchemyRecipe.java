package com.gekocaretaker.gekosmagic.recipe.alchemy;

import com.gekocaretaker.gekosmagic.elixir.Essence;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public interface AlchemyRecipe<T> {
    RegistryEntry<T> from();
    Essence ingredient();
    RegistryEntry<T> to();
    Identifier id();
}
