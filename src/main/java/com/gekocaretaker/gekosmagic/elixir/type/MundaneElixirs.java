package com.gekocaretaker.gekosmagic.elixir.type;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.effect.ModEffects;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.util.EffectTime;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class MundaneElixirs {
    public static final RegistryEntry<Elixir> LIZARD_BRAIN = registerType("lizard_brain",
            new StatusEffectInstance(ModEffects.LIZARD_BRAIN, EffectTime.MIN_1));
    public static final RegistryEntry<Elixir> LONG_LIZARD_BRAIN = registerTypeM("lizard_brain", "long",
            new StatusEffectInstance(ModEffects.LIZARD_BRAIN, EffectTime.MIN_2));

    public static final RegistryEntry<Elixir> ABSOLUTELY_NOT = registerType("absolutely_not",
            new StatusEffectInstance(ModEffects.ABSOLUTELY_NOT, EffectTime.MIN_3));
    public static final RegistryEntry<Elixir> LONG_ABSOLUTELY_NOT = registerTypeM("absolutely_not", "long",
            new StatusEffectInstance(ModEffects.ABSOLUTELY_NOT, EffectTime.MIN_8));

    private static RegistryEntry<Elixir> register(String id, Elixir elixir) {
        return Registry.registerReference(ModRegistries.ELIXIR, Gekosmagic.identify(id), elixir);
    }

    private static RegistryEntry<Elixir> registerType(String id, StatusEffectInstance... effects) {
        return register("mundane_" + id, new Elixir("mundane", id, effects));
    }

    private static RegistryEntry<Elixir> registerTypeM(String id, String modifier, StatusEffectInstance... effects) {
        return register("mundane_" + modifier + "_" + id, new Elixir("mundane", id, effects));
    }

    public static void init() {}

    private MundaneElixirs() {}
}
