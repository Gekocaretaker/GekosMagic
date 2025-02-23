package com.gekocaretaker.gekosmagic.datagen.book.magic.general;

import com.gekocaretaker.gekosmagic.item.ModItems;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;

public class Version1_1Entry extends EntryProvider {
    public static final String ID = "version_1_1_0";

    public Version1_1Entry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("The Data Update");
        this.pageText(
                """
                        Gecko's can now spawn and some QOL changes to the alchemy stand. Not much else for the average player, but stay on the lookout for version 1.2.0!
                        \\
                        \\
                        The alchemy recipes are now actually stored in the recipes folder instead of the alchemy folder. As part of this, the recipe type that *was* 'gekosmagic:custom' is now 'gekosmagic:basic'.
                        \\
                        \\
                        The alchemy stand now has a little container that shows alchemy progress. Elixirs now show color on the alchemy stand!
                        \\
                        \\
                        As part of the recipe changes, the custom elixirs (basic & advanced recipe types) can have custom names! No more will your custom elixirs just be named Basic Elixir of Water.
                        """
        );

        this.page("page2", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText(
                """
                        Gecko variants are data driven. These are stored in the gecko_variant folder. The wiki has the full information on how to add your own.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "The Data Update";
    }

    @Override
    protected String entryDescription() {
        return "Version 1.1.0";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(ModItems.ORCHID_GECKO_SCALE);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
