package com.gekocaretaker.gekosmagic.datagen.book.magic;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.datagen.book.magic.fourth_wall.EntitySecretsEntry;
import com.gekocaretaker.gekosmagic.datagen.book.magic.fourth_wall.FourthWallEntry;
import com.gekocaretaker.gekosmagic.datagen.book.magic.general.IntroEntry;
import com.gekocaretaker.gekosmagic.datagen.book.magic.general.LinkTo4thWallEntry;
import com.gekocaretaker.gekosmagic.datagen.book.magic.general.Version1Entry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.util.Identifier;

public class GeneralCategory extends CategoryProvider {
    public static final String ID = "general";

    public GeneralCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "_____________________",
                "_____________________",
                "__________a__________",
                "________b____________",
                "_____________________"
        };
    }

    @Override
    protected void generateEntries() {
        BookEntryModel introEntry = this.add(new IntroEntry(this).generate('a'));
        BookEntryModel version1Entry = this.add(new Version1Entry(this)
                .generate('b').withParent(introEntry));
    }

    @Override
    protected String categoryName() {
        return "General";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Gekosmagic.identify("textures/essence/flow.png"));
    }

    @Override
    public String categoryId() {
        return ID;
    }

    public static Identifier makeEntryId(String id) {
        return Gekosmagic.identify(ID + "/" + id);
    }
}
