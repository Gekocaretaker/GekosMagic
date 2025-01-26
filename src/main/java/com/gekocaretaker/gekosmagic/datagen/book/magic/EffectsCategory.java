package com.gekocaretaker.gekosmagic.datagen.book.magic;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.datagen.book.magic.effects.EffectTemplateEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;

public class EffectsCategory extends CategoryProvider {
    public static final String ID = "effects";

    public EffectsCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "_____________________",
                "_____________________",
                "________a_v_l________",
                "_____________________",
                "_____________________"
        };
    }

    @Override
    protected void generateEntries() {
        this.add(new EffectTemplateEntry(this, "absolutely_not",
                "Absolutely Not", "Absolutely Not",
                "This effect scares creepers away and causes phantoms to ignore you.").generate('a'));
        this.add(new EffectTemplateEntry(this, "vulnerability",
                "Vunlerability", "Vulnerability",
                "Causes defense to weaken. This is the opposite of Resistance.").generate('v'));
        this.add(new EffectTemplateEntry(this, "lizard_brain",
                "Lizard Brain", "Lizard Brain",
                "Forces those who are affect to crawl. Useful for vein mining and preventing enemies from running.").generate('l'));
    }

    @Override
    protected String categoryName() {
        return "Effects";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Gekosmagic.identify("textures/gui/effect_category.png"));
    }

    @Override
    public String categoryId() {
        return ID;
    }
}
