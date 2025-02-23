package com.gekocaretaker.gekosmagic.resource;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.util.Quadruple;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.*;

public class EssenceAssetLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
    public static final String ID = "essences";
    public static final EssenceAssetLoader INSTANCE = new EssenceAssetLoader();

    public static final Identifier ESSENCE_CONTAINER_TEXTURE = Gekosmagic.identify("textures/gui/sprites/container/alchemy_stand/essence_container.png");
    public static final Identifier ESSENCE_CONTAINER_FILL_TEXTURE = Gekosmagic.identify("textures/gui/sprites/container/alchemy_stand/essence_container_fill.png");
    public static final int ESSENCE_CONTAINER_TEXTURE_SIZE = 16;
    public static final int ESSENCE_CONTAINER_FILL_TEXTURE_SIZE = 10;

    public record EssenceEntry(Identifier essence,
                               Identifier containerTextureIdentifier, int containerTextureSize,
                               Identifier containerFillTextureIdentifier, int containerFillTextureSize) {
        public static final Codec<EssenceEntry> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(
                    Identifier.CODEC.fieldOf("essence").forGetter(EssenceEntry::essence),
                    Identifier.CODEC.optionalFieldOf("container_texture", ESSENCE_CONTAINER_TEXTURE).forGetter(EssenceEntry::containerTextureIdentifier),
                    Codec.INT.optionalFieldOf("container_texture_size", ESSENCE_CONTAINER_TEXTURE_SIZE).forGetter(EssenceEntry::containerTextureSize),
                    Identifier.CODEC.optionalFieldOf("container_full_texture", ESSENCE_CONTAINER_FILL_TEXTURE).forGetter(EssenceEntry::containerFillTextureIdentifier),
                    Codec.INT.optionalFieldOf("container_full_texture_size", ESSENCE_CONTAINER_FILL_TEXTURE_SIZE).forGetter(EssenceEntry::containerFillTextureSize)
            ).apply(instance, EssenceEntry::new);
        });

        public EssenceEntry(Identifier essence, Identifier containerTextureIdentifier, int containerTextureSize, Identifier containerFillTextureIdentifier, int containerFillTextureSize) {
            this.essence = essence;
            this.containerTextureIdentifier = containerTextureIdentifier.withPrefixedPath("textures/").withSuffixedPath(".png");
            this.containerTextureSize = containerTextureSize;
            this.containerFillTextureIdentifier = containerFillTextureIdentifier.withPrefixedPath("textures/").withSuffixedPath(".png");
            this.containerFillTextureSize = containerFillTextureSize;
        }
    }

    protected static final List<EssenceEntry> ESSENCES = new ArrayList<>();

    private EssenceAssetLoader() {
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
            jsonObject.addProperty("essence", identifier.toString());
            DataResult<EssenceEntry> result = EssenceEntry.CODEC.parse(JsonOps.INSTANCE, jsonObject);
            Optional<EssenceEntry> entry = result.result();
            if (entry.isPresent()) {
                ESSENCES.add(entry.get());
            } else {
                Gekosmagic.LOGGER.error("Essence Resource Entry '{}' could not be parsed.", identifier);
            }
        }));
    }

    public static Quadruple<Identifier, Integer, Identifier, Integer> getTexturesByEssence(Essence essence) {
        for (EssenceEntry entry : ESSENCES) {
            if (Objects.equals(entry.essence().toString(), essence.id().toString())) {
                return new Quadruple<>(entry.containerTextureIdentifier(), entry.containerTextureSize(), entry.containerFillTextureIdentifier(), entry.containerFillTextureSize());
            }
        }

        return new Quadruple<>(
                ESSENCE_CONTAINER_TEXTURE, ESSENCE_CONTAINER_TEXTURE_SIZE,
                ESSENCE_CONTAINER_FILL_TEXTURE, ESSENCE_CONTAINER_FILL_TEXTURE_SIZE);
    }
}
