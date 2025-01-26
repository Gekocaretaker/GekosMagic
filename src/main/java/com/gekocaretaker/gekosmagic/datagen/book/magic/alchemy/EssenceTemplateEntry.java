package com.gekocaretaker.gekosmagic.datagen.book.magic.alchemy;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;

public class EssenceTemplateEntry extends EntryProvider {
    private final String identifier;
    private final String entryName;
    private final String entryDescription;
    private final Ingredient item;
    private final String pageTitleString;
    private final String pageTextString;

    public EssenceTemplateEntry(CategoryProviderBase parent,
                                String identifier, String entryName,
                                String entryDescription, Item item,
                                String pageTitleString, String pageTextString) {
        super(parent);
        this.identifier = identifier;
        this.entryName = entryName;
        this.entryDescription = entryDescription;
        this.item = Ingredient.ofItems(item);
        this.pageTitleString = pageTitleString;
        this.pageTextString = pageTextString;
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .withItem(this.item));
        this.pageTitle(this.pageTitleString);
        this.pageText(this.pageTextString);
    }

    @Override
    protected String entryName() {
        return entryName;
    }

    @Override
    protected String entryDescription() {
        return entryDescription;
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Gekosmagic.identify("textures/essence/" + identifier + "_full.png"));
    }

    @Override
    protected String entryId() {
        return identifier + "_essence";
    }
}
