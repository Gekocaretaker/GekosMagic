package com.gekocaretaker.gekosmagic.recipe;

import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public record AdvancedAlchemyRecipe(ElixirContentsComponent from, RegistryEntry<Essence> ingredient, ElixirContentsComponent to,
                                    String translation, String requiredTranslation) implements Recipe<RecipeInput> {

    @Override
    public boolean matches(RecipeInput input, World world) {
        return false;
    }

    @Override
    public ItemStack craft(RecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.ADVANCED_ALCHEMY;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AdvancedAlchemyRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "advanced_alchemy";
    }
}
