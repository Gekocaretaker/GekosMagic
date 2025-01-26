package com.gekocaretaker.gekosmagic.predicate.entity;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.entity.passive.GeckoEntity;
import com.gekocaretaker.gekosmagic.entity.passive.GeckoVariant;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import net.minecraft.predicate.entity.EntitySubPredicateTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.Optional;

public class ModEntitySubPredicateTypes {
    public static final EntitySubPredicateTypes.DynamicVariantType<GeckoVariant> GECKO = register("gecko", EntitySubPredicateTypes.DynamicVariantType.create(ModRegistryKeys.GECKO_VARIANT, (entity) -> {
        Optional variant;
        if (entity instanceof GeckoEntity geckoEntity) {
            variant = Optional.of(geckoEntity.getVariant());
        } else {
            variant = Optional.empty();
        }

        return variant;
    }));

    private static <V> EntitySubPredicateTypes.DynamicVariantType<V> register(String id, EntitySubPredicateTypes.DynamicVariantType<V> type) {
        Registry.register(Registries.ENTITY_SUB_PREDICATE_TYPE, Gekosmagic.identify(id), type.codec);
        return type;
    }

    public static void init() {}

    private ModEntitySubPredicateTypes() {}
}
