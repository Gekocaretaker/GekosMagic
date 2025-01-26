package com.gekocaretaker.gekosmagic.datagen;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.block.ModBlocks;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.elixir.Elixirs;
import com.gekocaretaker.gekosmagic.elixir.type.ThickElixirs;
import com.gekocaretaker.gekosmagic.entity.ModEntities;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.registry.RegistryObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EnglishLanguageProvider extends AbstractModonomiconLanguageProvider {
    public EnglishLanguageProvider(DataOutput output, ModonomiconLanguageProvider cachedProvider) {
        super(output, Gekosmagic.MODID, "en_us", cachedProvider);
    }

    private void addMisc() {
        this.add("container.alchemy_stand", "Alchemy Stand");
        this.add("effect.gekosmagic.uninteresting", "???");
        this.add("emi.category.gekosmagic.alchemy", "Alchemy");

        addEnchant("fast_reel", "Fast Reel", "Increases the velocity of the fishing bobber.");

        this.add(ModEntities.GECKO, "Gecko");
        this.add(ModEntities.ELIXIR, "Elixir");

        addEssence("air", "Broken Game");
        addEssence("burning", "Burning");
        addEssence("cold", "Cold");
        addEssence("evil", "Evil");
        addEssence("fear", "Fear");
        addEssence("fermented", "Fermentation");
        addEssence("flow", "Flowing");
        addEssence("fungal", "Fungus");
        addEssence("glowing", "Glowing");
        addEssence("gritty", "Grit");
        addEssence("growth", "Growth");
        addEssence("lingering", "Lingering");
        addEssence("lucky", "Luck");
        addEssence("magma", "Magma");
        addEssence("midnight", "Midnight");
        addEssence("nether", "Nether");
        addEssence("old", "Old-Time");
        addEssence("peace", "Peace");
        addEssence("powered", "Powering");
        addEssence("rage", "Rage");
        addEssence("resonant", "Resonance");
        addEssence("rich", "Richness");
        addEssence("sad", "Sadness");
        addEssence("sharp", "Sharpness");
        addEssence("shiny", "Shine");
        addEssence("silent", "Silence");
        addEssence("slimy", "Slimy");
        addEssence("slurry", "Slurry");
        addEssence("soul", "Souls");
        addEssence("spreading", "Spreading");
        addEssence("sticky", "Sticking");
        addEssence("sturdy", "Sturdy");
        addEssence("sweet", "Sweetness");
        addEssence("time", "Time");
        addEssence("tokay", "Tokay");
        addEssence("warp", "Warping");
        addEssence("water", "Water");
        addEssence("young", "Young-Time");

        addElixirs(ModItems.ELIXIR, "Elixir");
        addElixirs(ModItems.SPLASH_ELIXIR, "Splash Elixir");
        addElixirs(ModItems.LINGERING_ELIXIR, "Lingering Elixir");
        addElixirs(ModItems.BUTTERED_ELIXIR, "Buttered Elixir");
        addElixirs(ModItems.BLAND_ELIXIR, "Bland Elixir");
        addElixirs(ModItems.DIFFUSING_ELIXIR, "Diffusing Elixir");
        add("item.gekosmagic.elixir.uninteresting", "Basic Elixir of Water");
    }

    private void addItems() {
        this.add(ModItems.BLACK_GECKO_SCALE, "Black Gecko Scale");
        this.add(ModItems.CAT_GECKO_SCALE, "Cat Gecko Scale");
        this.add(ModItems.ORCHID_GECKO_SCALE, "Orchid Gecko Scale");
        this.add(ModItems.SAND_GECKO_SCALE, "Sand Gecko Scale");
        this.add(ModItems.TOKAY_GECKO_SCALE, "Tokay Gecko Scale");
        this.add(ModItems.GECKO_SPAWN_EGG, "Gecko Spawn Egg");
        this.add(ModItems.GLASS_PHIAL, "Glass Phial");

        addEffect("absolutely_not", "Absolutely Not", "Scares creepers away and phantoms ignore you.");
        addEffect("lizard_brain", "Lizard Brain", "Forces effected entities to crawl.");
        addEffect("vulnerability", "Vulnerability", "Damage dealt to effected entities gains an additional 15% per level.");
        addEffect("adept_rabbit", "the Adept Rabbit", "");

        addItemGroup("elixirs", "Elixirs");
        addItemGroup("elixirs.bland", "Bland");
        addItemGroup("elixirs.buttered", "Buttered");
        addItemGroup("elixirs.clear", "Clear");
        addItemGroup("elixirs.diffusing", "Diffusing");
        addItemGroup("elixirs.generic", "Generic");
        addItemGroup("elixirs.lingering", "Lingering");
        addItemGroup("elixirs.splash", "Splash");
        addItemGroup("elixirs.uninteresting", "Uninteresting");

        addSound("entity.gecko.chirps", "Gecko chirps");
        addSound("entity.gecko.death", "Gecko dies");
        addSound("entity.gecko.eat", "Gecko eats");
        addSound("entity.gecko.hurt", "Gecko hurts");
        addSound("entity.gecko.scales_drop", "Gecko sheds scales");

        this.add("tag.item.gekosmagic.foods.glow_berries", "Glow Berries");
        this.add("tag.item.gekosmagic.gecko_scales", "Gecko Scales");

        addTrimMaterial("gecko_black", "Gecko Black");
        addTrimMaterial("gecko_red", "Gecko Red");
        addTrimMaterial("orchid_pink", "Orchid Pink");
        addTrimMaterial("tokay_blue", "Tokay Blue");
    }

    private void addBlocks() {
        this.add(ModBlocks.ALCHEMY_STAND_BLOCK, "Alchemy Stand");
    }

    private void addBookStuff() {}

    @Override
    protected void addTranslations() {
        this.addMisc();
        this.addBookStuff();
        this.addItems();
        this.addBlocks();
    }

    private void addEffect(String effect, String effectName, String effectDesc) {
        this.add("item.minecraft.lingering_potion.effect." + effect, "Lingering Potion of " + effectName);
        this.add("item.minecraft.potion.effect." + effect, "Potion of " + effectName);
        this.add("item.minecraft.splash_potion.effect." + effect, "Splash Potion of " + effectName);
        this.add("effect.gekosmagic." + effect, effectName);
        this.add("effect.gekosmagic." + effect + ".description", effectDesc);
    }

    private void addEnchant(String enchant, String enchantName, String enchantDesc) {
        this.add("enchantment.gekosmagic." + enchant, enchantName);
        this.add("enchantment.gekosmagic." + enchant + ".description", enchantDesc);
    }

    private void addEssence(String id, String name) {
        this.add("essence.gekosmagic." + id, "Essence of " + name);
        this.add("tag.item.gekosmagic.essence." + id, name + " Essence");
    }

    private void addItemGroup(String id, String name) {
        this.add("itemGroup.gekosmagic." + id, name);
    }

    private void addElixirs(Item item, String itemName) {
        List<String> usedTranslationKeys = new ArrayList<>();
        ModRegistries.ELIXIR.forEach(elixir -> {
            String key = item.getTranslationKey() + "." + elixir.getType() + ".effect." + elixir.getBase();
            if (!usedTranslationKeys.contains(key)) {
                String value = "";
                if (elixir.equals(Elixirs.WATER)) {
                    String val = itemName.replace("Elixir", "Phial");
                    String type = "";
                    if (!elixir.isOfType("basic")) {
                        type = turnIdToName(elixir.getType());
                    }
                    value = val + " of " + type + " Water";
                } else if (elixir.equals(ThickElixirs.TURTLE_MASTER) ||
                        elixir.equals(ThickElixirs.ADEPT_RABBIT) ||
                        elixir.equals(ThickElixirs.LONG_TURTLE_MASTER) ||
                        elixir.equals(ThickElixirs.LONG_ADEPT_RABBIT) ||
                        elixir.equals(ThickElixirs.STRONG_TURTLE_MASTER) ||
                        elixir.equals(ThickElixirs.STRONG_ADEPT_RABBIT)) {
                    value = turnIdToName(elixir.getType()) + " " + itemName + " of the " + turnIdToName(elixir.getBase());
                } else {
                    value = turnIdToName(elixir.getType()) + " " + itemName + " of " + turnIdToName(elixir.getBase());
                }

                usedTranslationKeys.add(key);
                this.add(key, value);
            }
        });
    }

    private void addSound(String id, String name) {
        this.add("sounds.gekosmagic." + id, name);
    }

    private void addTrimMaterial(String id, String name) {
        this.add("trim_material.gekosmagic." + id, name);
    }

    private static String turnIdToName(String original) {
        String word = original.replace('_', ' ');
        String newName = WordUtils.capitalizeFully(word);
        return newName;
    }

    /*
    private static void addElixirs(Item item, String itemName) {
        wrapper.streamEntries().map((entry) -> {
            return new Pair<ItemStack, Elixir>(ElixirContentsComponent.createStack(item, entry), entry.value());
        }).forEach((pair) -> {
            try {
                String translationKey = pair.getLeft().getTranslationKey();
                String type = turnIdToName(pair.getRight().getType());
                String base = turnIdToName(pair.getRight().getBase());
                translationBuilder.add(translationKey, type + " " + itemName + " of " + base);
            } catch (RuntimeException e) {
                Gekosmagic.LOGGER.error(e.getMessage());
            }
        });
    }*/
}
