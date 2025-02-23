package com.gekocaretaker.gekosmagic;

import com.gekocaretaker.gekosmagic.block.ModBlocks;
import com.gekocaretaker.gekosmagic.block.entity.ModBlockEntityTypes;
import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.elixir.Elixirs;
import com.gekocaretaker.gekosmagic.elixir.Essences;
import com.gekocaretaker.gekosmagic.enchantment.ModEnchantments;
import com.gekocaretaker.gekosmagic.entity.ModEntities;
import com.gekocaretaker.gekosmagic.entity.data.ModTrackedDataHandlerRegistry;
import com.gekocaretaker.gekosmagic.entity.passive.GeckoVariants;
import com.gekocaretaker.gekosmagic.item.ModItemGroups;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.loot.ModLootTables;
import com.gekocaretaker.gekosmagic.loot.function.ModLootFunctionTypes;
import com.gekocaretaker.gekosmagic.network.ModPayloads;
import com.gekocaretaker.gekosmagic.potion.ModPotions;
import com.gekocaretaker.gekosmagic.predicate.entity.ModEntitySubPredicateTypes;
import com.gekocaretaker.gekosmagic.recipe.AlchemyRecipeRegistry;
import com.gekocaretaker.gekosmagic.recipe.ModRecipeSerializers;
import com.gekocaretaker.gekosmagic.recipe.ModRecipeTypes;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import com.gekocaretaker.gekosmagic.screen.ModScreenHandlerTypes;
import com.gekocaretaker.gekosmagic.sound.ModSounds;
import com.gekocaretaker.gekosmagic.stat.ModStats;
import com.gekocaretaker.gekosmagic.util.ModTags;
import com.gekocaretaker.gekosmagic.village.ModTradeOffers;
import com.gekocaretaker.gekosmagic.world.ModWorldGen;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gekosmagic implements ModInitializer {
    public static final String MODID = "gekosmagic";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final AlchemyRecipeRegistry alchemyRecipeRegistry = AlchemyRecipeRegistry.create();

    @Override
    public void onInitialize() {
        Elixirs.init();
        Essences.init();
        GeckoVariants.init();

        ModBlockEntityTypes.init();
        ModBlocks.init();
        ModDataComponentTypes.init();
        ModEnchantments.init();
        ModEntities.init();
        ModEntitySubPredicateTypes.init();
        ModItemGroups.init();
        ModItems.init();
        ModLootFunctionTypes.init();
        ModLootTables.init();
        ModPayloads.init();
        ModPotions.init();
        ModRecipeSerializers.init();
        ModRecipeTypes.init();
        ModRegistries.init();
        ModRegistryKeys.init();
        ModScreenHandlerTypes.init();
        ModSounds.init();
        ModStats.init();
        ModTags.init();
        ModTrackedDataHandlerRegistry.init();
        ModTradeOffers.init();
        ModWorldGen.init();
    }

    public static Identifier identify(String path) {
        return Identifier.of(MODID, path);
    }

    /*
      Notes:
      Jeb said that there was 161 brewable unique potions.
      He also said there would be 2,653 if all ingredients were added.

      Places that I must add new ElixirItems to:
      AlchemyStandScreenHandler
      AlchemyRecipeRegistry
      AlchemyStandBlockEntity
      GekosmagicEmi
      GekosmagicClient
      ModItemGroups

      // Update for this group of lines, I am not giving each base a unique feature, as they serve a different purpose.
      Maybe make each ingredient able to become a base elixir.
      That would mean for all vanilla (minus gunpowder & dragon breath)
      and gecko scales + (plus any other ingredients that comes along)
      Each should maybe have a small unique feature

      Base Elixirs + their ingredient:
      Awkward + Nether Wart
      Thick + Slime Block
      Mundane + Magma Cream
      Sparkling + Sugar
      Milky + Ghast Tear
      Artless + Turtle Scute
      Thin + Rabbit's Foot
      Flat + Glistering Melon
      Bulky + Stone
      Bungling + Pufferfish
      Smooth + Golden Carrot
      Suave + Phantom Membrane
      Debonair + Cobweb
      Elegant + Wind Charge
      Fancy + Blaze Powder
      Charming + Spider Eye
      Dashing + Brown Mushroom
      Refined + Tube Coral
      Cordial + Sculk
      Potent + Bonemeal
      Foul + Egg
      Odorless + Chorus Fruit
      Rank + Snow Block
      Harsh + Amethyst Block
      Acrid + Soul Sand
      Gross + Ice
      Stinky + Oak Sapling

      Elixir Types + Modifying Ingredient + <use|optional>:
      Splash + Gunpowder + Allows elixir to be thrown
      Lingering + Dragon's Breath + Allows elixir to be thrown, creates an effect cloud
      Uninteresting + Sand Gecko Scale + Hides the effect(s), but keeps color and name
      Bland + Cat Gecko Scale + Makes the water generic colored and generic name, but keeps effect(s)
      Clear + Orchid Gecko Scale + Hides the effect(s), color, and name
      Diffuse + Tokay Gecko Scale + Instant elixirs that remove the specific effect(s)
      Buttered + Black Gecko Scale + Makes drinking faster

      Ingredients with a special purpose.
            These are manually implemented, and should not interchange with each other.
            Could be used as a basic ingredient, but heavily discouraged:
      Redstone Dust (Increases time)
      Glowstone Dust (Increases power at a cost of time)
      Fermented Spider Eye (Reversion, aka turning potion to it's opposite)

      Main Ingredients that do not create a base:
      None as of now.
     */
}
