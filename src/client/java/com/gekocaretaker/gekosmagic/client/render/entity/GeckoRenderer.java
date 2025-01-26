package com.gekocaretaker.gekosmagic.client.render.entity;

import com.gekocaretaker.gekosmagic.client.render.entity.feature.GeckoCollarFeatureRenderer;
import com.gekocaretaker.gekosmagic.client.render.entity.model.GeckoEntityModel;
import com.gekocaretaker.gekosmagic.entity.passive.GeckoEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GeckoRenderer extends MobEntityRenderer<GeckoEntity, GeckoEntityModel<GeckoEntity>> {
    public GeckoRenderer(EntityRendererFactory.Context context) {
        super(context, new GeckoEntityModel<>(context.getPart(GeckoEntityModel.GECKO)), 0.5f);
        this.addFeature(new GeckoCollarFeatureRenderer(this, context.getModelLoader()));
    }

    @Override
    public Identifier getTexture(GeckoEntity entity) {
        return entity.getTexture();
    }

    @Override
    public void render(GeckoEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
