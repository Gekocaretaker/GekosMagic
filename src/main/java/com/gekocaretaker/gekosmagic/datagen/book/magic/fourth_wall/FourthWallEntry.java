package com.gekocaretaker.gekosmagic.datagen.book.magic.fourth_wall;

import com.gekocaretaker.gekosmagic.item.ModItems;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;

public class FourthWallEntry extends EntryProvider {
    public static final String ID = "fourth_wall_info";

    public FourthWallEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("4th Wall Stuff");
        this.pageText(
                """
                        If you entered here, I am guessing you want to know some stuff that kinda is not lore friendly. You asked for it.
                        """
        );

        this.page("extra", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText(
                """
                        This contains information about coding and stuff that happened while I was modding.
                        \\
                        \\
                        I add things here as I think of them. Think of it as a dev log that started after the first version was made.
                        \\
                        \\
                        \\
                        \\
                        (Press the 'x' to enter the category)
                        """
        );
    }

    @Override
    protected String entryName() {
        return "4th Wall Landing Page";
    }

    @Override
    protected String entryDescription() {
        return "The page that introduced you to the 4th wall.";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.CATEGORY_START;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(ModItems.SAND_GECKO_SCALE);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
