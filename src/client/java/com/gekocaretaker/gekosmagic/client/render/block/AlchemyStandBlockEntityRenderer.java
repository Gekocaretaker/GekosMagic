package com.gekocaretaker.gekosmagic.client.render.block;

import com.gekocaretaker.gekosmagic.block.entity.AlchemyStandBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.joml.AxisAngle4f;
import org.joml.Math;
import org.joml.Quaternionf;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class AlchemyStandBlockEntityRenderer implements BlockEntityRenderer<AlchemyStandBlockEntity> {
    public AlchemyStandBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(AlchemyStandBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        int light1 = WorldRenderer.getLightmapCoordinates(Objects.requireNonNull(entity.getWorld()), entity.getPos());
        ItemStack itemStack = entity.getStack(0);
        ItemStack itemStack1 = entity.getStack(1);
        ItemStack itemStack2 = entity.getStack(2);

        if (!itemStack.isEmpty()) {
            matrices.push();
            matrices.translate(0.86f, 0.23f, 0.5f);
            matrices.scale(1.15f, 1.15f, 1.15f);
            matrices.multiply(new Quaternionf(new AxisAngle4f(Math.toRadians(180), 0, 1, 0)));
            MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformationMode.GROUND, light1, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
            matrices.pop();
        }

        if (!itemStack1.isEmpty()) {
            matrices.push();
            matrices.translate(0.32f, 0.23f, 0f);
            matrices.scale(1.15f, 1.15f, 1.15f);
            matrices.multiply(new Quaternionf(new AxisAngle4f(Math.toRadians(315), 0, 1, 0)));
            MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack1, ModelTransformationMode.GROUND, light1, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
            matrices.pop();
        }

        if (!itemStack2.isEmpty()) {
            matrices.push();
            matrices.translate(-0.39f, 0.23f, 0.705f);
            matrices.scale(1.15f, 1.15f, 1.15f);
            matrices.multiply(new Quaternionf(new AxisAngle4f(Math.toRadians(45), 0, 1, 0)));
            MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack2, ModelTransformationMode.GROUND, light1, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
            matrices.pop();
        }
    }
}
