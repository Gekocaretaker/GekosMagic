package com.gekocaretaker.gekosmagic.datagen.book;

import com.gekocaretaker.gekosmagic.datagen.book.magic.*;
import com.gekocaretaker.gekosmagic.datagen.book.magic.fourth_wall.FourthWallEntry;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import net.minecraft.util.Identifier;

public class MagicBook extends SingleBookSubProvider {
    public static final String ID = "scaled_tome";

    public MagicBook(String modid, ModonomiconLanguageProvider lang) {
        super(ID, modid, lang);
    }

    @Override
    protected BookModel additionalSetup(BookModel book) {
        return super.additionalSetup(book)
                .withModel(Identifier.of("modonomicon", "modonomicon_blue"))
                .withGenerateBookItem(true);
    }

    @Override
    protected void registerDefaultMacros() {
        //
    }

    @Override
    protected void generateCategories() {
        this.add(new GeneralCategory(this).generate());

        this.add(new FourthWallCategory(this).generate()
                .withShowCategoryButton(false)
                .withEntryToOpen(
                        FourthWallCategory.makeEntryId(FourthWallEntry.ID),
                        true
                ));

        this.add(new EnchantmentCategory(this).generate());
        this.add(new AlchemyCategory(this).generate());
        this.add(new EffectsCategory(this).generate());
    }

    @Override
    protected String bookName() {
        return "Scaled Tome";
    }

    @Override
    protected String bookTooltip() {
        return "A guide of the magical world around you.";
    }
}
