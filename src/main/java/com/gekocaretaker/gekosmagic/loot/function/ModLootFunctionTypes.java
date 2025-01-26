package com.gekocaretaker.gekosmagic.loot.function;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.mojang.serialization.MapCodec;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModLootFunctionTypes {
    public static final LootFunctionType<SetElixirLootFunction> SET_ELIXIR = register("set_elixir", SetElixirLootFunction.CODEC);

    private static <T extends LootFunction>LootFunctionType<T> register(String id, MapCodec<T> codec) {
        return (LootFunctionType<T>) Registry.register(Registries.LOOT_FUNCTION_TYPE, Gekosmagic.identify(id), new LootFunctionType<>(codec));
    }

    public static void init() {}

    private ModLootFunctionTypes() {}
}
