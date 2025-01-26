package com.gekocaretaker.gekosmagic.elixir;

import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Essence implements ItemConvertible {
    public static final int DEFAULT_MAX_COUNT = 100;
    private final Item item;
    private final int maxCount;
    private final int color;
    @Nullable
    private String translationKey;

    public Essence(Item item, int maxCount) {
        this(item, maxCount, 6933380);
    }

    public Essence(Item item, int maxCount, int color) {
        this.item = item;
        this.color = color;
        if (maxCount > 100 || maxCount <= 0) {
            this.maxCount = DEFAULT_MAX_COUNT;
        } else {
            this.maxCount = maxCount;
        }
    }

    public int getMaxCount() {
        return maxCount;
    }

    @Override
    public Item asItem() {
        return this.item;
    }

    public boolean itemIsEssence(Item item) {
        return item.getRegistryEntry().isIn(TagKey.of(RegistryKeys.ITEM, ModRegistries.ESSENCE.getId(this).withPrefixedPath("essence/")));
    }

    public int getColor() {
        return this.color;
    }

    public int essenceLimit() {
        return this.maxCount;
    }

    protected String getOrCreateTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.createTranslationKey("essence", ModRegistries.ESSENCE.getId(this));
        }
        return this.translationKey;
    }

    public String getTranslationKey() {
        return this.getOrCreateTranslationKey();
    }

    public Text getName() {
        return Text.translatable(this.getTranslationKey());
    }

    public Optional<TooltipData> getTooltipData(EssenceContainer container) {
        return Optional.empty();
    }
}
