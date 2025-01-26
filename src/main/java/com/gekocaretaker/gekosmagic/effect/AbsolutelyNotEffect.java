package com.gekocaretaker.gekosmagic.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class AbsolutelyNotEffect extends StatusEffect {
    protected AbsolutelyNotEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xb5d0d2);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        return super.applyUpdateEffect(entity, amplifier);
    }
}
