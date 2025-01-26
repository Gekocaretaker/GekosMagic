package com.gekocaretaker.gekosmagic.datagen.book.magic.fourth_wall;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;

public class EssenceSecretsEntry extends EntryProvider {
    public static final String ID = "fourth_wall_essence_secrets";

    public EssenceSecretsEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("info", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Information");
        this.pageText(
                """
                        This is a section that gives some info on essences added.
                        """
        );

        this.page("fear", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Fear");
        this.pageText(
                """
                        Early on, fear essence was going to be used to make uninteresting elixirs, before it became a separate item. When adding all the essences, I completely forgot to add fear essence. I had glossed over it on the list. Ironic isn't it?
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Essence Stuff";
    }

    @Override
    protected String entryDescription() {
        return "Info on essences.";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Gekosmagic.identify("textures/essence/nether.png"));
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
