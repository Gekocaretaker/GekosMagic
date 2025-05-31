package com.gekocaretaker.gekosmagic.client.render.entity.feature;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.client.render.entity.model.GeckoEntityModel;
import com.gekocaretaker.gekosmagic.client.render.entity.state.GeckoEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GeckoCollarFeatureRenderer extends FeatureRenderer<GeckoEntityRenderState, GeckoEntityModel> {
    private static final Identifier SKIN = Gekosmagic.identify("textures/entity/gecko/gecko_collar.png");

    public GeckoCollarFeatureRenderer(FeatureRendererContext<GeckoEntityRenderState, GeckoEntityModel> context, EntityModelLoader loader) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, GeckoEntityRenderState state, float limbAngle, float limbDistance) {
        DyeColor dyeColor = state.collarColor;
        if (dyeColor != null) {
            int j = dyeColor.getEntityColor();
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(SKIN));
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, j);
        }
    }
}
