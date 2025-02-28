package com.gekocaretaker.gekosmagic.recipe;

import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.recipe.input.AlchemyRecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public record BasicAlchemyRecipe(RegistryEntry<Elixir> from, RegistryEntry<Essence> ingredient, ElixirContentsComponent to,
                                 String translation) implements Recipe<AlchemyRecipeInput> {
    @Override
    public boolean matches(AlchemyRecipeInput input, World world) {
        return input.matchesContents(new ElixirContentsComponent(this.from)) && input.getEssenceContainer().isOf(this.ingredient);
    }

    @Override
    public ItemStack craft(AlchemyRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return ElixirContentsComponent.createStack(input.getItemStack().getItem(), this.to, input.getItemStack().getItem().getTranslationKey() + this.translation, input.getItemStack().getCount());
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forSingleSlot(Ingredient.ofItem(ModItems.ELIXIR));
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return new RecipeBookCategory();
    }

    @Override
    public RecipeSerializer<? extends Recipe<AlchemyRecipeInput>> getSerializer() {
        return ModRecipeSerializers.BASIC_ALCHEMY;
    }

    @Override
    public RecipeType<? extends Recipe<AlchemyRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BasicAlchemyRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "basic_alchemy";
    }
}
