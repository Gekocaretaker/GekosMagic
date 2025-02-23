package com.gekocaretaker.gekosmagic.recipe;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipeTypes {
    public static final RecipeType<ElixirRecipe> ELIXIR = register(ElixirRecipe.Type.ID, ElixirRecipe.Type.INSTANCE);
    public static final RecipeType<ItemAlchemyRecipe> ITEM_ALCHEMY = register(ItemAlchemyRecipe.Type.ID, ItemAlchemyRecipe.Type.INSTANCE);
    public static final RecipeType<BasicAlchemyRecipe> BASIC_ALCHEMY = register(BasicAlchemyRecipe.Type.ID, BasicAlchemyRecipe.Type.INSTANCE);
    public static final RecipeType<AdvancedAlchemyRecipe> ADVANCED_ALCHEMY = register(AdvancedAlchemyRecipe.Type.ID, AdvancedAlchemyRecipe.Type.INSTANCE);

    private static <T extends RecipeType<R>, R extends Recipe<?>> T register(String id, T recipeType) {
        return Registry.register(Registries.RECIPE_TYPE, Gekosmagic.identify(id), recipeType);
    }

    public static void init() {}

    private ModRecipeTypes() {}
}
