package com.gekocaretaker.gekosmagic.elixir;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

import java.util.List;

public class Essences {
    public static final Essence AIR = register("air", 100);
    public static final Essence BURNING = register("burning", 100, 16022546);
    public static final Essence NETHER = register("nether", 100, 15204352);
    public static final Essence MAGMA = register("magma", 100, 15250432);
    public static final Essence SWEET = register("sweet", 100, 13750737);
    public static final Essence SAD = register("sad", 100, 11189687);
    public static final Essence SLIMY = register("slimy", 100, 1296128);
    public static final Essence LUCKY = register("lucky", 100, 13746328);
    public static final Essence SHINY = register("shiny", 100, 16711812);
    public static final Essence SHARP = register("sharp", 100, 13026093);
    public static final Essence RICH = register("rich", 100, 13152784);
    public static final Essence STURDY = register("sturdy", 50, 2468682);
    public static final Essence EVIL = register("evil", 100, 11056515);
    public static final Essence FLOW = register("flow", 100, 7458253);
    public static final Essence FEAR = register("fear", 100, 7736839);
    public static final Essence STICKY = register("sticky", 100, 13159137);
    public static final Essence SLURRY = register("slurry", 100, 11709331);
    public static final Essence FUNGAL = register("fungal", 100, 14330454);
    public static final Essence OLD = register("old", 100, 1729252);
    public static final Essence SILENT = register("silent", 100, 1533066);
    public static final Essence GROWTH = register("growth", 100, 15394501);
    public static final Essence TIME = register("time", 100, 2938762);
    public static final Essence WARP = register("warp", 100, 13211092);
    public static final Essence COLD = register("cold", 100, 8442323);
    public static final Essence RESONANT = register("resonant", 100, 7543262);
    public static final Essence SOUL = register("soul", 100, 2860195);
    public static final Essence WATER = register("water", 100, 11459547);
    public static final Essence YOUNG = register("young", 100, 2601511);
    public static final Essence GLOWING = register("glowing", 100, 12766208);
    public static final Essence POWERED = register("powered", 100, 11550527);
    public static final Essence FERMENTED = register("fermented", 100, 14352453);
    public static final Essence SPREADING = register("spreading", 100, 6778468);
    public static final Essence LINGERING = register("lingering", 50, 14197743);
    public static final Essence PEACE = register("peace", 100, 16760047);
    public static final Essence GRITTY = register("gritty", 100, 9279591);
    public static final Essence MIDNIGHT = register("midnight", 100, 7237230);
    public static final Essence RAGE = register("rage", 100, 15478061);
    public static final Essence TOKAY = register("tokay", 100, 6012148);

    public static Pair<Essence, Boolean> itemIsEssence(ItemStack itemStack) {
        //DynamicRegistryManager registryManager = world.getRegistryManager();
        //List<Essence> essences = registryManager.get(ModRegistryKeys.ESSENCE).stream().toList();
        List<Essence> essences = ModRegistries.ESSENCE.stream().toList();
        for (Essence essence : essences) {
            if (essence.itemIsEssence(itemStack.getItem())) {
                return new Pair<>(essence, true);
            }
        }
        return new Pair<>(AIR, false);
        //return new Pair<>(registryManager.get(ModRegistryKeys.ESSENCE).get(AIR_ESSENCE), false);
    }

    private static RegistryKey<Essence> of(String id) {
        return RegistryKey.of(ModRegistryKeys.ESSENCE, Gekosmagic.identify(id));
    }

    private static Essence register(String id, int limit, int color) {
        return Registry.register(ModRegistries.ESSENCE, Gekosmagic.identify(id), new Essence(limit, color));
    }

    private static Essence register(String id, int limit) {
        return Registry.register(ModRegistries.ESSENCE, Gekosmagic.identify(id), new Essence(limit));
    }

    public static void init() {}

    private Essences() {}
}
