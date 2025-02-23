package com.gekocaretaker.gekosmagic.elixir;

import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Essence {
    public static final int DEFAULT_LIMIT = 100;
    public static final int DEFAULT_COLOR = 6933380;
    public static final Codec<Essence> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
            Codec.INT.fieldOf("limit").validate(integer ->
                    integer.intValue() <= DEFAULT_LIMIT && integer > 0 ? DataResult.success(integer) : DataResult.error(() ->
                            "Essence limit must be at least 1 and at most 100")).forGetter(Essence::limit),
            Codec.INT.fieldOf("color").forGetter(Essence::color)
    ).apply(instance, Essence::new));
    public static final Codec<RegistryEntry<Essence>> REGISTRY_CODEC = ModRegistries.ESSENCE.getEntryCodec().validate((entry) -> {
        return entry.matches(ModRegistries.ESSENCE.getEntry(Essences.AIR)) ? DataResult.error(() -> {
            return "Essence must not be gekosmagic:air";
        }) : DataResult.success(entry);
    });
    public static final Codec<RegistryEntryList<Essence>> REGISTRY_ENTRY_LIST_CODEC = RegistryCodecs.entryList(ModRegistryKeys.ESSENCE, CODEC);
    public static PacketCodec<RegistryByteBuf, Essence> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            Essence::limit,
            PacketCodecs.INTEGER,
            Essence::color,
            Essence::new
    );

    private final int limit;
    private final int color;
    @Nullable
    private String translationKey;

    public Essence(int limit) {
        this(limit, DEFAULT_COLOR);
    }

    public Essence(int limit, int color) {
        if (limit > DEFAULT_LIMIT || limit <= 0) {
            this.limit = DEFAULT_LIMIT;
        } else {
            this.limit = limit;
        }
        this.color = color;
    }

    public int limit() {
        return this.limit;
    }

    public boolean itemIsEssence(Item item) {
        TagKey<Item> essenceTag = TagKey.of(RegistryKeys.ITEM, ModRegistries.ESSENCE.getId(this).withPrefixedPath("essence/"));
        return new ItemStack(item).isIn(essenceTag);
    }

    public int color() {
        return this.color;
    }

    protected String getOrCreateTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.createTranslationKey("essence", ModRegistries.ESSENCE.getId(this));
        }
        return this.translationKey;
    }

    public Text getName() {
        return Text.translatable(this.getOrCreateTranslationKey());
    }

    public Optional<TooltipData> getTooltipData(EssenceContainer container) {
        return Optional.empty();
    }

    public Identifier id() {
        return ModRegistries.ESSENCE.getId(this);
    }
}
