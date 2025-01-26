package com.gekocaretaker.gekosmagic.elixir;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Pair;

public class Essences {
    public static final Essence AIR = register("air", new Essence(Items.AIR, 1));
    public static final Essence BURNING_ESSENCE = register("burning", new Essence(Items.BLAZE_POWDER, 100, 16022546));
    public static final Essence NETHER_ESSENCE = register("nether", new Essence(Items.NETHER_WART, 100, 15204352));
    public static final Essence MAGMA_ESSENCE = register("magma", new Essence(Items.MAGMA_CREAM, 100, 15250432));
    public static final Essence SWEET_ESSENCE = register("sweet", new Essence(Items.SUGAR, 100, 13750737));
    public static final Essence SAD_ESSENCE = register("sad", new Essence(Items.GHAST_TEAR, 100, 11189687));
    public static final Essence SLIMY_ESSENCE = register("slimy", new Essence(Items.SLIME_BLOCK, 100, 1296128));
    public static final Essence LUCKY_ESSENCE = register("lucky", new Essence(Items.RABBIT_FOOT, 100, 13746328));
    public static final Essence SHINY_ESSENCE = register("shiny", new Essence(Items.GLISTERING_MELON_SLICE, 100, 16711812));
    public static final Essence SHARP_ESSENCE = register("sharp", new Essence(Items.PUFFERFISH, 100, 13026093));
    public static final Essence RICH_ESSENCE = register("rich", new Essence(Items.GOLDEN_CARROT, 100, 13152784));
    public static final Essence STURDY_ESSENCE = register("sturdy", new Essence(Items.TURTLE_SCUTE, 50, 2468682));
    public static final Essence EVIL_ESSENCE = register("evil", new Essence(Items.PHANTOM_MEMBRANE, 100, 11056515));
    public static final Essence FLOW_ESSENCE = register("flow", new Essence(Items.WIND_CHARGE, 50, 7458253));
    public static final Essence FEAR_ESSENCE = register("fear", new Essence(Items.SPIDER_EYE, 100, 7736839));
    public static final Essence STICKY_ESSENCE = register("sticky", new Essence(Items.COBWEB, 100, 13159137));
    public static final Essence SLURRY_ESSENCE = register("slurry", new Essence(Items.STONE, 100, 11709331));
    public static final Essence FUNGAL_ESSENCE = register("fungal", new Essence(Items.BROWN_MUSHROOM, 100, 14330454));
    public static final Essence OLD_ESSENCE = register("old", new Essence(Items.TUBE_CORAL, 100, 1729252));
    public static final Essence SILENT_ESSENCE = register("silent", new Essence(Items.SCULK, 100, 1533066));
    public static final Essence GROWTH_ESSENCE = register("growth", new Essence(Items.BONE_MEAL, 100, 15394501));
    public static final Essence TIME_ESSENCE = register("time", new Essence(Items.EGG, 100, 2938762));
    public static final Essence WARP_ESSENCE = register("warp", new Essence(Items.CHORUS_FRUIT, 100, 13211092));
    public static final Essence COLD_ESSENCE = register("cold", new Essence(Items.SNOW_BLOCK, 100, 8442323));
    public static final Essence RESONANT_ESSENCE = register("resonant", new Essence(Items.AMETHYST_BLOCK, 100, 7543262));
    public static final Essence SOUL_ESSENCE = register("soul", new Essence(Items.SOUL_SAND, 100, 2860195));
    public static final Essence WATER_ESSENCE = register("water", new Essence(Items.ICE, 100, 11459547));
    public static final Essence YOUNG_ESSENCE =register("young", new Essence(Items.OAK_SAPLING, 100, 2601511));

    public static final Essence GLOWING_ESSENCE = register("glowing", new Essence(Items.GLOWSTONE_DUST, 100, 12766208));
    public static final Essence POWERED_ESSENCE = register("powered", new Essence(Items.REDSTONE, 100, 11550527));
    public static final Essence FERMENTED_ESSENCE = register("fermented", new Essence(Items.FERMENTED_SPIDER_EYE, 100, 14352453));

    // Modifier Essences
    public static final Essence SPREADING_ESSENCE = register("spreading", new Essence(Items.GUNPOWDER, 100, 6778468));
    public static final Essence LINGERING_ESSENCE = register("lingering", new Essence(Items.DRAGON_BREATH, 50, 14197743));
    public static final Essence PEACE_ESSENCE = register("peace", new Essence(ModItems.ORCHID_GECKO_SCALE, 100, 16760047));
    public static final Essence GRITTY_ESSENCE = register("gritty", new Essence(ModItems.SAND_GECKO_SCALE, 100, 9279591));
    public static final Essence MIDNIGHT_ESSENCE = register("midnight", new Essence(ModItems.BLACK_GECKO_SCALE, 100, 7237230));
    public static final Essence RAGE_ESSENCE = register("rage", new Essence(ModItems.CAT_GECKO_SCALE, 100, 15478061));
    public static final Essence TOKAY_ESSENCE = register("tokay", new Essence(ModItems.TOKAY_GECKO_SCALE, 100, 6012148));

    public static Essence register(String id, Essence essence) {
        return Registry.register(ModRegistries.ESSENCE, Gekosmagic.identify(id), essence);
    }

    public static Pair<Essence, Boolean> itemIsEssence(Item item) {
        for (RegistryEntry<Essence> essenceRegistryEntry : ModRegistries.ESSENCE.getIndexedEntries()) {
            Essence essenceToUse = essenceRegistryEntry.value();
            if (essenceToUse.asItem() == item) {
                return new Pair<>(essenceToUse, true);
            } else if (essenceToUse.itemIsEssence(item)) {
                return new Pair<>(essenceToUse, true);
            }
        }

        return new Pair<>(AIR, false);
    }

    public static void init() {}

    private Essences() {}
}
