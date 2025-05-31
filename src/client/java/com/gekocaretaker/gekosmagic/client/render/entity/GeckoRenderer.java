package com.gekocaretaker.gekosmagic.client.render.entity;

import com.gekocaretaker.gekosmagic.client.render.entity.feature.GeckoCollarFeatureRenderer;
import com.gekocaretaker.gekosmagic.client.render.entity.model.GeckoEntityModel;
import com.gekocaretaker.gekosmagic.client.render.entity.state.GeckoEntityRenderState;
import com.gekocaretaker.gekosmagic.entity.passive.GeckoEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GeckoRenderer extends AgeableMobEntityRenderer<GeckoEntity, GeckoEntityRenderState, GeckoEntityModel> {
    public GeckoRenderer(EntityRendererFactory.Context context) {
        super(context, new GeckoEntityModel(context.getPart(GeckoEntityModel.GECKO)), new GeckoEntityModel(context.getPart(GeckoEntityModel.GECKO_BABY)), 0.5f);
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
        livingEntityRenderState.danceAnimationState.copyFrom(livingEntity.danceAnimationState);
    }
}
