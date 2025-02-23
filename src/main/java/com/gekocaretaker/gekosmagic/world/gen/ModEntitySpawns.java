package com.gekocaretaker.gekosmagic.world.gen;

import com.gekocaretaker.gekosmagic.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawns {
    public static void init() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE),
                SpawnGroup.AMBIENT, ModEntities.GECKO, 30, 1, 4);
    }

    private ModEntitySpawns() {}
}
