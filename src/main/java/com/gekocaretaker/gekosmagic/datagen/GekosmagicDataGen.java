package com.gekocaretaker.gekosmagic.datagen;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.datagen.book.MagicBook;
import com.gekocaretaker.gekosmagic.datagen.book.magic.enchantments.FastReelEntry;
import com.gekocaretaker.gekosmagic.enchantment.ModEnchantments;
import com.klikli_dev.modonomicon.api.datagen.BookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.FabricBookProvider;
import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;

import java.util.concurrent.ExecutionException;

public class GekosmagicDataGen implements DataGeneratorEntrypoint {
    public static FabricDataGenerator fabricDataGen;

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGen = fabricDataGenerator;
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        LanguageProviderCache enUsCache = new LanguageProviderCache("en_us");

        FastReelEntry.DISPLAY_ITEM = new ItemStack(Items.ENCHANTED_BOOK);
        try {
            FastReelEntry.DISPLAY_ITEM.addEnchantment(fabricDataGenerator.getRegistries().get().getOptional(RegistryKeys.ENCHANTMENT).get().getOptional(ModEnchantments.FAST_REEL).get(), 1);
        } catch (InterruptedException | ExecutionException e) {
            Gekosmagic.LOGGER.error(e.getLocalizedMessage());
        }

        pack.addProvider(FabricBookProvider.of(new BookSubProvider[]{new MagicBook(Gekosmagic.MODID, enUsCache)}));
        pack.addProvider((FabricDataGenerator.Pack.Factory<EnglishLanguageProvider>) (output) -> {
            return new EnglishLanguageProvider(output, enUsCache);
        });

        //AlchemyRecipeDataProvider.init();
        //pack.addProvider(EnglishLanguageProvider::new);
    }
}
