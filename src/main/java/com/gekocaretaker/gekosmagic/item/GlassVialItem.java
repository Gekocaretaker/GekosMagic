package com.gekocaretaker.gekosmagic.item;

import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Elixirs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class GlassVialItem extends Item {
    public GlassVialItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = blockHitResult.getBlockPos();
                if (!world.canPlayerModifyAt(user, pos)) {
                    return ActionResult.PASS;
                }

                if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
                    world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    world.emitGameEvent(user, GameEvent.FLUID_PICKUP, pos);
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    return ActionResult.SUCCESS.withNewHandStack(this.fill(itemStack, user, ElixirContentsComponent.createStack(ModItems.ELIXIR, Elixirs.WATER)));
                }
            }
        }
        return ActionResult.PASS;
    }

    protected ItemStack fill(ItemStack stack, PlayerEntity player, ItemStack ouputStack) {
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        return ItemUsage.exchangeStack(stack, player, ouputStack);
    }
}
