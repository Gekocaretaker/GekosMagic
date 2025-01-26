package com.gekocaretaker.gekosmagic.datagen.book.magic.fourth_wall;

import com.gekocaretaker.gekosmagic.item.ModItems;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;

public class EntitySecretsEntry extends EntryProvider {
    public static final String ID = "fourth_wall_entity_secrets";

    public EntitySecretsEntry(CategoryProviderBase parent) {
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
                        This is a section that gives some info on the entities of the mod.
                        """
        );

        this.page("gecko", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Gecko");
        this.pageText(
                """
                        While there are 5 variants of gecko's found in the overworld, there is a secret 6th variant that is only found with commands! I think it has something to do with the number **15**? Idk, but it is *time* to go.
                        """
        );
    }

    @Override
    protected String entryName() {
        return "Entity Secrets";
    }

    @Override
    protected String entryDescription() {
        return "Info on entities.";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(ModItems.GECKO_SPAWN_EGG);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
