package com.gekocaretaker.gekosmagic.client.render.entity;

import com.gekocaretaker.gekosmagic.client.render.entity.feature.GeckoCollarFeatureRenderer;
import com.gekocaretaker.gekosmagic.client.render.entity.model.GeckoEntityModel;
import com.gekocaretaker.gekosmagic.client.render.entity.state.GeckoEntityRenderState;
import com.gekocaretaker.gekosmagic.entity.passive.GeckoEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

// TODO: WALK & DANCE animations do not work
@Environment(EnvType.CLIENT)
public class GeckoRenderer extends MobEntityRenderer<GeckoEntity, GeckoEntityRenderState, GeckoEntityModel> {
    public GeckoRenderer(EntityRendererFactory.Context context) {
        super(context, new GeckoEntityModel(context.getPart(GeckoEntityModel.GECKO)), 0.5f);
        this.addFeature(new GeckoCollarFeatureRenderer(this, context.getModelLoader()));
    }

    @Override
    public GeckoEntityRenderState createRenderState() {
        return new GeckoEntityRenderState();
    }

    @Override
    public Identifier getTexture(GeckoEntityRenderState state) {
        return state.texture;
    }

    @Override
    public void updateRenderState(GeckoEntity livingEntity, GeckoEntityRenderState livingEntityRenderState, float f) {
        super.updateRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.inSittingPose = livingEntity.isInSittingPose();
        livingEntityRenderState.isDancing = livingEntity.isSongPlaying();
        livingEntityRenderState.texture = livingEntity.getTexture();
        livingEntityRenderState.collarColor = livingEntity.isTamed() ? livingEntity.getCollarColor() : null;
    }

    /*@Override
    public void render(GeckoEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }*/
}
