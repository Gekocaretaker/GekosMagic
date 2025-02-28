package com.gekocaretaker.gekosmagic.block.entity;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntityTypes {
    public static final BlockEntityType<AlchemyStandBlockEntity> ALCHEMY_STAND = register("alchemy_stand", AlchemyStandBlockEntity::new, ModBlocks.ALCHEMY_STAND_BLOCK);

    private static <T extends BlockEntity> BlockEntityType<T> register(String path, FabricBlockEntityTypeBuilder.Factory<? extends T> blockEntityFactory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Gekosmagic.identify(path), FabricBlockEntityTypeBuilder.<T>create(blockEntityFactory, blocks).build());
    }

    public static void init() {}

    private ModBlockEntityTypes() {}
}
