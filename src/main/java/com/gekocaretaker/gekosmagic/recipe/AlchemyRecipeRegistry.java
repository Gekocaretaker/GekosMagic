package com.gekocaretaker.gekosmagic.recipe;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.elixir.Elixirs;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.item.*;
import com.gekocaretaker.gekosmagic.recipe.alchemy.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class AlchemyRecipeRegistry {
    public static final AlchemyRecipeRegistry EMPTY = new AlchemyRecipeRegistry(List.of(), List.of(), List.of(), List.of(), List.of());
    private final List<Essence> elixirTypes;
    private final List<AlchemyElixirRecipe> elixirRecipes;
    private final List<AlchemyItemRecipe> itemRecipes;
    private final List<AlchemyCustomRecipe> customRecipes;
    private final List<AlchemyAdvancedRecipe> advancedRecipes;

    AlchemyRecipeRegistry(List<Essence> elixirTypes, List<AlchemyElixirRecipe> elixirRecipes, List<AlchemyItemRecipe> itemRecipes, List<AlchemyCustomRecipe> customRecipes, List<AlchemyAdvancedRecipe> advancedRecipes) {
        this.elixirTypes = elixirTypes;
        this.elixirRecipes = elixirRecipes;
        this.itemRecipes = itemRecipes;
        this.customRecipes = customRecipes;
        this.advancedRecipes = advancedRecipes;
    }

    public boolean isValidIngredient(Essence essence) {
        return this.isElixirRecipeIngredient(essence) ||
                this.isCustomRecipeIngredient(essence) ||
                this.isAdvancedRecipeIngredient(essence) ||
                this.isItemRecipeIngredient(essence);
    }

    public boolean isElixirType(Essence essence) {
        Iterator iterator = this.elixirTypes.iterator();
        Essence essence1;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            essence1 = (Essence) iterator.next();
        } while (essence1 != essence);
        return true;
    }

    public boolean isItemRecipeIngredient(Essence essence) {
        Iterator iterator = this.itemRecipes.iterator();
        AlchemyItemRecipe recipe;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            recipe = (AlchemyItemRecipe) iterator.next();
        } while (recipe.ingredient() != essence);
        return true;
    }

    public boolean isElixirRecipeIngredient(Essence essence) {
        Iterator iterator = this.elixirRecipes.iterator();
        AlchemyElixirRecipe recipe;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            recipe = (AlchemyElixirRecipe) iterator.next();
        } while (recipe.ingredient() != essence);
        return true;
    }

    public boolean isCustomRecipeIngredient(Essence essence) {
        Iterator iterator = this.customRecipes.iterator();
        AlchemyCustomRecipe recipe;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            recipe = (AlchemyCustomRecipe) iterator.next();
        } while (recipe.ingredient() != essence);
        return true;
    }

    public boolean isAdvancedRecipeIngredient(Essence essence) {
        Iterator iterator = this.advancedRecipes.iterator();
        AlchemyAdvancedRecipe recipe;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            recipe = (AlchemyAdvancedRecipe) iterator.next();
        } while (recipe.ingredient() != essence);
        return true;
    }

    public boolean hasRecipe(ItemStack input, Essence ingredient) {
        if (!this.isElixirType(ingredient)) {
            return false;
        } else {
            return this.hasItemRecipe(input, ingredient) ||
                    this.hasElixirRecipe(input, ingredient) ||
                    this.hasCustomRecipe(input, ingredient) ||
                    this.hasAdvancedRecipe(input, ingredient);
        }
    }

    public boolean hasItemRecipe(ItemStack input, Essence ingredient) {
        Iterator iterator = this.itemRecipes.iterator();
        AlchemyItemRecipe recipe;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            recipe = (AlchemyItemRecipe) iterator.next();
        } while (!input.itemMatches(recipe.from()) || recipe.ingredient() != ingredient);
        return true;
    }

    public boolean hasElixirRecipe(ItemStack input, Essence ingredient) {
        Optional<RegistryEntry<Elixir>> optional = ((ElixirContentsComponent) input.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT)).elixir();
        if (optional.isEmpty()) {
            return false;
        } else {
            Iterator iterator = this.elixirRecipes.iterator();
            AlchemyElixirRecipe recipe;
            do {
                if (!iterator.hasNext()) {
                    return false;
                }
                recipe = (AlchemyElixirRecipe) iterator.next();
            } while (!recipe.from().matches((RegistryEntry) optional.get()) || recipe.ingredient() != ingredient);
            return true;
        }
    }

    public boolean hasCustomRecipe(ItemStack input, Essence ingredient) {
        Optional<RegistryEntry<Elixir>> optional = input.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT).elixir();
        if (optional.isEmpty()) {
            return false;
        } else {
            Iterator iterator = this.customRecipes.iterator();
            AlchemyCustomRecipe recipe;
            do {
                if (!iterator.hasNext()) {
                    return false;
                }
                recipe = (AlchemyCustomRecipe) iterator.next();
            } while (!recipe.from().matches((RegistryEntry) optional.get()) || recipe.ingredient() != ingredient);
            return true;
        }
    }

    public boolean hasAdvancedRecipe(ItemStack input, Essence ingredient) {
        ElixirContentsComponent contents = input.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT);
        if (contents == ElixirContentsComponent.DEFAULT) {
            return false;
        } else {
            Iterator iterator = this.advancedRecipes.iterator();
            AlchemyAdvancedRecipe recipe;
            do {
                if (!iterator.hasNext()) {
                    return false;
                }
                recipe = (AlchemyAdvancedRecipe) iterator.next();
            } while (!recipe.fromContents().equals(contents) || recipe.ingredient() != ingredient);
            return true;
        }
    }

    public ItemStack craft(Essence ingredient, ItemStack input) {
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
                Iterator iterator = this.itemRecipes.iterator();
                AlchemyRecipe recipe;
                do {
                    if (!iterator.hasNext()) {
                        iterator = this.advancedRecipes.iterator();
                        do {
                            if (!iterator.hasNext()) {
                                iterator = this.customRecipes.iterator();
                                do {
                                    if (!iterator.hasNext()) {
                                        iterator = this.elixirRecipes.iterator();
                                        do {
                                            if (!iterator.hasNext()) {
                                                return input;
                                            }
                                            recipe = (AlchemyRecipe) iterator.next();
                                        } while (!recipe.from().matches(elixir) || recipe.ingredient() != ingredient);
                                        return ElixirContentsComponent.createStack(input.getItem(), recipe.to());
                                    }
                                    recipe = (AlchemyRecipe) iterator.next();
                                } while (!recipe.from().matches(elixir) || recipe.ingredient() != ingredient);
                                return ElixirContentsComponent.createStack(input.getItem(), ((AlchemyCustomRecipe) recipe).contents());
                            }
                            recipe = (AlchemyRecipe) iterator.next();
                        } while (!((AlchemyAdvancedRecipe) recipe).fromContents().equals(elixirContentsComponent) || recipe.ingredient() != ingredient);
                        return ElixirContentsComponent.createStack(input.getItem(), ((AlchemyAdvancedRecipe) recipe).toContents());
                    }
                    recipe = (AlchemyRecipe) iterator.next();
                } while (!input.itemMatches(recipe.from()) || recipe.ingredient() != ingredient);
                return ElixirContentsComponent.createStack((Item) recipe.to().value(), elixir);
            }
        }
    }

    public void registerElixirType(Essence essence) {
        this.elixirTypes.add(essence);
    }

    public void registerElixirRecipe(Identifier identifier, RegistryEntry<Elixir> input, Essence ingredient, RegistryEntry<Elixir> output) {
        this.elixirRecipes.add(new AlchemyElixirRecipe(identifier, input, ingredient, output));
    }

    public void registerElixirRecipe(AlchemyElixirRecipe recipe) {
        this.elixirRecipes.add(recipe);
    }

    public void registerCustomRecipe(Identifier identifier, RegistryEntry<Elixir> input, Essence ingredient, ElixirContentsComponent output) {
        this.customRecipes.add(new AlchemyCustomRecipe(identifier, input, ingredient, output));
    }

    public void registerCustomRecipe(AlchemyCustomRecipe recipe) {
        this.customRecipes.add(recipe);
    }

    public void registerAdvancedRecipe(Identifier identifier, ElixirContentsComponent input, Essence ingredient, ElixirContentsComponent output) {
        this.advancedRecipes.add(new AlchemyAdvancedRecipe(identifier, input, ingredient, output));
    }

    public void registerAdvancedRecipe(AlchemyAdvancedRecipe recipe) {
        this.advancedRecipes.add(recipe);
    }

    public void registerItemRecipe(Identifier identifier, Item input, Essence ingredient, Item output) {
        try {
            assertElixir(input);
            assertElixir(output);
            this.itemRecipes.add(new AlchemyItemRecipe(identifier, input.getRegistryEntry(), ingredient, output.getRegistryEntry()));
        } catch (IllegalArgumentException e) {
            Gekosmagic.LOGGER.error(e.getMessage());
        }
    }

    public void registerItemRecipe(AlchemyItemRecipe recipe) {
        try {
            assertElixir(recipe.from().value());
            assertElixir(recipe.to().value());
            this.itemRecipes.add(recipe);
        } catch (IllegalArgumentException e) {
            Gekosmagic.LOGGER.error(e.getMessage());
        }
    }

    public void clear() {
        //this.elixirTypes.clear();
        this.itemRecipes.clear();
        this.elixirRecipes.clear();
        this.customRecipes.clear();
        this.advancedRecipes.clear();
    }

    public List<AlchemyRecipe> getRecipes() {
        List<AlchemyRecipe> recipes = new ArrayList<>();

        recipes.addAll(List.copyOf(this.elixirRecipes));
        recipes.addAll(List.copyOf(this.customRecipes));
        recipes.addAll(List.copyOf(this.advancedRecipes));
        recipes.addAll(List.copyOf(this.itemRecipes));

        return recipes;
    }

    public List<AlchemyElixirRecipe> getElixirRecipes() {
        return List.copyOf(this.elixirRecipes);
    }

    public List<AlchemyCustomRecipe> getCustomRecipes() {
        return List.copyOf(this.customRecipes);
    }

    public List<AlchemyAdvancedRecipe> getAdvancedRecipes() {
        return List.copyOf(this.advancedRecipes);
    }

    public List<AlchemyItemRecipe> getItemRecipes() {
        return List.copyOf(this.itemRecipes);
    }

    private static void assertElixir(Item elixirItem) throws IllegalArgumentException {
        if (!(elixirItem instanceof ElixirItem ||
                elixirItem instanceof SplashElixirItem ||
                elixirItem instanceof LingeringElixirItem ||
                elixirItem instanceof ButteredElixirItem ||
                elixirItem instanceof UninterestingElixirItem ||
                elixirItem instanceof ClearElixirItem ||
                elixirItem instanceof DiffusingElixir)) {
            throw new IllegalArgumentException("Expected an elixir, got: " + String.valueOf(Registries.ITEM.getId(elixirItem)));
        }
    }

    public static AlchemyRecipeRegistry create() {
        return new AlchemyRecipeRegistry(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}
