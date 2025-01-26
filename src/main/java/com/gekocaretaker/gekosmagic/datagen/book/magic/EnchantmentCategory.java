package com.gekocaretaker.gekosmagic.datagen.book.magic;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.datagen.book.magic.enchantments.EnchantMainEntry;
import com.gekocaretaker.gekosmagic.datagen.book.magic.enchantments.FastReelEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class EnchantmentCategory extends CategoryProvider {
    public static final String ID = "enchantments";

    public EnchantmentCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "_____________________",
                "_____________________",
                "__________m__________",
                "___________f_________",
                "_____________________"
        };
    }

    @Override
    protected void generateEntries() {
        BookEntryModel enchantMainEntry = this.add(new EnchantMainEntry(this).generate('m'));
        BookEntryModel fastReelEntry = this.add(new FastReelEntry(this).generate('f').withParent(enchantMainEntry));
    }

    @Override
    protected String categoryName() {
        return "Enchantments";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Items.ENCHANTED_BOOK);
    }

    @Override
    public String categoryId() {
        return ID;
    }

    public static Identifier makeEntryId(String id) {
        return Gekosmagic.identify(ID + "/" + id);
    }
}
