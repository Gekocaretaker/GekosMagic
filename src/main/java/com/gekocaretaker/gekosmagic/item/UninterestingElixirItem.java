package com.gekocaretaker.gekosmagic.item;

import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

public class UninterestingElixirItem extends ElixirItem {
    public UninterestingElixirItem(Settings settings) {
        super(settings);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return "item.gekosmagic.elixir.uninteresting";
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        ElixirContentsComponent elixirContentsComponent = stack.get(ModDataComponentTypes.ELIXIR_CONTENTS);
        if (elixirContentsComponent != null) {
            Objects.requireNonNull(tooltip);
            elixirContentsComponent.buildMysteryTooltip(tooltip::add, 1.0F, context.getUpdateTickRate());
        }
    }
}
