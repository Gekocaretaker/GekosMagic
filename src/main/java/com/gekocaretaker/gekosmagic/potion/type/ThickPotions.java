package com.gekocaretaker.gekosmagic.potion.type;

import com.gekocaretaker.gekosmagic.effect.ModEffects;
import com.gekocaretaker.gekosmagic.potion.TypeBasedPotions;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;

public class ThickPotions extends TypeBasedPotions {
    public final Potion ADEPT_RABBIT_POTION = register("adept_rabbit", new Potion(
            "adept_rabbit",
            new StatusEffectInstance(StatusEffects.SPEED, seconds(20)),
            new StatusEffectInstance(ModEffects.VULNERABILITY, seconds(20))
    ));
    public final Potion LONG_ADEPT_RABBIT_POTION = register("long_adept_rabbit", new Potion(
            "adept_rabbit",
            new StatusEffectInstance(StatusEffects.SPEED, seconds(40)),
            new StatusEffectInstance(ModEffects.VULNERABILITY, seconds(40))
    ));
    public final Potion STRONG_ADEPT_RABBIT_POTION = register("strong_adept_rabbit", new Potion(
            "adept_rabbit",
            new StatusEffectInstance(StatusEffects.SPEED, seconds(20), 1),
            new StatusEffectInstance(ModEffects.VULNERABILITY, seconds(20), 1)
    ));

    public final Potion VULNERABILITY = register("vulnerability", new Potion(
            "vulnerability",
            new StatusEffectInstance(ModEffects.VULNERABILITY, seconds(20))
    ));
    public final Potion LONG_VULNERABILITY = register("long_vulnerability", new Potion(
            "vulnerability",
            new StatusEffectInstance(ModEffects.VULNERABILITY, seconds(40))
    ));

    @Override
    public void registerRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(
                    Potions.THICK,
                    Items.TURTLE_HELMET,
                    getEntry(ADEPT_RABBIT_POTION)
            );

            builder.registerPotionRecipe(
                    getEntry(ADEPT_RABBIT_POTION),
                    Items.REDSTONE,
                    getEntry(LONG_ADEPT_RABBIT_POTION)
            );

            builder.registerPotionRecipe(
                    getEntry(ADEPT_RABBIT_POTION),
                    Items.GLOWSTONE_DUST,
                    getEntry(STRONG_ADEPT_RABBIT_POTION)
            );
        });
    }

    protected ThickPotions() {}
    public static final ThickPotions POTIONS = new ThickPotions();
}
