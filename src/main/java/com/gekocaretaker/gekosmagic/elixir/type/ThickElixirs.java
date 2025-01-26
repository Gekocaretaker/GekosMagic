package com.gekocaretaker.gekosmagic.elixir.type;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.effect.ModEffects;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.util.EffectTime;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ThickElixirs {
    public static final RegistryEntry<Elixir> TURTLE_MASTER = registerType("turtle_master",
            new StatusEffectInstance(StatusEffects.SLOWNESS, EffectTime.SEC_20, 3),
            new StatusEffectInstance(StatusEffects.RESISTANCE, EffectTime.SEC_20, 2));
    public static final RegistryEntry<Elixir> LONG_TURTLE_MASTER = registerTypeM("turtle_master", "long",
            new StatusEffectInstance(StatusEffects.SLOWNESS, EffectTime.SEC_40, 3),
            new StatusEffectInstance(StatusEffects.RESISTANCE, EffectTime.SEC_40, 2));
    public static final RegistryEntry<Elixir> STRONG_TURTLE_MASTER = registerTypeM("turtle_master", "strong",
            new StatusEffectInstance(StatusEffects.SLOWNESS, EffectTime.SEC_20, 5),
            new StatusEffectInstance(StatusEffects.RESISTANCE, EffectTime.SEC_20, 3));

    public static final RegistryEntry<Elixir> ADEPT_RABBIT = registerType("adept_rabbit",
            new StatusEffectInstance(StatusEffects.SPEED, EffectTime.SEC_20, 2),
            new StatusEffectInstance(ModEffects.VULNERABILITY, EffectTime.SEC_20, 3));
    public static final RegistryEntry<Elixir> LONG_ADEPT_RABBIT = registerTypeM("adept_rabbit", "long",
            new StatusEffectInstance(StatusEffects.SPEED, EffectTime.SEC_40, 2),
            new StatusEffectInstance(ModEffects.VULNERABILITY, EffectTime.SEC_40, 3));
    public static final RegistryEntry<Elixir> STRONG_ADEPT_RABBIT = registerTypeM("adept_rabbit", "strong",
            new StatusEffectInstance(StatusEffects.SPEED, EffectTime.SEC_20, 3),
            new StatusEffectInstance(ModEffects.VULNERABILITY, EffectTime.SEC_20, 5));

    public static final RegistryEntry<Elixir> LUCK = registerType("luck",
            new StatusEffectInstance(StatusEffects.LUCK, EffectTime.MIN_5));

    public static final RegistryEntry<Elixir> BAD_LUCK = registerType("bad_luck",
            new StatusEffectInstance(StatusEffects.UNLUCK, EffectTime.MIN_5));

    public static final RegistryEntry<Elixir> VULNERABILITY = registerType("vulnerability",
            new StatusEffectInstance(ModEffects.VULNERABILITY, EffectTime.SEC_20));
    public static final RegistryEntry<Elixir> LONG_VULNERABILITY = registerTypeM("vulnerability", "long",
            new StatusEffectInstance(ModEffects.VULNERABILITY, EffectTime.SEC_40));

    private static RegistryEntry<Elixir> register(String id, Elixir elixir) {
        return Registry.registerReference(ModRegistries.ELIXIR, Gekosmagic.identify(id), elixir);
    }

    private static RegistryEntry<Elixir> registerType(String id, StatusEffectInstance... effects) {
        return register("thick_" + id, new Elixir("thick", id, effects));
    }

    private static RegistryEntry<Elixir> registerTypeM(String id, String modifier, StatusEffectInstance... effects) {
        return register("thick_" + modifier + "_" + id, new Elixir("thick", id, effects));
    }

    public static void init() {}

    private ThickElixirs() {}
}
