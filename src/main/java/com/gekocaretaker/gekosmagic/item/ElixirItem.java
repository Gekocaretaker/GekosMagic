package com.gekocaretaker.gekosmagic.item;

import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.elixir.Elixirs;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.consume.UseAction;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.Objects;

public class ElixirItem extends Item {
    private static final int MAX_USE_TIME = 32;

    public ElixirItem(Item.Settings settings) {
        super(settings);
    }

    public ItemStack getDefaultStack() {
        ItemStack itemStack = super.getDefaultStack();
        itemStack.set(ModDataComponentTypes.ELIXIR_CONTENTS, new ElixirContentsComponent(Elixirs.WATER));
        return itemStack;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity) playerEntity, stack);
        }
        if (world instanceof ServerWorld serverWorld) {
            ElixirContentsComponent elixirContentsComponent = (ElixirContentsComponent) stack.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT);
            elixirContentsComponent.forEachEffect((effect) -> {
                if (effect.getEffectType().value().isInstant()) {
                    effect.getEffectType().value().applyInstantEffect(serverWorld, playerEntity, playerEntity, user, effect.getAmplifier(), 1.0);
                } else {
                    user.addStatusEffect(effect);
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

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return MAX_USE_TIME;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    // TODO: Items no longer have getTranslationKey.
    /*@Override
    public String getTranslationKey(ItemStack stack) {
        return Elixir.finishTranslationKey(stack.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT).elixir(), this.getTranslationKey() + ".", ".effect.");
    }*/

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        ElixirContentsComponent elixirContentsComponent = stack.get(ModDataComponentTypes.ELIXIR_CONTENTS);
        if (elixirContentsComponent != null) {
            Objects.requireNonNull(tooltip);
            elixirContentsComponent.buildTooltip(tooltip::add, 1.0F, context.getUpdateTickRate());
        }
    }
}
