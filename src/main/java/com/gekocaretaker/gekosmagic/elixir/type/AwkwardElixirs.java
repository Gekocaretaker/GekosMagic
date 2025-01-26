package com.gekocaretaker.gekosmagic.elixir.type;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.util.EffectTime;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class AwkwardElixirs {
    public static final RegistryEntry<Elixir> REGENERATION = registerType("regeneration",
            new StatusEffectInstance(StatusEffects.REGENERATION, EffectTime.SEC_45));
    public static final RegistryEntry<Elixir> LONG_REGENERATION = registerTypeM("regeneration", "long",
            new StatusEffectInstance(StatusEffects.REGENERATION, EffectTime.MIN_1p5));
    public static final RegistryEntry<Elixir> STRONG_REGENERATION = registerTypeM("regeneration", "strong",
            new StatusEffectInstance(StatusEffects.REGENERATION, EffectTime.SEC_22p5, 1));

    public static final RegistryEntry<Elixir> SWIFTNESS = registerType("swiftness",
            new StatusEffectInstance(StatusEffects.SPEED, EffectTime.MIN_3));
    public static final RegistryEntry<Elixir> LONG_SWIFTNESS = registerTypeM("swiftness", "long",
            new StatusEffectInstance(StatusEffects.SPEED, EffectTime.MIN_8));
    public static final RegistryEntry<Elixir> STRONG_SWIFTNESS = registerTypeM("swiftness", "strong",
            new StatusEffectInstance(StatusEffects.SPEED, EffectTime.MIN_1p5, 1));

    public static final RegistryEntry<Elixir> FIRE_RESISTANCE = registerType("fire_resistance",
            new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, EffectTime.MIN_3));
    public static final RegistryEntry<Elixir> LONG_FIRE_RESISTANCE = registerTypeM("fire_resistance", "long",
            new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, EffectTime.MIN_8));

    public static final RegistryEntry<Elixir> HEALING = registerType("healing",
            new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, EffectTime.INSTANT));
    public static final RegistryEntry<Elixir> STRONG_HEALING = registerTypeM("healing", "strong",
            new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, EffectTime.INSTANT, 1));

    public static final RegistryEntry<Elixir> NIGHT_VISION = registerType("night_vision",
            new StatusEffectInstance(StatusEffects.NIGHT_VISION, EffectTime.MIN_3));
    public static final RegistryEntry<Elixir> LONG_NIGHT_VISION = registerTypeM("night_vision", "long",
            new StatusEffectInstance(StatusEffects.NIGHT_VISION, EffectTime.MIN_8));

    public static final RegistryEntry<Elixir> STRENGTH = registerType("strength",
            new StatusEffectInstance(StatusEffects.STRENGTH, EffectTime.MIN_3));
    public static final RegistryEntry<Elixir> LONG_STRENGTH = registerTypeM("strength", "long",
            new StatusEffectInstance(StatusEffects.STRENGTH, EffectTime.MIN_8));
    public static final RegistryEntry<Elixir> STRONG_STRENGTH = registerTypeM("strength", "strong",
            new StatusEffectInstance(StatusEffects.STRENGTH, EffectTime.MIN_1p5, 1));

    public static final RegistryEntry<Elixir> LEAPING = registerType("leaping",
            new StatusEffectInstance(StatusEffects.JUMP_BOOST, EffectTime.MIN_3));
    public static final RegistryEntry<Elixir> LONG_LEAPING = registerTypeM("leaping", "long",
            new StatusEffectInstance(StatusEffects.JUMP_BOOST, EffectTime.MIN_8));
    public static final RegistryEntry<Elixir> STRONG_LEAPING = registerTypeM("leaping", "strong",
            new StatusEffectInstance(StatusEffects.JUMP_BOOST, EffectTime.MIN_1p5, 1));

    public static final RegistryEntry<Elixir> WATER_BREATHING = registerType("water_breathing",
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, EffectTime.MIN_3));
    public static final RegistryEntry<Elixir> LONG_WATER_BREATHING = registerTypeM("water_breathing", "long",
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, EffectTime.MIN_8));

    public static final RegistryEntry<Elixir> INVISIBILITY = registerType("invisibility",
            new StatusEffectInstance(StatusEffects.INVISIBILITY, EffectTime.MIN_3));
    public static final RegistryEntry<Elixir> LONG_INVISIBILITY = registerTypeM("invisibility", "long",
            new StatusEffectInstance(StatusEffects.INVISIBILITY, EffectTime.MIN_8));

    public static final RegistryEntry<Elixir> SLOW_FALLING = registerType("slow_falling",
            new StatusEffectInstance(StatusEffects.SLOW_FALLING, EffectTime.MIN_1p5));
    public static final RegistryEntry<Elixir> LONG_SLOW_FALLING = registerTypeM("slow_falling", "long",
            new StatusEffectInstance(StatusEffects.SLOW_FALLING, EffectTime.MIN_4));

    public static final RegistryEntry<Elixir> POISON = registerType("poison",
            new StatusEffectInstance(StatusEffects.POISON, EffectTime.SEC_45));
    public static final RegistryEntry<Elixir> LONG_POISON = registerTypeM("poison", "long",
            new StatusEffectInstance(StatusEffects.POISON, EffectTime.MIN_1p5));
    public static final RegistryEntry<Elixir> STRONG_POISON = registerTypeM("poison", "strong",
            new StatusEffectInstance(StatusEffects.POISON, EffectTime.SEC_21p6, 1));

    public static final RegistryEntry<Elixir> WEAKNESS = registerType("weakness",
            new StatusEffectInstance(StatusEffects.WEAKNESS, EffectTime.MIN_1p5));
    public static final RegistryEntry<Elixir> LONG_WEAKNESS = registerTypeM("weakness", "long",
            new StatusEffectInstance(StatusEffects.WEAKNESS, EffectTime.MIN_4));

    public static final RegistryEntry<Elixir> SLOWNESS = registerType("slowness",
            new StatusEffectInstance(StatusEffects.SLOWNESS, EffectTime.MIN_1p5));
    public static final RegistryEntry<Elixir> LONG_SLOWNESS = registerTypeM("slowness", "long",
            new StatusEffectInstance(StatusEffects.SLOWNESS, EffectTime.MIN_4));
    public static final RegistryEntry<Elixir> STRONG_SLOWNESS = registerTypeM("slowness", "strong",
            new StatusEffectInstance(StatusEffects.SLOWNESS, EffectTime.SEC_20, 3));

    public static final RegistryEntry<Elixir> HARMING = registerType("harming",
            new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, EffectTime.INSTANT));
    public static final RegistryEntry<Elixir> STRONG_HARMING = registerTypeM("harming", "strong",
            new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, EffectTime.INSTANT, 1));

    public static final RegistryEntry<Elixir> DECAY = registerType("decay",
            new StatusEffectInstance(StatusEffects.WITHER, EffectTime.SEC_40));

    public static final RegistryEntry<Elixir> INFESTATION = registerType("infestation",
            new StatusEffectInstance(StatusEffects.INFESTED, EffectTime.MIN_3));

    public static final RegistryEntry<Elixir> OOZING = registerType("oozing",
            new StatusEffectInstance(StatusEffects.OOZING, EffectTime.MIN_3));

    public static final RegistryEntry<Elixir> WEAVING = registerType("weaving",
            new StatusEffectInstance(StatusEffects.WEAVING, EffectTime.MIN_3));

    public static final RegistryEntry<Elixir> WIND_CHARGING = registerType("wind_charging",
            new StatusEffectInstance(StatusEffects.WIND_CHARGED, EffectTime.MIN_3));

    private static RegistryEntry<Elixir> register(String id, Elixir elixir) {
        return Registry.registerReference(ModRegistries.ELIXIR, Gekosmagic.identify(id), elixir);
    }

    private static RegistryEntry<Elixir> registerType(String id, StatusEffectInstance... effects) {
        return register("awkward_" + id, new Elixir("awkward", id, effects));
    }

    private static RegistryEntry<Elixir> registerTypeM(String id, String modifier, StatusEffectInstance... effects) {
        return register("awkward_" + modifier + "_" + id, new Elixir("awkward", id, effects));
    }

    public static void init() {}

    private AwkwardElixirs() {}
}
