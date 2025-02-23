package com.gekocaretaker.gekosmagic.compat.emi;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.block.ModBlocks;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.elixir.Elixirs;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.recipe.*;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.Comparison;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
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

        Comparison elixirComparison = Comparison.compareComponents();

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
        for (RecipeEntry<ElixirRecipe> recipe : emiRegistry.getRecipeManager().listAllOfType(ModRecipeTypes.ELIXIR)) {
            emiRegistry.addRecipe(new AlchemyEmiRecipe(
                    recipe.id(),
                    EmiIngredient.of(Ingredient.ofStacks(
                            ElixirContentsComponent.createStack(ModItems.ELIXIR, recipe.value().from())
                    )),
                    recipe.value().ingredient().value(),
                    EmiStack.of(ElixirContentsComponent.createStack(ModItems.ELIXIR, recipe.value().to()))
            ));
            for (RecipeEntry<ItemAlchemyRecipe> itemRecipe : emiRegistry.getRecipeManager().listAllOfType(ModRecipeTypes.ITEM_ALCHEMY)) {
                if (!alreadyCreatedItemConversion.contains(recipe.value().from().value())) {
                    emiRegistry.addRecipe(new AlchemyEmiRecipe(
                            recipe.id().withSuffixedPath("_to_" + itemRecipe.id().getPath()),
                            EmiIngredient.of(Ingredient.ofStacks(
                                    ElixirContentsComponent.createStack(itemRecipe.value().from().value(), recipe.value().from())
                            )),
                            itemRecipe.value().ingredient().value(),
                            EmiStack.of(ElixirContentsComponent.createStack(itemRecipe.value().to().value(), recipe.value().from()))
                    ));
                }
                emiRegistry.addRecipe(new AlchemyEmiRecipe(
                        recipe.id().withSuffixedPath("_as_" + itemRecipe.id().getPath()),
                        EmiIngredient.of(Ingredient.ofStacks(
                                ElixirContentsComponent.createStack(itemRecipe.value().to().value(), recipe.value().from())
                        )),
                        recipe.value().ingredient().value(),
                        EmiStack.of(ElixirContentsComponent.createStack(itemRecipe.value().to().value(), recipe.value().to()))
                ));
            }
            if (!alreadyCreatedItemConversion.contains(recipe.value().from().value())) {
                alreadyCreatedItemConversion.add(recipe.value().from().value());
            }
        }

        alreadyCreatedItemConversion.clear();
        for (RecipeEntry<BasicAlchemyRecipe> recipe: emiRegistry.getRecipeManager().listAllOfType(ModRecipeTypes.BASIC_ALCHEMY)) {
            emiRegistry.addRecipe(new AlchemyEmiRecipe(
                    recipe.id(),
                    EmiIngredient.of(Ingredient.ofStacks(
                            ElixirContentsComponent.createStack(ModItems.ELIXIR, recipe.value().from())
                    )),
                    recipe.value().ingredient().value(),
                    EmiStack.of(ElixirContentsComponent.createStack(ModItems.ELIXIR, recipe.value().to(), AlchemyRecipeRegistry.makeTranslationKey(new ItemStack(ModItems.ELIXIR), recipe.value().translation())))
            ));
            for (RecipeEntry<ItemAlchemyRecipe> itemRecipe : emiRegistry.getRecipeManager().listAllOfType(ModRecipeTypes.ITEM_ALCHEMY)) {
                if (!alreadyCreatedItemConversion.contains(recipe.value().from().value())) {
                    emiRegistry.addRecipe(new AlchemyEmiRecipe(
                            recipe.id().withSuffixedPath("_to_" + itemRecipe.id().getPath()),
                            EmiIngredient.of(Ingredient.ofStacks(
                                    ElixirContentsComponent.createStack(itemRecipe.value().from().value(), recipe.value().from())
                            )),
                            itemRecipe.value().ingredient().value(),
                            EmiStack.of(ElixirContentsComponent.createStack(itemRecipe.value().to().value(), recipe.value().from(), AlchemyRecipeRegistry.makeTranslationKey(new ItemStack(itemRecipe.value().to().value()), recipe.value().translation())))
                    ));
                }
                emiRegistry.addRecipe(new AlchemyEmiRecipe(
                        recipe.id().withSuffixedPath("_as_" + itemRecipe.id().getPath()),
                        EmiIngredient.of(Ingredient.ofStacks(
                                ElixirContentsComponent.createStack(itemRecipe.value().to().value(), recipe.value().from())
                        )),
                        recipe.value().ingredient().value(),
                        EmiStack.of(ElixirContentsComponent.createStack(itemRecipe.value().to().value(), recipe.value().to(), AlchemyRecipeRegistry.makeTranslationKey(new ItemStack(itemRecipe.value().to().value()), recipe.value().translation())))
                ));
            }
            if (!alreadyCreatedItemConversion.contains(recipe.value().from().value())) {
                alreadyCreatedItemConversion.add(recipe.value().from().value());
            }
        }

        alreadyCreatedItemConversion.clear();
        List<ElixirContentsComponent> alreadyCreatedItemConversion2 = new ArrayList<>();
        for (RecipeEntry<AdvancedAlchemyRecipe> recipe : emiRegistry.getRecipeManager().listAllOfType(ModRecipeTypes.ADVANCED_ALCHEMY)) {
            emiRegistry.addRecipe(new AlchemyEmiRecipe(
                    recipe.id(),
                    EmiIngredient.of(Ingredient.ofStacks(
                            ElixirContentsComponent.createStack(ModItems.ELIXIR, recipe.value().from(), AlchemyRecipeRegistry.makeTranslationKey(new ItemStack(ModItems.ELIXIR), recipe.value().requiredTranslation()))
                    )),
                    recipe.value().ingredient().value(),
                    EmiStack.of(ElixirContentsComponent.createStack(ModItems.ELIXIR, recipe.value().to(), AlchemyRecipeRegistry.makeTranslationKey(new ItemStack(ModItems.ELIXIR), recipe.value().translation())))
            ));
            for (RecipeEntry<ItemAlchemyRecipe> itemRecipe : emiRegistry.getRecipeManager().listAllOfType(ModRecipeTypes.ITEM_ALCHEMY)) {
                if (!alreadyCreatedItemConversion2.contains(recipe.value().from())) {
                    emiRegistry.addRecipe(new AlchemyEmiRecipe(
                            recipe.id().withSuffixedPath("_to_" + itemRecipe.id().getPath()),
                            EmiIngredient.of(Ingredient.ofStacks(
                                    ElixirContentsComponent.createStack(itemRecipe.value().from().value(), recipe.value().from(), AlchemyRecipeRegistry.makeTranslationKey(new ItemStack(itemRecipe.value().from().value()), recipe.value().requiredTranslation()))
                            )),
                            itemRecipe.value().ingredient().value(),
                            EmiStack.of(ElixirContentsComponent.createStack(itemRecipe.value().to().value(), recipe.value().to(), AlchemyRecipeRegistry.makeTranslationKey(new ItemStack(itemRecipe.value().to().value()), recipe.value().translation())))
                    ));
                }
                emiRegistry.addRecipe(new AlchemyEmiRecipe(
                        recipe.id().withSuffixedPath("_as_" + itemRecipe.id().getPath()),
                        EmiIngredient.of(Ingredient.ofStacks(
                                ElixirContentsComponent.createStack(itemRecipe.value().to().value(), recipe.value().from(), AlchemyRecipeRegistry.makeTranslationKey(new ItemStack(itemRecipe.value().to().value()), recipe.value().requiredTranslation()))
                        )),
                        recipe.value().ingredient().value(),
                        EmiStack.of(ElixirContentsComponent.createStack(itemRecipe.value().to().value(), recipe.value().to(), AlchemyRecipeRegistry.makeTranslationKey(new ItemStack(itemRecipe.value().to().value()), recipe.value().translation())))
                ));
            }
            if (!alreadyCreatedItemConversion2.contains(recipe.value().from())) {
                alreadyCreatedItemConversion2.add(recipe.value().from());
            }
        }

        emiRegistry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(Gekosmagic.identify("/world/unique/gekosmagic/phial_of_water"))
                .leftInput(EmiStack.of(ModItems.GLASS_PHIAL))
                .rightInput(EmiStack.of(Fluids.WATER), true)
                .output(EmiStack.of(ElixirContentsComponent.createStack(ModItems.ELIXIR, Elixirs.WATER)))
                .build()
        );
    }
}
