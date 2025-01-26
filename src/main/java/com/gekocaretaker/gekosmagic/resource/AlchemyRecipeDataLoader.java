package com.gekocaretaker.gekosmagic.resource;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.recipe.alchemy.AlchemyAdvancedRecipe;
import com.gekocaretaker.gekosmagic.recipe.alchemy.AlchemyCustomRecipe;
import com.gekocaretaker.gekosmagic.recipe.alchemy.AlchemyElixirRecipe;
import com.gekocaretaker.gekosmagic.recipe.alchemy.AlchemyItemRecipe;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.*;

public class AlchemyRecipeDataLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
    public static final String ID = "alchemy";
    public static final AlchemyRecipeDataLoader INSTANCE = new AlchemyRecipeDataLoader();

    private AlchemyRecipeDataLoader() {
        super(new Gson(), ID);
    }

    @Override
    public Identifier getFabricId() {
        return Gekosmagic.identify(ID);
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        Gekosmagic.alchemyRecipeRegistry.clear();
        prepared.forEach((identifier, jsonElement) -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            jsonObject.addProperty("identifier", identifier.toString());

            String type = jsonObject.get("type").getAsString();
            jsonObject.remove("type");
            String essenceAsString = jsonObject.get("essence").getAsString();

            Essence essence = ModRegistries.ESSENCE.get(Identifier.tryParse(essenceAsString));

            if (Objects.equals(type, "gekosmagic:elixir")) {
                AlchemyElixirRecipe elixirRecipe = AlchemyElixirRecipe.CODEC.parse(JsonOps.INSTANCE, jsonObject).result().orElse(AlchemyElixirRecipe.EMPTY);
                if (AlchemyElixirRecipe.recipeExists(elixirRecipe)) {
                    Gekosmagic.alchemyRecipeRegistry.registerElixirRecipe(elixirRecipe);
                } else {
                    Gekosmagic.LOGGER.error("Failed to load alchemical elixir recipe '{}'.", identifier.toString());
                }
            } else if (Objects.equals(type, "gekosmagic:item")) {
                AlchemyItemRecipe itemRecipe = AlchemyItemRecipe.CODEC.parse(JsonOps.INSTANCE, jsonObject).result().orElse(AlchemyItemRecipe.EMPTY);
                if (AlchemyItemRecipe.recipeExists(itemRecipe)) {
                    Gekosmagic.alchemyRecipeRegistry.registerItemRecipe(itemRecipe);
                } else {
                    Gekosmagic.LOGGER.error("Failed to load alchemical item recipe '{}'.", identifier.toString());
                }
            } else if (Objects.equals(type, "gekosmagic:custom")) {
                AlchemyCustomRecipe customRecipe = AlchemyCustomRecipe.CODEC.parse(JsonOps.INSTANCE, jsonObject).result().orElse(AlchemyCustomRecipe.EMPTY);
                if (AlchemyCustomRecipe.recipeExists(customRecipe)) {
                    Gekosmagic.alchemyRecipeRegistry.registerCustomRecipe(customRecipe);
                } else {
                    Gekosmagic.LOGGER.error("Failed to load alchemical custom recipe '{}'.", identifier.toString());
                }
            } else if (Objects.equals(type, "gekosmagic:advanced")) {
                AlchemyAdvancedRecipe advancedRecipe = AlchemyAdvancedRecipe.CODEC.parse(JsonOps.INSTANCE, jsonObject).result().orElse(AlchemyAdvancedRecipe.EMPTY);
                if (AlchemyAdvancedRecipe.recipeExists(advancedRecipe)) {
                    Gekosmagic.alchemyRecipeRegistry.registerAdvancedRecipe(advancedRecipe);
                } else {
                    Gekosmagic.LOGGER.error("Failed to load alchemical advanced recipe '{}'.", identifier.toString());
                }
            } else {
                Gekosmagic.LOGGER.error("Failed to load alchemical recipe of type '" + type + "'.");
            }
        });
    }

    // Old elixir getter
    /*String fromAsString = jsonObject.get("from").getAsString();
      String toAsString = jsonObject.get("to").getAsString();
      Optional<RegistryEntry.Reference<Elixir>> from = ModRegistries.ELIXIR.getEntry(Identifier.tryParse(fromAsString));
      Optional<RegistryEntry.Reference<Elixir>> to = ModRegistries.ELIXIR.getEntry(Identifier.tryParse(toAsString));

      if (from.isPresent() && to.isPresent() && essence != null) {
          Gekosmagic.alchemyRecipeRegistry.registerElixirRecipe(
                  identifier, from.get(),
                  essence, to.get()
          );
      } else {
          Gekosmagic.LOGGER.error("Failed to load alchemical elixir recipe '" + identifier.toString() + "'.");
      }*/

    // Old item getter
    /*String fromAsString = jsonObject.get("from").getAsString();
      String toAsString = jsonObject.get("to").getAsString();
      Item from = Registries.ITEM.get(Identifier.tryParse(fromAsString));
      Item to = Registries.ITEM.get(Identifier.tryParse(toAsString));

      if (from != Items.AIR && to != Items.AIR && essence != null) {
          Gekosmagic.alchemyRecipeRegistry.registerItemRecipe(
                  identifier, from,
                  essence, to
          );
      } else {
          Gekosmagic.LOGGER.error("Failed to load alchemical item recipe '" + identifier.toString() + "'.");
      }*/

    // Old custom getter
    /*String fromAsString = jsonObject.get("from").getAsString();
      Optional<RegistryEntry.Reference<Elixir>> from = ModRegistries.ELIXIR.getEntry(Identifier.tryParse(fromAsString));

      ElixirContentsComponent toElixirContentsComponent = null;
      List<JsonElement> toValueElements = jsonObject.get("to").getAsJsonArray().asList();
      List<StatusEffectInstance> toStatusEffectInstances = new ArrayList<>();
      toValueElements.forEach(valueElement -> {
          JsonObject toValueAsObject = valueElement.getAsJsonObject();
          Optional<RegistryEntry.Reference<StatusEffect>> effect = Registries.STATUS_EFFECT.getEntry(Identifier.tryParse(toValueAsObject.get("effect").getAsString()));
          int ticks = toValueAsObject.get("ticks").getAsInt();
          int amplifier = toValueAsObject.get("amplifier").getAsInt();
          if (effect.isPresent()) {
              toStatusEffectInstances.add(new StatusEffectInstance(effect.get(), ticks, amplifier));
          }
      });
      if (!toStatusEffectInstances.isEmpty()) {
          toElixirContentsComponent = ElixirContentsComponent.DEFAULT.with(toStatusEffectInstances.toArray(new StatusEffectInstance[0]));
      }

      if (from.isPresent() && toElixirContentsComponent != ElixirContentsComponent.DEFAULT && essence != null) {
          Gekosmagic.alchemyRecipeRegistry.registerCustomRecipe(
              identifier, from.get(),
              essence, toElixirContentsComponent
          );
      } else {
          Gekosmagic.LOGGER.error("Failed to load alchemical custom recipe '" + identifier.toString() + "'.");
      }*/
}
