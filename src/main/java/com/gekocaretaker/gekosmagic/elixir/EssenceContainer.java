package com.gekocaretaker.gekosmagic.elixir;

import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

public class EssenceContainer {
    public static final Codec<RegistryEntry<Essence>> ESSENCE_CODEC = ModRegistries.ESSENCE.getEntryCodec().validate((entry) -> {
        return entry.matches(ModRegistries.ESSENCE.getEntry(Essences.AIR)) ? DataResult.error(() -> {
            return "Essence must not be gekosmagic:air";
        }) : DataResult.success(entry);
    });

    public static final Codec<EssenceContainer> CODEC = Codec.lazyInitialized(() -> {
        return RecordCodecBuilder.create((instance) -> {
            return instance.group(ESSENCE_CODEC.fieldOf("id").forGetter(EssenceContainer::getRegistryEntry),
                    Codecs.rangedInt(0, 100).fieldOf("count").orElse(1).forGetter(EssenceContainer::getCount))
                    .apply(instance, EssenceContainer::new);
        });
    });

    public static final Codec<EssenceContainer> REGISTRY_ENTRY_CODEC = ESSENCE_CODEC.xmap(EssenceContainer::new, EssenceContainer::getRegistryEntry);

    public static final PacketCodec<RegistryByteBuf, EssenceContainer> OPTIONAL_PACKET_CODEC = new PacketCodec<RegistryByteBuf, EssenceContainer>() {
        private static final PacketCodec<RegistryByteBuf, RegistryEntry<Essence>> ESSENCE_PACKET_CODEC = PacketCodecs.registryEntry(ModRegistryKeys.ESSENCE);

        @Override
        public EssenceContainer decode(RegistryByteBuf buf) {
            int i = buf.readVarInt();
            if (i < 0) {
                return EssenceContainer.EMPTY;
            } else {
                RegistryEntry<Essence> registryEntry = ESSENCE_PACKET_CODEC.decode(buf);
                return new EssenceContainer(registryEntry, i);
            }
        }

        @Override
        public void encode(RegistryByteBuf buf, EssenceContainer value) {
            if (value.isEmpty()) {
                buf.writeVarInt(0);
            } else {
                buf.writeVarInt(value.getCount());
                ESSENCE_PACKET_CODEC.encode(buf, value.getRegistryEntry());
            }
        }
    };

    public static final PacketCodec<RegistryByteBuf, EssenceContainer> PACKET_CODEC = new PacketCodec<RegistryByteBuf, EssenceContainer>() {
        @Override
        public EssenceContainer decode(RegistryByteBuf buf) {
            EssenceContainer essenceContainer = (EssenceContainer) EssenceContainer.OPTIONAL_PACKET_CODEC.decode(buf);
            if (essenceContainer.isEmpty()) {
                throw new DecoderException("Empty EssenceContainer not allowed");
            } else {
                return essenceContainer;
            }
        }

        @Override
        public void encode(RegistryByteBuf buf, EssenceContainer value) {
            if (value.isEmpty()) {
                throw new EncoderException("Empty EssenceContainer not allowed");
            } else {
                EssenceContainer.OPTIONAL_PACKET_CODEC.encode(buf, value);
            }
        }
    };

    public static final PacketCodec<RegistryByteBuf, List<EssenceContainer>> LIST_PACKET_CODEC = PACKET_CODEC.collect(PacketCodecs.toList());

    private static final Logger LOGGER = LogUtils.getLogger();
    private int count;
    private final Essence essence;
    public static final EssenceContainer EMPTY = new EssenceContainer((Void) null);

    public EssenceContainer(RegistryEntry<Essence> essence) {
        this(essence.value(), 1);
    }

    public EssenceContainer(Essence essence, int count) {
        this.essence = essence;
        this.count = count;
    }

    public EssenceContainer(RegistryEntry<Essence> essenceRegistryEntry, Integer integer) {
        this(essenceRegistryEntry.value(), integer.intValue());
    }

    private EssenceContainer(@Nullable Void v) {
        this.essence = null;
        this.count = 0;
    }

    public Essence getEssence() {
        return this.essence;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment(int amount) {
        this.setCount(this.getCount() + amount);
    }

    public void decrement(int amount) {
        this.increment(-amount);
    }

    public int getMaxCount() {
        return this.essence.getMaxCount();
    }

    public boolean isCountMaxed() {
        return this.count == this.getMaxCount();
    }

    public void capCount() {
        if (this.getCount() > this.getMaxCount()) {
            this.count = this.getMaxCount();
        }
    }

    public int getEssenceColor() {
        return this.essence.getColor();
    }

    public String getTranslationKey() {
        return this.essence.getTranslationKey();
    }

    public Text getName() {
        return this.essence.getName();
    }

    public List<Text> getTooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type) {
        return this.getTooltip(type);
    }

    public List<Text> getTooltip(TooltipType type) {
        List<Text> list = Lists.newArrayList();
        MutableText mutableText = Text.empty().append(this.getName()).withColor(this.getEssenceColor());
        list.add(mutableText);
        MutableText fillText = Text.literal(this.getCount() + " / " + this.getMaxCount()).formatted(Formatting.DARK_PURPLE);
        list.add(fillText);

        if (type.isAdvanced()) {
            MutableText idText = Text.literal(ModRegistries.ESSENCE.getId(this.getEssence()).toString()).formatted(Formatting.DARK_GRAY);
            list.add(idText);
        }

        return list;
    }

    public Text toHoverableText() {
        MutableText mutableText = Text.empty().append(this.getName());
        MutableText mutableText2 = Texts.bracketed(mutableText);
        return mutableText2;
    }

    public Optional<TooltipData> getTooltipData() {
        return this.getEssence().getTooltipData(this);
    }

    public RegistryEntry<Essence> getRegistryEntry() {
        return ModRegistries.ESSENCE.getEntry(this.essence);
    }

    public boolean isOf(Essence essence) {
        return this.essence == essence;
    }

    public boolean isOf(ItemStack itemStack) {
        return isOf(itemStack.getItem());
    }

    public boolean isOf(Item item) {
        return this.essence.itemIsEssence(item);
    }

    public boolean isEmpty() {
        return this == EMPTY || this.essence == Essences.AIR || this.essence == null || this.count < 0;
    }

    public static Optional<EssenceContainer> fromNbt(RegistryWrapper.WrapperLookup registries, NbtElement nbt) {
        return CODEC.parse(registries.getOps(NbtOps.INSTANCE), nbt).resultOrPartial((error) -> {
            LOGGER.error("Tried to load invalid essence: '{}'", error);
        });
    }

    public NbtElement encode(RegistryWrapper.WrapperLookup registries, NbtElement prefix) {
        return CODEC.encode(this, registries.getOps(NbtOps.INSTANCE), prefix).getOrThrow();
    }
}
