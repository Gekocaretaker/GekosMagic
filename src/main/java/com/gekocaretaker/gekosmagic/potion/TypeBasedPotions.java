package com.gekocaretaker.gekosmagic.potion;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public abstract class TypeBasedPotions {
    protected TypeBasedPotions() {}

    protected static <T extends Potion> T register(String path, T potion) {
        return Registry.register(
                Registries.POTION,
                Gekosmagic.identify(path),
                potion
        );
    }

    protected static int minutes(int minutes) {
        return seconds(minutes * 60);
    }

    protected static int seconds(int seconds) {
        return seconds * 20;
    }

    protected static RegistryEntry<Potion> getEntry(Potion potion) {
        return Registries.POTION.getEntry(potion);
    }

    public abstract void registerRecipes();
}
