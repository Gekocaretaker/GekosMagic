package com.gekocaretaker.gekosmagic.component.type;

import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;
import net.minecraft.util.math.ColorHelper;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public record ElixirContentsComponent(Optional<RegistryEntry<Elixir>> elixir, Optional<Integer> customColor, List<StatusEffectInstance> customEffects) {
    public static final ElixirContentsComponent DEFAULT = new ElixirContentsComponent(Optional.empty(), Optional.empty(), List.of());
    private static final Text NONE_TEXT = Text.translatable("effect.none").formatted(Formatting.GRAY);
    private static final Text MYSTERY_TEXT = Text.translatable("effect.gekosmagic.uninteresting", Formatting.GRAY);
    public static final int EFFECTLESS_COLOR = -13083194;
    private static final Codec<ElixirContentsComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Elixir.CODEC.optionalFieldOf("elixir").forGetter(ElixirContentsComponent::elixir), Codec.INT.optionalFieldOf("custom_color").forGetter(ElixirContentsComponent::customColor), StatusEffectInstance.CODEC.listOf().optionalFieldOf("custom_effects", List.of()).forGetter(ElixirContentsComponent::customEffects)).apply(instance, ElixirContentsComponent::new);
    });
    public static final Codec<ElixirContentsComponent> CODEC = Codec.withAlternative(BASE_CODEC, Elixir.CODEC, ElixirContentsComponent::new);
    public static final PacketCodec<RegistryByteBuf, ElixirContentsComponent> PACKET_CODEC = PacketCodec.tuple(Elixir.PACKET_CODEC.collect(PacketCodecs::optional), ElixirContentsComponent::elixir, PacketCodecs.INTEGER.collect(PacketCodecs::optional), ElixirContentsComponent::customColor, StatusEffectInstance.PACKET_CODEC.collect(PacketCodecs.toList()), ElixirContentsComponent::customEffects, ElixirContentsComponent::new);

    public ElixirContentsComponent(RegistryEntry<Elixir> elixir) {
        this(Optional.of(elixir), Optional.empty(), List.of());
    }

    public static ItemStack createStack(Item item, RegistryEntry<Elixir> elixir) {
        ItemStack itemStack = new ItemStack(item);
        itemStack.set(ModDataComponentTypes.ELIXIR_CONTENTS, new ElixirContentsComponent(elixir));
        return itemStack;
    }

    public static ItemStack createStack(Item item, ElixirContentsComponent contents) {
        ItemStack itemStack = new ItemStack(item);
        itemStack.set(ModDataComponentTypes.ELIXIR_CONTENTS, contents);
        return itemStack;
    }

    public static PotionContentsComponent convertToPotionContentsComponent(ElixirContentsComponent contents) {
        List<StatusEffectInstance> effects = new ArrayList<>();
        contents.forEachEffect(effects::add);
        PotionContentsComponent potionContentsComponent = new PotionContentsComponent(Optional.empty(), Optional.empty(), effects);
        return potionContentsComponent;
    }

    public static ItemStack createPotionContentsStack(Item item, int count, ElixirContentsComponent contents) {
        ItemStack stack = new ItemStack(item, count);
        stack.set(DataComponentTypes.POTION_CONTENTS, convertToPotionContentsComponent(contents));
        return stack;
    }

    public static ItemStack createPotionContentsStack(Item item, int count, RegistryEntry<Elixir> elixir) {
        ElixirContentsComponent elixirContentsComponent = new ElixirContentsComponent(elixir);
        return createPotionContentsStack(item, count, elixirContentsComponent);
    }

    public boolean matches(RegistryEntry<Elixir> elixir) {
        return this.elixir.isPresent() && this.elixir.get().matches(elixir) && this.customEffects.isEmpty();
    }

    public Iterable<StatusEffectInstance> getEffects() {
        if (this.elixir.isEmpty()) {
            return this.customEffects;
        } else {
            return this.customEffects.isEmpty() ? this.elixir.get().value().getEffects() : Iterables.concat(this.elixir.get().value().getEffects(), this.customEffects);
        }
    }

    public void forEachEffect(Consumer<StatusEffectInstance> effectConsumer) {
        Iterator iterator;
        StatusEffectInstance statusEffectInstance;
        if (this.elixir.isPresent()) {
            iterator = this.elixir.get().value().getEffects().iterator();
            while(iterator.hasNext()) {
                statusEffectInstance = (StatusEffectInstance) iterator.next();
                effectConsumer.accept(new StatusEffectInstance(statusEffectInstance));
            }
        }
        iterator = this.customEffects.iterator();
        while(iterator.hasNext()) {
            statusEffectInstance = (StatusEffectInstance) iterator.next();
            effectConsumer.accept(new StatusEffectInstance(statusEffectInstance));
        }
    }

    public ElixirContentsComponent with(RegistryEntry<Elixir> elixir) {
        return new ElixirContentsComponent(Optional.of(elixir), this.customColor, this.customEffects);
    }

    public ElixirContentsComponent with(StatusEffectInstance... customEffects) {
        List<StatusEffectInstance> newCustomEffects = new ArrayList<>();
        newCustomEffects.addAll(this.customEffects);
        newCustomEffects.addAll(Arrays.stream(customEffects).toList());
        return new ElixirContentsComponent(this.elixir, this.customColor, newCustomEffects);
    }

    public int getColor() {
        return this.customColor.isPresent() ? this.customColor.get() : getColor(this.getEffects());
    }

    public static int getColor(RegistryEntry<Elixir> elixir) {
        return getColor(elixir.value().getEffects());
    }

    public static int getColor(Iterable<StatusEffectInstance> effects) {
        return  mixColors(effects).orElse(EFFECTLESS_COLOR);
    }

    public static OptionalInt mixColors(Iterable<StatusEffectInstance> effects) {
        int red = 0;
        int green = 0;
        int blue = 0;
        int effectCount = 0;
        Iterator iterator = effects.iterator();

        while (iterator.hasNext()) {
            StatusEffectInstance statusEffectInstance = (StatusEffectInstance) iterator.next();
            if (statusEffectInstance.shouldShowParticles()) {
                int color = statusEffectInstance.getEffectType().value().getColor();
                int amp = statusEffectInstance.getAmplifier() + 1;
                red += amp * ColorHelper.Argb.getRed(color);
                green += amp * ColorHelper.Argb.getGreen(color);
                blue += amp * ColorHelper.Argb.getBlue(color);
                effectCount += amp;
            }
        }
        if (effectCount == 0) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(ColorHelper.Argb.getArgb(red / effectCount, green / effectCount, blue / effectCount));
        }
    }

    public boolean hasEffects() {
        if (!this.customEffects.isEmpty()) {
            return true;
        } else {
            return this.elixir.isPresent() && !this.elixir.get().value().getEffects().isEmpty();
        }
    }

    public List<StatusEffectInstance> customEffects() {
        return Lists.transform(this.customEffects, StatusEffectInstance::new);
    }

    public void buildTooltip(Consumer<Text> textConsumer, float durationMultiplier, float tickRate) {
        buildTooltip(this.getEffects(), textConsumer, durationMultiplier, tickRate);
    }

    public static void buildTooltip(Iterable<StatusEffectInstance> effects, Consumer<Text> textConsumer, float durationMultiplyer, float tickRate) {
        List<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> list = Lists.newArrayList();
        boolean bl = true;

        Iterator iterator;
        MutableText mutableText;
        RegistryEntry registryEntry;
        for (iterator = effects.iterator(); iterator.hasNext(); textConsumer.accept(mutableText.formatted(((StatusEffect) registryEntry.value()).getCategory().getFormatting()))) {
            StatusEffectInstance statusEffectInstance = (StatusEffectInstance) iterator.next();
            bl = false;
            mutableText = Text.translatable(statusEffectInstance.getTranslationKey());
            registryEntry = statusEffectInstance.getEffectType();
            ((StatusEffect) registryEntry.value()).forEachAttributeModifier(statusEffectInstance.getAmplifier(), (attribute, modifier) -> {
                list.add(new Pair(attribute, modifier));
            });
            if (statusEffectInstance.getAmplifier() > 0) {
                mutableText = Text.translatable("potion.withAmplifier", new Object[]{mutableText, Text.translatable("potion.potency." + statusEffectInstance.getAmplifier())});
            }
            if (!statusEffectInstance.isDurationBelow(20)) {
                mutableText = Text.translatable("potion.withDuration", new Object[]{mutableText, StatusEffectUtil.getDurationText(statusEffectInstance, durationMultiplyer, tickRate)});
            }
        }

        if (bl) {
            textConsumer.accept(NONE_TEXT);
        }

        if (!list.isEmpty()) {
            textConsumer.accept(ScreenTexts.EMPTY);
            textConsumer.accept(Text.translatable("potion.whenDrank").formatted(Formatting.DARK_PURPLE));
            iterator = list.iterator();

            while (iterator.hasNext()) {
                Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier> pair = (Pair) iterator.next();
                EntityAttributeModifier entityAttributeModifier = pair.getRight();
                double d = entityAttributeModifier.value();
                double e;
                if (entityAttributeModifier.operation() != EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE && entityAttributeModifier.operation() != EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                    e = entityAttributeModifier.value();
                } else {
                    e = entityAttributeModifier.value() * 100.0;
                }

                if (d > 0.0) {
                    textConsumer.accept(Text.translatable("attribute.modifier.plus." + entityAttributeModifier.operation().getId(), new Object[]{AttributeModifiersComponent.DECIMAL_FORMAT.format(e), Text.translatable(pair.getLeft().value().getTranslationKey())}).formatted(Formatting.BLUE));
                } else if (d < 0.0) {
                    e *= -1.0;
                    textConsumer.accept(Text.translatable("attribute.modifier.take." + entityAttributeModifier.operation().getId(), new Object[]{AttributeModifiersComponent.DECIMAL_FORMAT.format(e), Text.translatable(pair.getLeft().value().getTranslationKey())}).formatted(Formatting.RED));
                }
            }
        }
    }

    public void buildNoneTooltip(Consumer<Text> textConsumer, float durationMultiplyer, float tickRate) {
        buildNoneTooltip(this.getEffects(), textConsumer, durationMultiplyer, tickRate);
    }

    public static void buildNoneTooltip(Iterable<StatusEffectInstance> effects, Consumer<Text> textConsumer, float durationMultiplyer, float tickRate) {
        textConsumer.accept(NONE_TEXT);
    }

    public void buildMysteryTooltip(Consumer<Text> textConsumer, float durationMultiplyer, float tickRate) {
        buildMysteryTooltip(this.getEffects(), textConsumer, durationMultiplyer, tickRate);
    }

    public static void buildMysteryTooltip(Iterable<StatusEffectInstance> effects, Consumer<Text> textConsumer, float durationMultiplyer, float tickRate) {
        textConsumer.accept(MYSTERY_TEXT);
    }

    public Optional<RegistryEntry<Elixir>> elixir() {
        return this.elixir;
    }

    public Optional<Integer> customColor() {
        return this.customColor;
    }
}
