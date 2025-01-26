package com.gekocaretaker.gekosmagic.recipe;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipeSerializers {
    public static final RecipeSerializer<ElixirArrowRecipe> ELIXIR_ARROW = register("crafting_special_elixir_tipped_arrow", new SpecialRecipeSerializer<>(ElixirArrowRecipe::new));

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, Gekosmagic.identify(id), serializer);
    }

    public static void init() {}

    private ModRecipeSerializers() {}
}
