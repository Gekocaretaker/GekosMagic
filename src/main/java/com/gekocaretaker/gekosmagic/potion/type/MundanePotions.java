package com.gekocaretaker.gekosmagic.potion.type;

import com.gekocaretaker.gekosmagic.effect.ModEffects;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.potion.TypeBasedPotions;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;

public class MundanePotions extends TypeBasedPotions {
    public final Potion LIZARD_BRAIN_POTION = register("lizard_brain", new Potion(
            "lizard_brain",
            new StatusEffectInstance(
                    ModEffects.LIZARD_BRAIN,
                    seconds(60)
            )
    ));
    public final Potion LONG_LIZARD_BRAIN_POTION = register("long_lizard_brain", new Potion(
            "lizard_brain",
            new StatusEffectInstance(
                    ModEffects.LIZARD_BRAIN,
                    seconds(120)
            )
    ));

    public final Potion ABSOLUTELY_NOT_POTION = register("absolutely_not", new Potion(
            "absolutely_not",
            new StatusEffectInstance(
                    ModEffects.ABSOLUTELY_NOT,
                    minutes(3)
            )
    ));
    public final Potion LONG_ABSOLUTELY_NOT_POTION = register("long_absolutely_not", new Potion(
            "absolutely_not",
            new StatusEffectInstance(
                    ModEffects.ABSOLUTELY_NOT,
                    minutes(8)
            )
    ));

    @Override
    public void registerRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(
                    Potions.MUNDANE,
                    ModItems.TOKAY_GECKO_SCALE,
                    getEntry(LIZARD_BRAIN_POTION)
            );

            builder.registerPotionRecipe(
                    getEntry(LIZARD_BRAIN_POTION),
                    Items.REDSTONE,
                    getEntry(LONG_LIZARD_BRAIN_POTION)
            );

            builder.registerPotionRecipe(
                    Potions.MUNDANE,
                    Items.POPPED_CHORUS_FRUIT,
                    getEntry(ABSOLUTELY_NOT_POTION)
            );

            builder.registerPotionRecipe(
                    getEntry(ABSOLUTELY_NOT_POTION),
                    Items.REDSTONE,
                    getEntry(LONG_ABSOLUTELY_NOT_POTION)
            );
        });
    }

    protected MundanePotions() {}
    public static final MundanePotions POTIONS = new MundanePotions();
}
