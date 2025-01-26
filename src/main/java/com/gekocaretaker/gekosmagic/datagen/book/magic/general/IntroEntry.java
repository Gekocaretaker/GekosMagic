package com.gekocaretaker.gekosmagic.datagen.book.magic.general;

import com.gekocaretaker.gekosmagic.item.ModItems;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;

public class IntroEntry extends EntryProvider {
    public static final String ID = "intro";

    public IntroEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("The Beginning");
        this.pageText(
                """
                        Greetings! I am Geko, the writer of this tome you are holding. You may have borrowed it at your local library, bound a book with scales of your pet geckos, or was gifted it and are giving it a read before it ends on a shelf to never be touched again.
                        """
        );

        this.page("intro_cont", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText(
                """
                        However you got a hold of it does not matter, what matter is *what* you do now.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "The Beginning";
    }

    @Override
    protected String entryDescription() {
        return "An introduction to this guide";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.CATEGORY_START;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(ModItems.TOKAY_GECKO_SCALE);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
