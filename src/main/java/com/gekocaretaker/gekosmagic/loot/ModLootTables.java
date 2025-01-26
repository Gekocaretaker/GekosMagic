package com.gekocaretaker.gekosmagic.loot;

import com.gekocaretaker.gekosmagic.elixir.type.AwkwardElixirs;
import com.gekocaretaker.gekosmagic.elixir.type.ThickElixirs;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.loot.function.SetElixirLootFunction;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTables {
    public static void init() {
        LootTableEvents.MODIFY.register((key, builder, lootTableSource, wrapperLookup) -> {
            Identifier netherBridge = Identifier.of("minecraft", "chests/nether_bridge");
            if (lootTableSource.isBuiltin() && netherBridge.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(ModItems.DIFFUSING_ELIXIR))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
                        .apply(SetElixirLootFunction.builder(AwkwardElixirs.DECAY))
                        .rolls(UniformLootNumberProvider.create(0.0F, 2.0F));

                builder.pool(poolBuilder);
            }

            Identifier ravager = Identifier.of("minecraft", "entities/ravager");
            if (lootTableSource.isBuiltin() && ravager.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(ModItems.ELIXIR))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 1.0F)))
                        .apply(SetElixirLootFunction.builder(ThickElixirs.VULNERABILITY))
                        .rolls(UniformLootNumberProvider.create(0.0F, 1.0F))
                        .conditionally(RandomChanceLootCondition.builder(0.25F));

                builder.pool(poolBuilder);
            }
        });
    }

    private ModLootTables() {}
}
