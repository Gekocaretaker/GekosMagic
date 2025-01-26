package com.gekocaretaker.gekosmagic.effect;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> LIZARD_BRAIN = register("lizard_brain", new LizardBrainEffect());
    public static final RegistryEntry<StatusEffect> ABSOLUTELY_NOT = register("absolutely_not", new AbsolutelyNotEffect());
    public static final RegistryEntry<StatusEffect> VULNERABILITY = register("vulnerability", new VulnerabilityEffect());

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Gekosmagic.identify(id), statusEffect);
    }

    public static void init() {}

    private ModEffects() {}
}
