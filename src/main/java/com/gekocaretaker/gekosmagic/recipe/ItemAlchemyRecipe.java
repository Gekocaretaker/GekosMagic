package com.gekocaretaker.gekosmagic.recipe;

import com.gekocaretaker.gekosmagic.elixir.Essence;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public record ItemAlchemyRecipe(RegistryEntry<Item> from, RegistryEntry<Essence> ingredient,
                                RegistryEntry<Item> to) implements Recipe<RecipeInput> {
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
        return ModRecipeSerializers.ITEM_ALCHEMY;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ItemAlchemyRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "item_alchemy";
    }
}
