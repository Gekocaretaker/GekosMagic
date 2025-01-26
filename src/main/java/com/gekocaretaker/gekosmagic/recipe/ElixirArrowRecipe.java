package com.gekocaretaker.gekosmagic.recipe;

import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.item.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ElixirArrowRecipe extends SpecialCraftingRecipe {
    public ElixirArrowRecipe(CraftingRecipeCategory craftingRecipeCategory) {
        super(craftingRecipeCategory);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        if (input.getWidth() == 3 && input.getHeight() == 3) {
            for (int i = 0; i < input.getHeight(); i++) {
                for (int j = 0; j < input.getWidth(); j++) {
                    ItemStack itemStack = input.getStackInSlot(j, i);
                    if (itemStack.isEmpty()) {
                        return false;
                    }

                    if (j == 1 && i == 1) {
                        if (!itemStack.isOf(ModItems.LINGERING_ELIXIR)) {
                            return false;
                        }
                    } else if (!itemStack.isOf(Items.ARROW)) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack itemStack = input.getStackInSlot(1, 1);
        if (!itemStack.isOf(ModItems.LINGERING_ELIXIR)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemStack1 = new ItemStack(Items.TIPPED_ARROW, 8);
            List<StatusEffectInstance> effects = new ArrayList<>();
            itemStack.get(ModDataComponentTypes.ELIXIR_CONTENTS).forEachEffect(effects::add);
            PotionContentsComponent potionContentsComponent = new PotionContentsComponent(Optional.empty(), Optional.empty(), effects);
            itemStack1.set(DataComponentTypes.POTION_CONTENTS, potionContentsComponent);
            return itemStack1;
        }
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.ELIXIR_ARROW;
    }
}
