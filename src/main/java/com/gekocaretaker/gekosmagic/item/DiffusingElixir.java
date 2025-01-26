package com.gekocaretaker.gekosmagic.item;

import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class DiffusingElixir extends ElixirItem {
    public DiffusingElixir(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity) playerEntity, stack);
        }
        if (!world.isClient) {
            ElixirContentsComponent elixirContentsComponent= stack.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT);
            elixirContentsComponent.forEachEffect((effect) -> {
                if (user.hasStatusEffect(effect.getEffectType())) {
                    StatusEffectInstance userEffect = user.getStatusEffect(effect.getEffectType());
                    if (userEffect.getAmplifier() > effect.getAmplifier()) {
                        int newAmp = userEffect.getAmplifier() - effect.getAmplifier();
                        int timeLeft = userEffect.getDuration() - effect.getDuration();
                        user.removeStatusEffect(effect.getEffectType());
                        if (timeLeft > 0) {
                            user.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), timeLeft, newAmp));
                        }
                    } else if (userEffect.getAmplifier() == effect.getAmplifier()) {
                        int timeLeft = userEffect.getDuration() - effect.getDuration();
                        user.removeStatusEffect(effect.getEffectType());
                        if (timeLeft > 0) {
                            user.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), timeLeft, effect.getAmplifier()));
                        }
                    } else if (userEffect.getAmplifier() < effect.getAmplifier()) {
                        user.removeStatusEffect(effect.getEffectType());
                    }
                }
            });
        }

        if (playerEntity != null) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            stack.decrementUnlessCreative(1, playerEntity);
        }

        if (playerEntity == null || !playerEntity.isInCreativeMode()) {
            if (stack.isEmpty()) {
                return new ItemStack(ModItems.GLASS_PHIAL);
            }
            if (playerEntity != null) {
                playerEntity.getInventory().insertStack(new ItemStack(ModItems.GLASS_PHIAL));
            }
        }
        user.emitGameEvent(GameEvent.DRINK);
        return stack;
    }
}
