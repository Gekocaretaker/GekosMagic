package com.gekocaretaker.gekosmagic.block;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

public class ModBlocks {
    public static final Block ALCHEMY_STAND_BLOCK = register("alchemy_stand", new AlchemyStandBlock(
            AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE)
                    .requiresTool().registryKey(of("alchemy_stand"))
    ), true);

    private static <T extends Block> T register(String path, T block, boolean needsItem) {
        Registry.register(Registries.BLOCK, Gekosmagic.identify(path), block);
        if (needsItem) {
            Registry.register(Registries.ITEM, Gekosmagic.identify(path), new BlockItem(block, ModItems.genericBlock(path)));
        }
        return block;
    }

    private static RegistryKey<Block> of(String path) {
        return RegistryKey.of(RegistryKeys.BLOCK, Gekosmagic.identify(path));
    }

    public static void init() {}

    private ModBlocks() {}
}
