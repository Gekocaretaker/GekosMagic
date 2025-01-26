package com.gekocaretaker.gekosmagic.elixir;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.elixir.type.AwkwardElixirs;
import com.gekocaretaker.gekosmagic.elixir.type.MundaneElixirs;
import com.gekocaretaker.gekosmagic.elixir.type.ThickElixirs;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class Elixirs {
    public static final RegistryEntry<Elixir> WATER = register("water", Elixir.ofBase("water", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> AWKWARD = register("awkward", Elixir.ofType("awkward", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> THICK = register("thick", Elixir.ofType("thick", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> MUNDANE = register("mundane", Elixir.ofType("mundane", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> SPARKLING = register("sparkling", Elixir.ofType("sparkling", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> MILKY = register("milky", Elixir.ofType("milky", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> ARTLESS = register("artless", Elixir.ofType("artless", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> THIN = register("thin", Elixir.ofType("thin", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> FLAT = register("flat", Elixir.ofType("flat", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> BULKY = register("bulky", Elixir.ofType("bulky", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> BUNGLING = register("bungling", Elixir.ofType("bungling", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> SMOOTH = register("smooth", Elixir.ofType("smooth", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> SUAVE = register("suave", Elixir.ofType("suave", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> DEBONAIR = register("debonair", Elixir.ofType("debonair", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> ELEGANT = register("elegant", Elixir.ofType("elegant", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> FANCY = register("fancy", Elixir.ofType("fancy", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> CHARMING = register("charming", Elixir.ofType("charming", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> DASHING = register("dashing", Elixir.ofType("dashing", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> REFINED = register("refined", Elixir.ofType("refined", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> CORDIAL = register("cordial", Elixir.ofType("cordial", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> POTENT = register("potent", Elixir.ofType("potent", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> FOUL = register("foul", Elixir.ofType("foul", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> ODORLESS = register("odorless", Elixir.ofType("odorless", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> RANK = register("rank", Elixir.ofType("rank", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> HARSH = register("harsh", Elixir.ofType("harsh", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> ACRID = register("acrid", Elixir.ofType("acrid", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> GROSS = register("gross", Elixir.ofType("gross", new StatusEffectInstance[0]));
    public static final RegistryEntry<Elixir> STINKY = register("stinky", Elixir.ofType("stinky", new StatusEffectInstance[0]));

    private static RegistryEntry<Elixir> register(String id, Elixir elixir) {
        return Registry.registerReference(ModRegistries.ELIXIR, Gekosmagic.identify(id), elixir);
    }

    public static void init() {
        AwkwardElixirs.init();
        ThickElixirs.init();
        MundaneElixirs.init();
    }

    private Elixirs() {}
}
