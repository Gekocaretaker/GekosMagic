package com.gekocaretaker.gekosmagic.entity;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.entity.passive.GeckoEntity;
import com.gekocaretaker.gekosmagic.entity.projectile.thrown.ElixirEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ModEntities {
    public static final EntityType<GeckoEntity> GECKO = register(GeckoEntity::new, SpawnGroup.AMBIENT, 0.75F, 0.5F, "gecko");
    public static final EntityType<ElixirEntity> ELIXIR = register(ElixirEntity::new, SpawnGroup.MISC, 0.25F, 0.25F, "elixir");

    private static EntityType register(EntityType.EntityFactory entity, SpawnGroup group, float width, float height, String id) {
        return Registry.register(Registries.ENTITY_TYPE,
                Gekosmagic.identify(id),
                EntityType.Builder.create(entity, group).dimensions(width, height).build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Gekosmagic.identify(id)))
        );
    }

    public static void init() {
        FabricDefaultAttributeRegistry.register(GECKO, GeckoEntity.createGeckoAttributes());
    }

    private ModEntities() {}
}
