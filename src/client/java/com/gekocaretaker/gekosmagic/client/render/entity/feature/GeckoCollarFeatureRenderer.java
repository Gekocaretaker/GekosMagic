package com.gekocaretaker.gekosmagic.client.render.entity.feature;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.client.render.entity.model.GeckoEntityModel;
import com.gekocaretaker.gekosmagic.entity.passive.GeckoEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GeckoCollarFeatureRenderer extends FeatureRenderer<GeckoEntity, GeckoEntityModel<GeckoEntity>> {
    private static final Identifier SKIN = Gekosmagic.identify("textures/entity/gecko/gecko_collar.png");
    private final GeckoEntityModel<GeckoEntity> model;

    public GeckoCollarFeatureRenderer(FeatureRendererContext<GeckoEntity, GeckoEntityModel<GeckoEntity>> context, EntityModelLoader loader) {
        super(context);
        this.model = new GeckoEntityModel<>(loader.getModelPart(GeckoEntityModel.GECKO_COLLAR));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, GeckoEntity entity, float f, float g, float h, float j, float k, float l) {
        if (entity.isTamed()) {
            int m = entity.getCollarColor().getEntityColor();
            render(this.getContextModel(), this.model, SKIN, matrices, vertexConsumers, light, entity, f, g, j, k, l, h, m);
        }
    }
}
