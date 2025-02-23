package com.gekocaretaker.gekosmagic.client.color;

import com.gekocaretaker.gekosmagic.block.ModBlocks;
import com.gekocaretaker.gekosmagic.block.entity.AlchemyStandBlockEntity;
import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.item.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.util.math.ColorHelper;

@Environment(EnvType.CLIENT)
public class ModColorProviders {
    public static void init() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex > 0 ? -1 : ColorHelper.Argb.fullAlpha(stack.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT).getColor()),
                ModItems.ELIXIR, ModItems.SPLASH_ELIXIR, ModItems.LINGERING_ELIXIR, ModItems.BUTTERED_ELIXIR, ModItems.UNINTERESTING_ELIXIR, ModItems.DIFFUSING_ELIXIR);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex > 0 ? -1 : ElixirContentsComponent.EFFECTLESS_COLOR,
                ModItems.CLEAR_ELIXIR, ModItems.BLAND_ELIXIR);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            AlchemyStandBlockEntity blockEntity = (AlchemyStandBlockEntity) world.getBlockEntity(pos);
            if (blockEntity != null) {
                if (tintIndex == 1) {
                    return ColorHelper.Argb.fullAlpha(blockEntity.getStack(0).getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT).getColor());
                } else if (tintIndex == 2) {
                    return ColorHelper.Argb.fullAlpha(blockEntity.getStack(1).getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT).getColor());
                } else if (tintIndex == 3) {
                    return ColorHelper.Argb.fullAlpha(blockEntity.getStack(2).getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT).getColor());
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }, ModBlocks.ALCHEMY_STAND_BLOCK);
    }

    private ModColorProviders() {}
}
