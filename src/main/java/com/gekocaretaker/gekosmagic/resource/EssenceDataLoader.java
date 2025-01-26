package com.gekocaretaker.gekosmagic.resource;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.util.Quadruple;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.profiler.Profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EssenceDataLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
    public static final String ID = "essences";
    public static final EssenceDataLoader INSTANCE = new EssenceDataLoader();

    public record EssenceEntry(Essence essence,
                               Identifier containerTextureIdentifier, int containerTextureSize,
                               Identifier containerFillTextureIdentifier, int containerFillTextureSize) {}

    protected static final List<EssenceEntry> ESSENCES = new ArrayList<>();

    private EssenceDataLoader() {
        super(new Gson(), ID);
    }

    @Override
    public Identifier getFabricId() {
        return Gekosmagic.identify(ID);
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        ESSENCES.clear();
        prepared.forEach(((identifier, jsonElement) -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String essenceIdString = identifier.toString();
            Essence essence = ModRegistries.ESSENCE.get(Identifier.tryParse(essenceIdString));

            Identifier containerTextureId = Identifier.tryParse(jsonObject.get("container_texture").getAsString());
            containerTextureId = Identifier.of(containerTextureId.getNamespace(), "textures/" + containerTextureId.getPath() + ".png");
            int cts = 16;
            if (jsonObject.get("container_texture_size") != null) {
                cts = jsonObject.get("container_texture_size").getAsInt();
            }

            Identifier fullTextureId = Identifier.tryParse(jsonObject.get("container_full_texture").getAsString());
            fullTextureId = Identifier.of(fullTextureId.getNamespace(), "textures/" + fullTextureId.getPath() + ".png");
            int cfts = 10;
            if (jsonObject.get("container_full_texture_size") != null) {
                cfts = jsonObject.get("container_full_texture_size").getAsInt();
            }

            if (essence == null) {
                Gekosmagic.LOGGER.error("[Geko's Magic] Essence '" + essenceIdString + "' not found.");
                return;
            }

            ESSENCES.add(new EssenceEntry(essence, containerTextureId, cts, fullTextureId, cfts));
        }));
    }

    public static Quadruple<Identifier, Integer, Identifier, Integer> getTexturesByEssence(Essence essence) {
        for (EssenceEntry entry : ESSENCES) {
            if (entry.essence().equals(essence)) {
                return new Quadruple<>(entry.containerTextureIdentifier(), entry.containerTextureSize(), entry.containerFillTextureIdentifier(), entry.containerFillTextureSize());
            }
        }

        return new Quadruple<>(null, null, null, null);
    }
}
