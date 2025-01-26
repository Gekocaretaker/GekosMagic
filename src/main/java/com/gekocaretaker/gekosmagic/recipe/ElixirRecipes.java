package com.gekocaretaker.gekosmagic.recipe;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.elixir.Essences;
import com.gekocaretaker.gekosmagic.item.ModItems;
import net.minecraft.item.Item;

public class ElixirRecipes {
    public static void init() {
        registerEssence(Essences.BURNING_ESSENCE);
        registerEssence(Essences.NETHER_ESSENCE);
        registerEssence(Essences.MAGMA_ESSENCE);
        registerEssence(Essences.SWEET_ESSENCE);
        registerEssence(Essences.SAD_ESSENCE);
        registerEssence(Essences.SLIMY_ESSENCE);
        registerEssence(Essences.LUCKY_ESSENCE);
        registerEssence(Essences.SHINY_ESSENCE);
        registerEssence(Essences.SHARP_ESSENCE);
        registerEssence(Essences.RICH_ESSENCE);
        registerEssence(Essences.STURDY_ESSENCE);
        registerEssence(Essences.EVIL_ESSENCE);
        registerEssence(Essences.FLOW_ESSENCE);
        registerEssence(Essences.FEAR_ESSENCE);
        registerEssence(Essences.STICKY_ESSENCE);
        registerEssence(Essences.SLURRY_ESSENCE);
        registerEssence(Essences.FUNGAL_ESSENCE);
        registerEssence(Essences.OLD_ESSENCE);
        registerEssence(Essences.SILENT_ESSENCE);
        registerEssence(Essences.GROWTH_ESSENCE);
        registerEssence(Essences.TIME_ESSENCE);
        registerEssence(Essences.WARP_ESSENCE);
        registerEssence(Essences.COLD_ESSENCE);
        registerEssence(Essences.RESONANT_ESSENCE);
        registerEssence(Essences.SOUL_ESSENCE);
        registerEssence(Essences.WATER_ESSENCE);
        registerEssence(Essences.YOUNG_ESSENCE);

        registerEssence(Essences.FERMENTED_ESSENCE);
        registerEssence(Essences.GLOWING_ESSENCE);
        registerEssence(Essences.POWERED_ESSENCE);

        registerEssence(Essences.SPREADING_ESSENCE);
        registerEssence(Essences.LINGERING_ESSENCE);
        registerEssence(Essences.PEACE_ESSENCE);
        registerEssence(Essences.GRITTY_ESSENCE);
        registerEssence(Essences.MIDNIGHT_ESSENCE);
        registerEssence(Essences.RAGE_ESSENCE);
        registerEssence(Essences.TOKAY_ESSENCE);

    }

    private static void registerEssence(Essence essence) {
        Gekosmagic.alchemyRecipeRegistry.registerElixirType(essence);
    }

    private ElixirRecipes() {}
}
