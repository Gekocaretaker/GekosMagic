package com.gekocaretaker.gekosmagic.loot.function;

import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;

public class SetElixirLootFunction extends ConditionalLootFunction {
    public static final MapCodec<SetElixirLootFunction> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return addConditionsField(instance).and(Elixir.CODEC.fieldOf("id").forGetter((function) -> {
            return function.elixir;
        })).apply(instance, SetElixirLootFunction::new);
    });
    private final RegistryEntry<Elixir> elixir;

    private SetElixirLootFunction(List<LootCondition> conditions, RegistryEntry<Elixir> elixir) {
        super(conditions);
        this.elixir = elixir;
    }

    @Override
    public LootFunctionType<SetElixirLootFunction> getType() {
        return ModLootFunctionTypes.SET_ELIXIR;
    }

    @Override
    public ItemStack process(ItemStack stack, LootContext context) {
        stack.apply(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT, this.elixir, ElixirContentsComponent::with);
        return stack;
    }

    public static ConditionalLootFunction.Builder<?> builder(RegistryEntry<Elixir> elixir) {
        return builder((conditions) -> {
            return new SetElixirLootFunction(conditions, elixir);
        });
    }
}
