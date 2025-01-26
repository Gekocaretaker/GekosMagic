package com.gekocaretaker.gekosmagic.compat.emi;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.block.ModBlocks;
import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.recipe.alchemy.AlchemyAdvancedRecipe;
import com.gekocaretaker.gekosmagic.recipe.alchemy.AlchemyCustomRecipe;
import com.gekocaretaker.gekosmagic.recipe.alchemy.AlchemyElixirRecipe;
import com.gekocaretaker.gekosmagic.recipe.alchemy.AlchemyItemRecipe;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.Comparison;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class GekosmagicEmi implements EmiPlugin {
    public static final Identifier BACKGROUND_TEXTURE = Gekosmagic.identify("textures/gui/container/alchemy_stand.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.ALCHEMY_STAND_BLOCK);
    public static final EmiRecipeCategory ALCHEMY_CATEGORY
            = new EmiRecipeCategory(Gekosmagic.identify("alchemy"), WORKSTATION, new EmiTexture(BACKGROUND_TEXTURE, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry emiRegistry) {
        emiRegistry.addCategory(ALCHEMY_CATEGORY);

        emiRegistry.addWorkstation(ALCHEMY_CATEGORY, WORKSTATION);

        Comparison elixirComparison = Comparison.compareData(stack -> stack.get(ModDataComponentTypes.ELIXIR_CONTENTS));

        emiRegistry.setDefaultComparison(ModItems.ELIXIR, elixirComparison);
        emiRegistry.setDefaultComparison(ModItems.SPLASH_ELIXIR, elixirComparison);
        emiRegistry.setDefaultComparison(ModItems.LINGERING_ELIXIR, elixirComparison);
        emiRegistry.setDefaultComparison(ModItems.BUTTERED_ELIXIR, elixirComparison);
        emiRegistry.setDefaultComparison(ModItems.CLEAR_ELIXIR, elixirComparison);
        emiRegistry.setDefaultComparison(ModItems.UNINTERESTING_ELIXIR, elixirComparison);
        emiRegistry.setDefaultComparison(ModItems.BLAND_ELIXIR, elixirComparison);
        emiRegistry.setDefaultComparison(ModItems.DIFFUSING_ELIXIR, elixirComparison);

        EmiStack arrow = EmiStack.of(Items.ARROW);
        ModRegistries.ELIXIR.forEach(elixir -> {
            emiRegistry.addRecipe(new EmiCraftingRecipe(
                    List.of(arrow, arrow, arrow, arrow,
                    EmiStack.of(ElixirContentsComponent.createStack(ModItems.LINGERING_ELIXIR, ModRegistries.ELIXIR.getEntry(elixir))),
                    arrow, arrow, arrow, arrow),
                    EmiStack.of(ElixirContentsComponent.createPotionContentsStack(Items.TIPPED_ARROW, 8, ModRegistries.ELIXIR.getEntry(elixir))),
                    Gekosmagic.identify("/crafting_tipped_arrow/" + ModRegistries.ELIXIR.getId(elixir).getPath()), false));
        });

        List<Elixir> alreadyCreatedItemConversion = new ArrayList<>();
        for (AlchemyElixirRecipe recipe : Gekosmagic.alchemyRecipeRegistry.getElixirRecipes()) {
            emiRegistry.addRecipe(new AlchemyEmiRecipe(
                    recipe.id().withPrefixedPath("/"),
                    EmiIngredient.of(Ingredient.ofStacks(
                            ElixirContentsComponent.createStack(ModItems.ELIXIR, recipe.from())
                    )),
                    recipe.ingredient(),
                    EmiStack.of(ElixirContentsComponent.createStack(ModItems.ELIXIR, recipe.to()))
            ));
            for (AlchemyItemRecipe itemRecipe : Gekosmagic.alchemyRecipeRegistry.getItemRecipes()) {
                if (!alreadyCreatedItemConversion.contains(recipe.from().value())) {
                    emiRegistry.addRecipe(new AlchemyEmiRecipe(
                            recipe.id().withPrefixedPath("/").withSuffixedPath("_to_" + itemRecipe.id().getPath()),
                            EmiIngredient.of(Ingredient.ofStacks(
                                    ElixirContentsComponent.createStack(itemRecipe.from().value(), recipe.from())
                            )),
                            itemRecipe.ingredient(),
                            EmiStack.of(ElixirContentsComponent.createStack(itemRecipe.to().value(), recipe.from()))
                    ));
                }
                emiRegistry.addRecipe(new AlchemyEmiRecipe(
                        recipe.id().withPrefixedPath("/").withSuffixedPath("_as_" + itemRecipe.id().getPath()),
                        EmiIngredient.of(Ingredient.ofStacks(
                                ElixirContentsComponent.createStack(itemRecipe.to().value(), recipe.from())
                        )),
                        recipe.ingredient(),
                        EmiStack.of(ElixirContentsComponent.createStack(itemRecipe.to().value(), recipe.to()))
                ));
            }
            if (!alreadyCreatedItemConversion.contains(recipe.from().value())) {
                alreadyCreatedItemConversion.add(recipe.from().value());
            }
        }

        alreadyCreatedItemConversion.clear();
        for (AlchemyCustomRecipe recipe: Gekosmagic.alchemyRecipeRegistry.getCustomRecipes()) {
            emiRegistry.addRecipe(new AlchemyEmiRecipe(
                    recipe.id().withPrefixedPath("/"),
                    EmiIngredient.of(Ingredient.ofStacks(
                            ElixirContentsComponent.createStack(ModItems.ELIXIR, recipe.from())
                    )),
                    recipe.ingredient(),
                    EmiStack.of(ElixirContentsComponent.createStack(ModItems.ELIXIR, recipe.contents()))
            ));
            for (AlchemyItemRecipe itemRecipe : Gekosmagic.alchemyRecipeRegistry.getItemRecipes()) {
                if (!alreadyCreatedItemConversion.contains(recipe.from().value())) {
                    emiRegistry.addRecipe(new AlchemyEmiRecipe(
                            recipe.id().withPrefixedPath("/").withSuffixedPath("_to_" + itemRecipe.id().getPath()),
                            EmiIngredient.of(Ingredient.ofStacks(
                                    ElixirContentsComponent.createStack(itemRecipe.from().value(), recipe.from())
                            )),
                            itemRecipe.ingredient(),
                            EmiStack.of(ElixirContentsComponent.createStack(itemRecipe.to().value(), recipe.from()))
                    ));
                }
                emiRegistry.addRecipe(new AlchemyEmiRecipe(
                        recipe.id().withPrefixedPath("/").withSuffixedPath("_as_" + itemRecipe.id().getPath()),
                        EmiIngredient.of(Ingredient.ofStacks(
                                ElixirContentsComponent.createStack(itemRecipe.to().value(), recipe.from())
                        )),
                        recipe.ingredient(),
                        EmiStack.of(ElixirContentsComponent.createStack(itemRecipe.to().value(), recipe.contents()))
                ));
            }
            if (!alreadyCreatedItemConversion.contains(recipe.from().value())) {
                alreadyCreatedItemConversion.add(recipe.from().value());
            }
        }

        alreadyCreatedItemConversion.clear();
        List<ElixirContentsComponent> alreadyCreatedItemConversion2 = new ArrayList<>();
        for (AlchemyAdvancedRecipe recipe : Gekosmagic.alchemyRecipeRegistry.getAdvancedRecipes()) {
            emiRegistry.addRecipe(new AlchemyEmiRecipe(
                    recipe.id().withPrefixedPath("/"),
                    EmiIngredient.of(Ingredient.ofStacks(
                            ElixirContentsComponent.createStack(ModItems.ELIXIR, recipe.fromContents())
                    )),
                    recipe.ingredient(),
                    EmiStack.of(ElixirContentsComponent.createStack(ModItems.ELIXIR, recipe.toContents()))
            ));
            for (AlchemyItemRecipe itemRecipe : Gekosmagic.alchemyRecipeRegistry.getItemRecipes()) {
                if (!alreadyCreatedItemConversion2.contains(recipe.fromContents())) {
                    emiRegistry.addRecipe(new AlchemyEmiRecipe(
                            recipe.id().withPrefixedPath("/").withSuffixedPath("_to_" + itemRecipe.id().getPath()),
                            EmiIngredient.of(Ingredient.ofStacks(
                                    ElixirContentsComponent.createStack(itemRecipe.from().value(), recipe.fromContents())
                            )),
                            itemRecipe.ingredient(),
                            EmiStack.of(ElixirContentsComponent.createStack(itemRecipe.to().value(), recipe.fromContents()))
                    ));
                }
                emiRegistry.addRecipe(new AlchemyEmiRecipe(
                        recipe.id().withPrefixedPath("/").withSuffixedPath("_as_" + itemRecipe.id().getPath()),
                        EmiIngredient.of(Ingredient.ofStacks(
                                ElixirContentsComponent.createStack(itemRecipe.to().value(), recipe.fromContents())
                        )),
                        recipe.ingredient(),
                        EmiStack.of(ElixirContentsComponent.createStack(itemRecipe.to().value(), recipe.toContents()))
                ));
            }
            if (!alreadyCreatedItemConversion2.contains(recipe.fromContents())) {
                alreadyCreatedItemConversion2.add(recipe.fromContents());
            }
        }
    }
}
