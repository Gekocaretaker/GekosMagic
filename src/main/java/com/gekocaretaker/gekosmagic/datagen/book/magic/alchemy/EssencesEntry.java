package com.gekocaretaker.gekosmagic.datagen.book.magic.alchemy;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;

public class EssencesEntry extends EntryProvider {
    public static final String ID = "essences";

    public EssencesEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Essences");
        this.pageText(
                """
                        The purest form of things found throughout the world. Their use is important for alchemy, allowing a more controlled approach to creating elixirs than potions have.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Essences";
    }

    @Override
    protected String entryDescription() {
        return "That which makes the elixirs";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.CATEGORY_START;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Gekosmagic.identify("textures/gui/sprites/container/alchemy_stand/essence_container.png"));
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
