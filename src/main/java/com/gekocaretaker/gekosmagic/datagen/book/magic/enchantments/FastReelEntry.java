package com.gekocaretaker.gekosmagic.datagen.book.magic.enchantments;

import com.gekocaretaker.gekosmagic.item.ModItemGroups;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;

public class FastReelEntry extends EntryProvider {
    public static final String ID = "fast_reel";

    public FastReelEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(Ingredient.ofStacks(
                        ModItemGroups.FAST_REEL_ENCHANTMENT
                ))
                .withText(this.context().pageText()));
        this.pageTitle("Fast Reel");
        this.pageText(
                """
                        This enchantment is placed on your fishing rod, increasing the speed the bobber moves. Great for moving creatures, but makes fishing harder. Pair with grapple for some *reel* fun.
                        """
        );

        this.page("addium", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText(
                """
                        Fishing rods can only handle up to Fast Reel III, any more and the rod combusts. Hair can become singed.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Fast Reel";
    }

    @Override
    protected String entryDescription() {
        return "A way to use physics to your advantage";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.FISHING_ROD);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
