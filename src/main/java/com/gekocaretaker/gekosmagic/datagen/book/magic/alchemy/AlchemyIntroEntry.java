package com.gekocaretaker.gekosmagic.datagen.book.magic.alchemy;

import com.gekocaretaker.gekosmagic.block.ModBlocks;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;

public class AlchemyIntroEntry extends EntryProvider {
    public static final String ID = "alchemy_intro";

    public AlchemyIntroEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Alchemy");
        this.pageText(
                """
                        Alchemy is similar to brewing in some ways, but is very different overall.
                        \\
                        \\
                        Blaze powder is needed to fuel alchemy due to burning essence being far too strong, sending glass shards flying everywhere. Placing ingredients into the essence container will turn it into essence.
                        """
        );

        this.page("containment", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Containers");
        this.pageText(
                """
                        Essence takes up much less space than the item, allowing for easier storage. To brew elixirs, you need to select an essence, and send a redstone signal in. I have found that without it, elixirs will try to brew themselves if possible.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Alchemy";
    }

    @Override
    protected String entryDescription() {
        return "The refined art of making stuff and hoping to not be blown up";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.CATEGORY_START;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(ModBlocks.ALCHEMY_STAND_BLOCK);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
