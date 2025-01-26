package com.gekocaretaker.gekosmagic.datagen.book.magic;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.datagen.book.magic.fourth_wall.EntitySecretsEntry;
import com.gekocaretaker.gekosmagic.datagen.book.magic.fourth_wall.EssenceSecretsEntry;
import com.gekocaretaker.gekosmagic.datagen.book.magic.fourth_wall.FourthWallEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class FourthWallCategory extends CategoryProvider {
    public static final String ID = "fourth_wall";

    public FourthWallCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "_____________________",
                "_____________________",
                "_______s__a__e_______",
                "_____________________",
                "_____________________"
        };
    }

    @Override
    protected void generateEntries() {
        BookEntryModel fourthWallEntry = this.add(new FourthWallEntry(this).generate('a'));
        BookEntryModel entitySecretsEntry = this.add(new EntitySecretsEntry(this).generate('e')).withParent(fourthWallEntry);
        BookEntryModel essenceSecretsEntry = this.add(new EssenceSecretsEntry(this).generate('s')).withParent(fourthWallEntry);
    }

    @Override
    protected String categoryName() {
        return "The Fourth Wall";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Items.BARRIER);
    }

    @Override
    public String categoryId() {
        return ID;
    }

    public static Identifier makeEntryId(String id) {
        return Gekosmagic.identify(ID + "/" + id);
    }
}
