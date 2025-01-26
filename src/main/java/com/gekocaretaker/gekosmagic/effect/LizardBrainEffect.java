package com.gekocaretaker.gekosmagic.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class LizardBrainEffect extends StatusEffect {
    protected LizardBrainEffect() {
        super(StatusEffectCategory.HARMFUL, 0x008b8b);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).setSwimming(true);
        }

        return super.applyUpdateEffect(entity, amplifier);
    }
}
