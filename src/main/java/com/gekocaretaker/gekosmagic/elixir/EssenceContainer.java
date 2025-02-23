package com.gekocaretaker.gekosmagic.elixir;

import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
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
import net.minecraft.util.ColorCode;
import net.minecraft.util.Formatting;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("UnreachableCode") // This is here because intellij thinks that the static block at the bottom is unreachable.
public class EssenceContainer {
    public static final Codec<EssenceContainer> CODEC;
    public static final PacketCodec<RegistryByteBuf, EssenceContainer> OPTIONAL_PACKET_CODEC;
    public static final PacketCodec<RegistryByteBuf, EssenceContainer> PACKET_CODEC;
    public static final PacketCodec<RegistryByteBuf, List<EssenceContainer>> LIST_PACKET_CODEC;

    private static final Logger LOGGER = LogUtils.getLogger();
    private int count;
    private final Essence essence;
    //private RegistryEntry<Essence> essenceRegistryEntry;
    public static final EssenceContainer EMPTY = new EssenceContainer((Void) null);

    public EssenceContainer(Essence essence) {
        this(essence, 1);
    }

    public EssenceContainer(Essence essence, int count) {
        this.essence = essence;
        this.count = count;
    }

    public EssenceContainer(RegistryEntry<Essence> essenceEntry) {
        this(essenceEntry, 1);
    }

    public EssenceContainer(RegistryEntry<Essence> essenceEntry, int count) {
        //this.essenceRegistryEntry = essenceEntry;
        this.essence = essenceEntry.value();
        this.count = count;
    }

    private EssenceContainer(@Nullable Void v) {
        this.essence = null;
        this.count = 0;
    }

    public Essence getEssence() {
        return this.essence;
    }

    public RegistryEntry<Essence> getEssenceRegistryEntry() {
        return ModRegistries.ESSENCE.getEntry(essence);
        //return this.essenceRegistryEntry;
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

    public int getLimit() {
        return this.essence.limit();
    }

    public boolean isCountMaxed() {
        return this.count == this.getLimit();
    }

    public void capCount() {
        if (this.getCount() > this.getLimit()) {
            this.count = this.getLimit();
        }
    }

    public int getEssenceColor() {
        return this.essence.color();
    }

    public String getTranslationKey() {
        return this.essence.getOrCreateTranslationKey();
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
        MutableText fillText = Text.literal(this.getCount() + " / " + this.getLimit()).formatted(Formatting.DARK_PURPLE);
        list.add(fillText);

        if (type.isAdvanced()) {
            MutableText idText = Text.literal(this.essence.id().toString()).formatted(Formatting.DARK_GRAY);
            list.add(idText);
            MutableText colorCodeText = Text.literal("#" + new ColorCode(this.essence.color())).formatted(Formatting.DARK_GRAY);
        }

        return list;
    }

    public Text toHoverableText() {
        MutableText mutableText = Text.empty().append(this.getName());
        return Texts.bracketed(mutableText);
    }

    public Optional<TooltipData> getTooltipData() {
        return this.getEssence().getTooltipData(this);
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
        boolean thisEmpty = this == EMPTY;
        boolean essenceNull = this.essence == null;
        boolean countLT0 = this.count < 0;

        return thisEmpty || essenceNull || countLT0;
    }

    public static Optional<EssenceContainer> fromNbt(RegistryWrapper.WrapperLookup registries, NbtElement nbt) {
        return CODEC.parse(registries.getOps(NbtOps.INSTANCE), nbt).resultOrPartial((error) -> {
            LOGGER.error("Tried to load invalid essence: '{}'", error);
        });
    }

    public NbtElement encode(RegistryWrapper.WrapperLookup registries, NbtElement prefix) {
        Optional<NbtElement> encoded = CODEC.encode(this, registries.getOps(NbtOps.INSTANCE), prefix).result();
        return encoded.orElseGet(NbtCompound::new);
    }

    static {
        CODEC = Codec.lazyInitialized(() ->
                RecordCodecBuilder.create((instance) ->
                        instance.group(
                        Essence.REGISTRY_CODEC.fieldOf("id").forGetter(EssenceContainer::getEssenceRegistryEntry),
                        Codecs.rangedInt(0, 100).fieldOf("count").forGetter(EssenceContainer::getCount)
                        ).apply(instance, EssenceContainer::new)
                )
        );

        OPTIONAL_PACKET_CODEC = new PacketCodec<>() {
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
                    ESSENCE_PACKET_CODEC.encode(buf, value.getEssenceRegistryEntry());
                }
            }
        };

        PACKET_CODEC = new PacketCodec<>() {
            @Override
            public EssenceContainer decode(RegistryByteBuf buf) {
                EssenceContainer essenceContainer = EssenceContainer.OPTIONAL_PACKET_CODEC.decode(buf);
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

        LIST_PACKET_CODEC = PACKET_CODEC.collect(PacketCodecs.toList());
    }
}
