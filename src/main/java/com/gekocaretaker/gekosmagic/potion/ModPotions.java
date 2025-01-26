package com.gekocaretaker.gekosmagic.potion;

import com.gekocaretaker.gekosmagic.potion.type.MundanePotions;
import com.gekocaretaker.gekosmagic.potion.type.ThickPotions;

public class ModPotions {
    public static void init() {
        MundanePotions.POTIONS.registerRecipes();
        ThickPotions.POTIONS.registerRecipes();
    }

    private ModPotions() {}
}
