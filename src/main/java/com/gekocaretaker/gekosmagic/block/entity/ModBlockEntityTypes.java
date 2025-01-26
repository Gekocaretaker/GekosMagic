package com.gekocaretaker.gekosmagic.block.entity;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntityTypes {
    public static final BlockEntityType<AlchemyStandBlockEntity> ALCHEMY_STAND = register("alchemy_stand", BlockEntityType
            .Builder.create(AlchemyStandBlockEntity::new, ModBlocks.ALCHEMY_STAND_BLOCK).build());

    private static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Gekosmagic.identify(path), blockEntityType);
    }

    public static void init() {}

    private ModBlockEntityTypes() {}
}
