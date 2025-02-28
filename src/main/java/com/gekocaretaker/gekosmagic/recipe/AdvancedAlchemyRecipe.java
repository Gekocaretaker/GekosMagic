package com.gekocaretaker.gekosmagic.recipe;

import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.recipe.input.AlchemyRecipeInput;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.world.World;

import java.util.Objects;

public record AdvancedAlchemyRecipe(ElixirContentsComponent from, RegistryEntry<Essence> ingredient, ElixirContentsComponent to,
                                    String translation, String requiredTranslation) implements Recipe<AlchemyRecipeInput> {
    @Override
    public boolean matches(AlchemyRecipeInput input, World world) {
        if (!input.getItemStack().contains(DataComponentTypes.ITEM_NAME)) {
            return false;
        }

        String itemsTranslation = input.getItemStack().getItem().getTranslationKey() + ".custom." + this.requiredTranslation;

        return input.matchesContents(this.from) && input.getEssenceContainer().isOf(this.ingredient) && Objects.equals(itemsTranslation, ((TranslatableTextContent)input.getItemStack().get(DataComponentTypes.ITEM_NAME).getContent()).getKey());
    }

    @Override
    public ItemStack craft(AlchemyRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return ElixirContentsComponent.createStack(input.getItemStack().getItem(), this.to, input.getItemStack().getItem().getTranslationKey() + ".custom." + this.translation, input.getItemStack().getCount());
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
        return ModRecipeSerializers.ADVANCED_ALCHEMY;
    }

    @Override
    public RecipeType<? extends Recipe<AlchemyRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AdvancedAlchemyRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "advanced_alchemy";
    }
}
