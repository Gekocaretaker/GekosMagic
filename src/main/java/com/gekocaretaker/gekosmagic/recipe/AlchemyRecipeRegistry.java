package com.gekocaretaker.gekosmagic.recipe;

import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.*;
import com.gekocaretaker.gekosmagic.util.ModTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.world.World;

import java.util.*;

public class AlchemyRecipeRegistry {
    AlchemyRecipeRegistry() {}

    private boolean essenceEntriesAreNotSame(RegistryEntry<Essence> firstEssence, Essence secondEssence) {
        return !Objects.equals(firstEssence.getIdAsString(), secondEssence.id().toString());
    }

    public boolean isValidIngredient(World world, Essence essence) {
        RecipeManager manager = world.getRecipeManager();
        return this.isElixirRecipeIngredient(manager, essence) ||
                this.isBasicAlchemyRecipeIngredient(manager, essence) ||
                this.isAdvancedRecipeIngredient(manager, essence) ||
                this.isItemAlchemyRecipeIngredient(manager, essence);
    }

    public boolean isElixirIngredient(ItemStack itemStack) {
        return itemStack.isIn(ModTags.ESSENCES);
    }

    public boolean isItemAlchemyRecipeIngredient(RecipeManager manager, Essence essence) {
        Iterator<RecipeEntry<ItemAlchemyRecipe>> iterator = manager.listAllOfType(ModRecipeTypes.ITEM_ALCHEMY).iterator();
        ItemAlchemyRecipe recipe;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            recipe = iterator.next().value();
        } while (essenceEntriesAreNotSame(recipe.ingredient(), essence));
        return true;
    }

    public boolean isElixirRecipeIngredient(RecipeManager manager, Essence essence) {
        Iterator<RecipeEntry<ElixirRecipe>> iterator = manager.listAllOfType(ModRecipeTypes.ELIXIR).iterator();
        ElixirRecipe recipe;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            recipe = iterator.next().value();
        } while (essenceEntriesAreNotSame(recipe.ingredient(), essence));
        return true;
    }

    public boolean isBasicAlchemyRecipeIngredient(RecipeManager manager, Essence essence) {
        Iterator<RecipeEntry<BasicAlchemyRecipe>> iterator = manager.listAllOfType(ModRecipeTypes.BASIC_ALCHEMY).iterator();
        BasicAlchemyRecipe recipe;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            recipe = iterator.next().value();
        } while (essenceEntriesAreNotSame(recipe.ingredient(), essence));
        return true;
    }

    public boolean isAdvancedRecipeIngredient(RecipeManager manager, Essence essence) {
        Iterator<RecipeEntry<AdvancedAlchemyRecipe>> iterator = manager.listAllOfType(ModRecipeTypes.ADVANCED_ALCHEMY).iterator();
        AdvancedAlchemyRecipe recipe;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            recipe = iterator.next().value();
        } while (essenceEntriesAreNotSame(recipe.ingredient(), essence));
        return true;
    }

    public boolean hasRecipe(World world, ItemStack input, Essence ingredient) {
        RecipeManager manager = world.getRecipeManager();
        return this.hasItemAlchemyRecipe(manager, input, ingredient) ||
            this.hasElixirRecipe(manager, input, ingredient) ||
            this.hasBasicAlchemyRecipe(manager, input, ingredient) ||
            this.hasAdvancedAlchemyRecipe(manager, input, ingredient);
    }

    public boolean hasItemAlchemyRecipe(RecipeManager manager, ItemStack input, Essence ingredient) {
        Iterator<RecipeEntry<ItemAlchemyRecipe>> iterator = manager.listAllOfType(ModRecipeTypes.ITEM_ALCHEMY).iterator();
        ItemAlchemyRecipe recipe;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            recipe = iterator.next().value();
        } while (!input.itemMatches(recipe.from()) || essenceEntriesAreNotSame(recipe.ingredient(), ingredient));
        return true;
    }

    public boolean hasElixirRecipe(RecipeManager manager, ItemStack input, Essence ingredient) {
        Optional<RegistryEntry<Elixir>> optional = input.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT).elixir();
        if (optional.isEmpty()) {
            return false;
        } else {
            Iterator<RecipeEntry<ElixirRecipe>> iterator = manager.listAllOfType(ModRecipeTypes.ELIXIR).iterator();
            ElixirRecipe recipe;
            do {
                if (!iterator.hasNext()) {
                    return false;
                }
                recipe = iterator.next().value();
            } while (!recipe.from().matches(optional.get()) || essenceEntriesAreNotSame(recipe.ingredient(), ingredient));
            return true;
        }
    }

    public boolean hasBasicAlchemyRecipe(RecipeManager manager, ItemStack input, Essence ingredient) {
        Optional<RegistryEntry<Elixir>> optional = input.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT).elixir();
        if (optional.isEmpty()) {
            return false;
        } else {
            Iterator<RecipeEntry<BasicAlchemyRecipe>> iterator = manager.listAllOfType(ModRecipeTypes.BASIC_ALCHEMY).iterator();
            BasicAlchemyRecipe recipe;
            do {
                if (!iterator.hasNext()) {
                    return false;
                }
                recipe = iterator.next().value();
            } while (!recipe.from().matches(optional.get()) || essenceEntriesAreNotSame(recipe.ingredient(), ingredient));
            return true;
        }
    }

    public boolean hasAdvancedAlchemyRecipe(RecipeManager manager, ItemStack input, Essence ingredient) {
        ElixirContentsComponent contents = input.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT);
        if (contents == ElixirContentsComponent.DEFAULT) {
            return false;
        } else {
            Iterator<RecipeEntry<AdvancedAlchemyRecipe>> iterator = manager.listAllOfType(ModRecipeTypes.ADVANCED_ALCHEMY).iterator();
            AdvancedAlchemyRecipe recipe;
            boolean hasName = false;
            do {
                if (!iterator.hasNext()) {
                    return false;
                }
                recipe = iterator.next().value();
                if (input.get(DataComponentTypes.ITEM_NAME) != null) {
                    if (Objects.equals(makeTranslationKey(input, recipe.requiredTranslation()), ((TranslatableTextContent) input.get(DataComponentTypes.ITEM_NAME).getContent()).getKey())) {
                        hasName = true;
                    }
                }
            } while (!recipe.from().equals(contents) || !hasName || essenceEntriesAreNotSame(recipe.ingredient(), ingredient));
            return true;
        }
    }

    public static String makeTranslationKey(ItemStack itemStack, String translation) {
        return itemStack.getItem().getTranslationKey() + ".custom." + translation;
    }

    public ItemStack craft(World world, Essence ingredient, ItemStack input) {
        if (input.isEmpty()) {
            return input;
        } else {
            ElixirContentsComponent elixirContentsComponent = input.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT);
            Optional<RegistryEntry<Elixir>> optional = elixirContentsComponent.elixir();
            RegistryEntry<Elixir> elixir;
            elixir = optional.orElse(Elixirs.WATER);
            if (!elixirContentsComponent.hasEffects() && optional.isEmpty()) {
                return input;
            } else {
                List<RecipeEntry<ItemAlchemyRecipe>> itemRecipes = world.getRecipeManager().listAllOfType(ModRecipeTypes.ITEM_ALCHEMY);
                Iterator iterator = itemRecipes.iterator();
                ItemAlchemyRecipe iaRecipe;
                do {
                    if (!iterator.hasNext()) {
                        List<RecipeEntry<AdvancedAlchemyRecipe>> advancedAlchemyRecipes = world.getRecipeManager().listAllOfType(ModRecipeTypes.ADVANCED_ALCHEMY);
                        iterator = advancedAlchemyRecipes.iterator();
                        AdvancedAlchemyRecipe aaRecipe;
                        boolean hasName = false;
                        do {
                            if (!iterator.hasNext()) {
                                List<RecipeEntry<BasicAlchemyRecipe>> basicAlchemyRecipes = world.getRecipeManager().listAllOfType(ModRecipeTypes.BASIC_ALCHEMY);
                                iterator = basicAlchemyRecipes.iterator();
                                BasicAlchemyRecipe baRecipe;
                                do {
                                    if (!iterator.hasNext()) {
                                        List<RecipeEntry<ElixirRecipe>> elixirRecipes = world.getRecipeManager().listAllOfType(ModRecipeTypes.ELIXIR);
                                        iterator = elixirRecipes.iterator();
                                        ElixirRecipe elixirRecipe;
                                        do {
                                            if (!iterator.hasNext()) {
                                                return input;
                                            }
                                            elixirRecipe = ((RecipeEntry<ElixirRecipe>) iterator.next()).value();
                                        } while (!elixirRecipe.from().matches(elixir) || essenceEntriesAreNotSame(elixirRecipe.ingredient(), ingredient) || elixirContentsComponent.elixir().isEmpty());
                                        return ElixirContentsComponent.createStack(input.getItem(), elixirRecipe.to());
                                    }
                                    baRecipe = ((RecipeEntry<BasicAlchemyRecipe>) iterator.next()).value();
                                } while (!baRecipe.from().matches(elixir) || essenceEntriesAreNotSame(baRecipe.ingredient(), ingredient) || elixirContentsComponent.elixir().isEmpty());
                                return ElixirContentsComponent.createStack(input.getItem(), baRecipe.to(), makeTranslationKey(input, baRecipe.translation()));
                            }
                            aaRecipe = ((RecipeEntry<AdvancedAlchemyRecipe>) iterator.next()).value();
                            if (input.get(DataComponentTypes.ITEM_NAME) != null) {
                                if (Objects.equals(makeTranslationKey(input, aaRecipe.requiredTranslation()), ((TranslatableTextContent) input.get(DataComponentTypes.ITEM_NAME).getContent()).getKey())) {
                                    hasName = true;
                                }
                            }
                        } while (!aaRecipe.from().equals(elixirContentsComponent) || !hasName || essenceEntriesAreNotSame(aaRecipe.ingredient(), ingredient));
                        return ElixirContentsComponent.createStack(input.getItem(), aaRecipe.to(), makeTranslationKey(input, aaRecipe.translation()));
                    }
                    iaRecipe = ((RecipeEntry<ItemAlchemyRecipe>) iterator.next()).value();
                } while (!input.itemMatches(iaRecipe.from()) || essenceEntriesAreNotSame(iaRecipe.ingredient(), ingredient));
                return ElixirContentsComponent.createStack(iaRecipe.to().value(), elixir);
            }
        }
    }

    public static AlchemyRecipeRegistry create() {
        return new AlchemyRecipeRegistry();
    }
}
