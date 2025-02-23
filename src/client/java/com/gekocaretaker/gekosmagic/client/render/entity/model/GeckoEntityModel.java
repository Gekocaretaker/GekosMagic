package com.gekocaretaker.gekosmagic.client.render.entity.model;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.client.render.entity.animation.GeckoEntityAnimations;
import com.gekocaretaker.gekosmagic.entity.passive.GeckoEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class GeckoEntityModel<T extends GeckoEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer GECKO = new EntityModelLayer(Gekosmagic.identify("gecko"), "main");
    public static final EntityModelLayer GECKO_COLLAR = new EntityModelLayer(Gekosmagic.identify("gecko"), "collar");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart right_leg;
    private final ModelPart left_arm;
    private final ModelPart left_leg;
    private final ModelPart tail;
    private final ModelPart head;

    public GeckoEntityModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.right_arm = this.body.getChild("right_arm");
        this.right_leg = this.body.getChild("right_leg");
        this.left_arm = this.body.getChild("left_arm");
        this.left_leg = this.body.getChild("left_leg");
        this.tail = this.body.getChild("tail");
        this.head = this.body.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.5F, -5.0F, 4.0F, 3.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.5F, 1.0F));

        ModelPartData right_arm = body.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, 0.5F, -3.5F));

        ModelPartData cube_r1 = right_arm.addChild("cube_r1", ModelPartBuilder.create().uv(15, 14).cuboid(-1.0F, -1.0F, -1.5F, 6.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        ModelPartData right_leg = body.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, 0.5F, 3.5F));

        ModelPartData cube_r2 = right_leg.addChild("cube_r2", ModelPartBuilder.create().uv(15, 14).cuboid(-1.0F, -1.0F, -1.5F, 6.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        ModelPartData left_arm = body.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 0.5F, -3.5F));

        ModelPartData cube_r3 = left_arm.addChild("cube_r3", ModelPartBuilder.create().uv(15, 18).cuboid(-5.0F, -1.0F, -1.5F, 6.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        ModelPartData left_leg = body.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 0.5F, 3.5F));

        ModelPartData cube_r4 = left_leg.addChild("cube_r4", ModelPartBuilder.create().uv(15, 18).cuboid(-5.0F, -1.0F, -1.5F, 6.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.0F));

        ModelPartData cube_r5 = tail.addChild("cube_r5", ModelPartBuilder.create().uv(0, 14).cuboid(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(19, 0).cuboid(-2.0F, -1.75F, -5.25F, 4.0F, 3.0F, 5.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, 0.0F, -5.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        root.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(headYaw, headPitch);

        this.animateMovement(GeckoEntityAnimations.WALK, limbAngle, limbDistance, 2f, 2.5f);
        this.updateAnimation(entity.danceAnimationState, GeckoEntityAnimations.DANCE, animationProgress, 1f);
        if (entity.isInSittingPose()) {
            this.updateAnimation(entity.sitAnimationState, GeckoEntityAnimations.SIT, animationProgress, 1.0F);
        }
    }
}
