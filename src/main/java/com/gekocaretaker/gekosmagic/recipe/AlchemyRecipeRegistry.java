package com.gekocaretaker.gekosmagic.recipe;

public class AlchemyRecipeRegistry {
    /*private boolean essenceEntriesAreNotSame(RegistryEntry<Essence> firstEssence, Essence secondEssence) {
        return !Objects.equals(firstEssence.getIdAsString(), secondEssence.id().toString());
    }

    public boolean isValidIngredient(World world, Essence essence) {
        if (world instanceof ServerWorld serverWorld) {
            ServerRecipeManager manager = serverWorld.getRecipeManager();
            return this.isElixirRecipeIngredient(manager, essence) ||
                    this.isBasicAlchemyRecipeIngredient(manager, essence) ||
                    this.isAdvancedRecipeIngredient(manager, essence) ||
                    this.isItemAlchemyRecipeIngredient(manager, essence);
        } else {
            return false;
        }
    }

    public boolean isItemAlchemyRecipeIngredient(ServerRecipeManager manager, Essence essence) {
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

    public boolean isElixirRecipeIngredient(ServerRecipeManager manager, Essence essence) {
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

    public boolean isBasicAlchemyRecipeIngredient(ServerRecipeManager manager, Essence essence) {
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

    public boolean isAdvancedRecipeIngredient(ServerRecipeManager manager, Essence essence) {
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
        if (world instanceof ServerWorld serverWorld) {
            ServerRecipeManager manager = serverWorld.getRecipeManager();
            return this.hasItemAlchemyRecipe(manager, input, ingredient) ||
                    this.hasElixirRecipe(manager, input, ingredient) ||
                    this.hasBasicAlchemyRecipe(manager, input, ingredient) ||
                    this.hasAdvancedAlchemyRecipe(manager, input, ingredient);
        } else {
            return false;
        }
    }

    public boolean hasItemAlchemyRecipe(ServerRecipeManager manager, ItemStack input, Essence ingredient) {
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

    public boolean hasElixirRecipe(ServerRecipeManager manager, ItemStack input, Essence ingredient) {
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

    public boolean hasBasicAlchemyRecipe(ServerRecipeManager manager, ItemStack input, Essence ingredient) {
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

    public boolean hasAdvancedAlchemyRecipe(ServerRecipeManager manager, ItemStack input, Essence ingredient) {
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
        if (world instanceof ServerWorld serverWorld) {
            if (input.isEmpty()) {
                return input;
            } else {
                ServerRecipeManager recipeManager = serverWorld.getRecipeManager();

                ElixirContentsComponent elixirContentsComponent = input.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT);
                Optional<RegistryEntry<Elixir>> optional = elixirContentsComponent.elixir();
                RegistryEntry<Elixir> elixir;
                elixir = optional.orElse(Elixirs.WATER);

                if (!elixirContentsComponent.hasEffects() && optional.isEmpty()) {
                    return input;
                } else {
                    List<RecipeEntry<ItemAlchemyRecipe>> itemRecipes = recipeManager.listAllOfType(ModRecipeTypes.ITEM_ALCHEMY);
                    Iterator iterator = itemRecipes.iterator();
                    ItemAlchemyRecipe iaRecipe;
                    do {
                        if (!iterator.hasNext()) {
                            List<RecipeEntry<AdvancedAlchemyRecipe>> advancedAlchemyRecipes = recipeManager.listAllOfType(ModRecipeTypes.ADVANCED_ALCHEMY);
                            iterator = advancedAlchemyRecipes.iterator();
                            AdvancedAlchemyRecipe aaRecipe;
                            boolean hasName = false;
                            do {
                                if (!iterator.hasNext()) {
                                    List<RecipeEntry<BasicAlchemyRecipe>> basicAlchemyRecipes = recipeManager.listAllOfType(ModRecipeTypes.BASIC_ALCHEMY);
                                    iterator = basicAlchemyRecipes.iterator();
                                    BasicAlchemyRecipe baRecipe;
                                    do {
                                        if (!iterator.hasNext()) {
                                            List<RecipeEntry<ElixirRecipe>> elixirRecipes = recipeManager.listAllOfType(ModRecipeTypes.ELIXIR);
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
        return input;
    }*/
}
