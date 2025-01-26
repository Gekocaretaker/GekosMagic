package com.gekocaretaker.gekosmagic.elixir;

import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import com.mojang.serialization.Codec;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Elixir {
    public static final Codec<RegistryEntry<Elixir>> CODEC = ModRegistries.ELIXIR.getEntryCodec();
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<Elixir>> PACKET_CODEC = PacketCodecs.registryEntry(ModRegistryKeys.ELIXIR);
    private final String typeName;
    @Nullable
    private final String baseName;
    private final List<StatusEffectInstance> effects;

    public Elixir(StatusEffectInstance... effects) {
        this("basic", "water", effects);
    }

    public Elixir(@Nullable String typeName, @Nullable String baseName, StatusEffectInstance... effects) {
        this.typeName = typeName;
        this.baseName = baseName;
        this.effects = List.of(effects);
    }

    public static Elixir ofType(String typeName, StatusEffectInstance... effects) {
        return new Elixir(typeName, "water", effects);
    }

    public static Elixir ofBase(String baseName, StatusEffectInstance... effects) {
        return new Elixir("basic", baseName, effects);
    }

    public String getType() {
        return this.typeName;
    }

    public String getBase() {
        return this.baseName;
    }

    public boolean isOfType(String type) {
        return Objects.equals(this.typeName, type);
    }

    // Should return something like this:
    // item.gekosmagic.elixir.basic.effect.none
    public static String finishTranslationKey(Optional<RegistryEntry<Elixir>> elixir, String prefix, String center) {
        String base, type;

        if (elixir.isPresent()) {
            base = elixir.get().value().baseName;
            type = elixir.get().value().typeName;

            if (base != null && type != null) {
                return prefix + type + center + base;
            } else if (base != null) {
                type = elixir.flatMap(RegistryEntry::getKey).map((key) -> {
                    return ModRegistries.ELIXIR.get(key).typeName;
                }).orElse("basic");
                return prefix + type + center + base;
            } else if (type != null) {
                base = elixir.flatMap(RegistryEntry::getKey).map((key) -> {
                    return key.getValue().getPath();
                }).orElse("water");
                return prefix + type + center + base;
            }
        }

        type = elixir.flatMap(RegistryEntry::getKey).map((key) -> {
            return ModRegistries.ELIXIR.get(key).typeName;
        }).orElse("basic");
        base = elixir.flatMap(RegistryEntry::getKey).map((key) -> {
            return key.getValue().getPath();
        }).orElse("water");
        return prefix + type + center + base;
    }

    public List<StatusEffectInstance> getEffects() {
        return this.effects;
    }

    public boolean hasInstantEffect() {
        if (!this.effects.isEmpty()) {
            Iterator iterator = this.effects.iterator();
            while (iterator.hasNext()) {
                StatusEffectInstance statusEffectInstance = (StatusEffectInstance) iterator.next();
                if (((StatusEffect) statusEffectInstance.getEffectType().value()).isInstant()) {
                    return true;
                }
            }
        }
        return false;
    }
}
