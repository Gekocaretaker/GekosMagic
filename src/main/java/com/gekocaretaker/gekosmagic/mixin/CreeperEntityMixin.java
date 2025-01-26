package com.gekocaretaker.gekosmagic.mixin;

import com.gekocaretaker.gekosmagic.effect.ModEffects;
import com.gekocaretaker.gekosmagic.entity.ai.goal.FleeEntityWithEffectGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {
    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals()V", at = @At("TAIL"))
    protected void gekosmagic$mixInitGoals(CallbackInfo ci) {
        this.goalSelector.add(3, new FleeEntityWithEffectGoal(((CreeperEntity)(Object)this), PlayerEntity.class, 6.0F, 1.0, 1.2, ModEffects.ABSOLUTELY_NOT));
    }
}
