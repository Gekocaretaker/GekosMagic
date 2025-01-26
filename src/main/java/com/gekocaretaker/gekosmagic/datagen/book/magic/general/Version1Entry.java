package com.gekocaretaker.gekosmagic.datagen.book.magic.general;

import com.gekocaretaker.gekosmagic.item.ModItems;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;

public class Version1Entry extends EntryProvider {
    public static final String ID = "version_1_0_0";

    public Version1Entry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Release");
        this.pageText(
                """
                        The first version of the tome! Here is what is introduced in this version:
                        \\
                        \\
                        [Alchemy](category://alchemy) is an art similar to brewing, but with multiple interesting changes.
                        \\
                        \\
                        [Fast Reel](entry://enchantments/fast_reel) is applied to the fishing rod, and is good for travel, transport, and pvp.
                        """
        );

        this.page("addium", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText(
                """
                        [Lizard Brain](entry://effects/lizard_brain) and [Absolutely Not](entry://effects/absolutely_not) are able to be brewed in both the brewing stand and the alchemy stand.
                        \\
                        \\
                        [Vulnerability](entry://effects/vulnerability) cannot be brewed but can be looted.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Release";
    }

    @Override
    protected String entryDescription() {
        return "Version 1.0.0";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(ModItems.CAT_GECKO_SCALE);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
