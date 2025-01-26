package com.gekocaretaker.gekosmagic.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ButteredElixirItem extends ElixirItem {
    private static final int MAX_USE_TIME = 16;

    public ButteredElixirItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return MAX_USE_TIME;
    }
}
