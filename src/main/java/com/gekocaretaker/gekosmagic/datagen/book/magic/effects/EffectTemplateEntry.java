package com.gekocaretaker.gekosmagic.datagen.book.magic.effects;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;

public class EffectTemplateEntry extends EntryProvider {
    private final String identifier;
    private final String entryName;
    private final String pageTitleString;
    private final String pageTextString;

    public EffectTemplateEntry(CategoryProviderBase parent,
                               String identifier, String entryName,
                               String pageTitleString, String pageTextString) {
        super(parent);
        this.identifier = identifier;
        this.entryName = entryName;
        this.pageTitleString = pageTitleString;
        this.pageTextString = pageTextString;
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle(this.pageTitleString);
        this.pageText(this.pageTextString);
    }

    @Override
    protected String entryName() {
        return this.entryName;
    }

    @Override
    protected String entryDescription() {
        return "";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Gekosmagic.identify("textures/mob_effect/" + this.identifier + ".png"));
    }

    @Override
    protected String entryId() {
        return this.identifier;
    }
}
