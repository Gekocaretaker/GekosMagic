package com.gekocaretaker.gekosmagic.client.render.entity.feature;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.client.render.entity.model.GeckoEntityModel;
import com.gekocaretaker.gekosmagic.client.render.entity.state.GeckoEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    private final GeckoEntityModel model;

    public GeckoCollarFeatureRenderer(FeatureRendererContext<GeckoEntityRenderState, GeckoEntityModel> context, EntityModelLoader loader) {
        super(context);
        this.model = new GeckoEntityModel(loader.getModelPart(GeckoEntityModel.GECKO_COLLAR));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, GeckoEntityRenderState state, float limbAngle, float limbDistance) {
        DyeColor dyeColor = state.collarColor;
        if (dyeColor != null) {
            int j = dyeColor.getEntityColor();
            render(this.model, SKIN, matrices, vertexConsumers, light, state, j);
        }
    }
}
