package com.gekocaretaker.gekosmagic.client.render.entity.model;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.client.render.entity.animation.GeckoEntityAnimations;
import com.gekocaretaker.gekosmagic.client.render.entity.state.GeckoEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BabyModelTransformer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.ModelTransformer;
import net.minecraft.util.math.MathHelper;

import java.util.Set;

@Environment(EnvType.CLIENT)
public class GeckoEntityModel extends EntityModel<GeckoEntityRenderState> {
    public static final ModelTransformer BABY_TRANSFORMER = new BabyModelTransformer(Set.of("head"));
    public static final EntityModelLayer GECKO = new EntityModelLayer(Gekosmagic.identify("gecko"), "main");
    public static final EntityModelLayer GECKO_BABY = new EntityModelLayer(Gekosmagic.identify("gecko_baby"), "main");
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
        super(root);
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

    public static TexturedModelData getBabyTexturedModelData() {
        return getTexturedModelData().transform(BABY_TRANSFORMER);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void setAngles(GeckoEntityRenderState state) {
        //this.getRootPart().traverse().forEach(ModelPart::resetTransform);
        super.setAngles(state);
        this.setHeadAngles(state.yawDegrees, state.pitch);

        this.animateWalking(GeckoEntityAnimations.WALK, state.limbFrequency, state.limbAmplitudeMultiplier, 3.0F, 2.5F);
        if (state.inSittingPose) {
            this.animate(GeckoEntityAnimations.SIT);
        }
        if (state.isDancing) {
            this.animate(state.danceAnimationState, GeckoEntityAnimations.DANCE, state.age);
        }
    }
}
