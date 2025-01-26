package com.gekocaretaker.gekosmagic.component;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<ElixirContentsComponent> ELIXIR_CONTENTS = register("elixir_contents", (builder) -> {
        return builder.codec(ElixirContentsComponent.CODEC).packetCodec(ElixirContentsComponent.PACKET_CODEC).cache();
    });

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderUnaryOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Gekosmagic.identify(id), ((ComponentType.Builder) builderUnaryOperator.apply(ComponentType.builder())).build());
    }

    public static void init() {}

    private ModDataComponentTypes() {}
}
