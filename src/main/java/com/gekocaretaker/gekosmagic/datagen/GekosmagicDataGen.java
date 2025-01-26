package com.gekocaretaker.gekosmagic.datagen;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.datagen.book.MagicBook;
import com.klikli_dev.modonomicon.api.datagen.BookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.FabricBookProvider;
import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class GekosmagicDataGen implements DataGeneratorEntrypoint {
    public static FabricDataGenerator fabricDataGen;

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGen = fabricDataGenerator;
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        LanguageProviderCache enUsCache = new LanguageProviderCache("en_us");
        pack.addProvider(FabricBookProvider.of(new BookSubProvider[]{new MagicBook(Gekosmagic.MODID, enUsCache)}));
        pack.addProvider((FabricDataGenerator.Pack.Factory<EnglishLanguageProvider>) (output) -> {
            return new EnglishLanguageProvider(output, enUsCache);
        });

        //AlchemyRecipeDataProvider.init();
        //pack.addProvider(EnglishLanguageProvider::new);
    }
}
