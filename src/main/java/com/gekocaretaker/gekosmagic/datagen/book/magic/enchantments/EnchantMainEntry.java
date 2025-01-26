package com.gekocaretaker.gekosmagic.datagen.book.magic.enchantments;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Items;

public class EnchantMainEntry extends EntryProvider {
    public static final String ID = "enchant_main";

    public EnchantMainEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Enchantments");
        this.pageText(
                """
                        Where would we be without enchantments? Probably stuck mining deepslate for the rest of time. The enchantments seen in this tome can help you on your travels, or when fighting others if you like.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Enchantments";
    }

    @Override
    protected String entryDescription() {
        return "Upgrade your gear";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.CATEGORY_START;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.LAPIS_LAZULI);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
