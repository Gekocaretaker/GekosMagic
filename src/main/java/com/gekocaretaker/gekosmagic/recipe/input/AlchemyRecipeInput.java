package com.gekocaretaker.gekosmagic.recipe.input;

import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.EssenceContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.input.RecipeInput;

import java.util.Objects;

public class AlchemyRecipeInput implements RecipeInput {
    private final ItemStack stack;
    private final EssenceContainer essenceContainer;

    public AlchemyRecipeInput(EssenceContainer essenceContainer, ItemStack stack) {
        this.stack = stack;
        this.essenceContainer = essenceContainer;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot != 0) {
            throw new IllegalArgumentException("No item for index " + slot);
        } else {
            return this.stack;
        }
    }

    public ItemStack getItemStack() {
        return this.getStackInSlot(0);
    }

    public EssenceContainer getEssenceContainer() {
        return this.essenceContainer;
    }

    public boolean stackHasElixirContents() {
        return this.stack.contains(ModDataComponentTypes.ELIXIR_CONTENTS);
    }

    public ElixirContentsComponent getContents() {
        return this.stack.getOrDefault(
                ModDataComponentTypes.ELIXIR_CONTENTS,
                ElixirContentsComponent.DEFAULT
        );
    }

    public boolean matchesContents(ElixirContentsComponent contents) {
        if (this.stackHasElixirContents()) {
            return Objects.equals(this.stack.get(ModDataComponentTypes.ELIXIR_CONTENTS), contents);
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return 1;
    }
}
