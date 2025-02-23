package com.gekocaretaker.gekosmagic.recipe;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.recipe.serializer.AdvancedAlchemySerializer;
import com.gekocaretaker.gekosmagic.recipe.serializer.BasicAlchemySerializer;
import com.gekocaretaker.gekosmagic.recipe.serializer.ItemAlchemySerializer;
import com.gekocaretaker.gekosmagic.recipe.serializer.ElixirSerializer;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipeSerializers {
    public static final RecipeSerializer<ElixirArrowRecipe> ELIXIR_ARROW = register("crafting_special_elixir_tipped_arrow", new SpecialRecipeSerializer<>(ElixirArrowRecipe::new));
    public static final RecipeSerializer<ElixirRecipe> ELIXIR = register("elixir", new ElixirSerializer());
    public static final RecipeSerializer<ItemAlchemyRecipe> ITEM_ALCHEMY = register("item_alchemy", new ItemAlchemySerializer());
    public static final RecipeSerializer<BasicAlchemyRecipe> BASIC_ALCHEMY = register("basic_alchemy", new BasicAlchemySerializer());
    public static final RecipeSerializer<AdvancedAlchemyRecipe> ADVANCED_ALCHEMY = register("advanced_alchemy", new AdvancedAlchemySerializer());

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, Gekosmagic.identify(id), serializer);
    }

    public static void init() {}

    private ModRecipeSerializers() {}
}
