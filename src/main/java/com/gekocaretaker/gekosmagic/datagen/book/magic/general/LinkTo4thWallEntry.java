package com.gekocaretaker.gekosmagic.datagen.book.magic.general;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Items;

public class LinkTo4thWallEntry extends EntryProvider {
    public static final String ID = "link_to_fourth_wall";

    public LinkTo4thWallEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        //
    }

    @Override
    protected String entryName() {
        return "A Theory on The 4th Wall";
    }

    @Override
    protected String entryDescription() {
        return "Beware all thee who seeketh knowledge: LORE BREAKING";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.BARRIER);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
