package com.gekocaretaker.gekosmagic.mixin;

import com.gekocaretaker.gekosmagic.effect.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(targets = "net.minecraft.entity.mob.PhantomEntity$FindTargetGoal")
public abstract class PhantomEntityFindTargetGoalMixin extends Goal {
    @Shadow private int delay;

    @Shadow(aliases = "field_7319")
    private PhantomEntity field_7319;

    @Shadow @Final private TargetPredicate PLAYERS_IN_RANGE_PREDICATE;

    @Inject(method = "canStart()Z", at = @At("HEAD"), cancellable = true)
    private void gekosmagic$canStart(CallbackInfoReturnable<Boolean> cir) {
        if (this.delay > 0) {
            --this.delay;
            cir.setReturnValue(false);
        } else {
            this.delay = MathHelper.ceilDiv(60, 2);
            ServerWorld serverWorld = (ServerWorld) field_7319.getWorld();
            List<PlayerEntity> list = serverWorld.getPlayers(this.PLAYERS_IN_RANGE_PREDICATE, field_7319, field_7319.getBoundingBox().expand(16.0, 64.0, 16.0));
            List<PlayerEntity> filteredList = new ArrayList<>();
            list.forEach(player -> {
                if (!player.hasStatusEffect(ModEffects.ABSOLUTELY_NOT)) {
                    filteredList.add(player);
                }
            });
            if (!filteredList.isEmpty()) {
                filteredList.sort(Comparator.comparing(Entity::getY).reversed());
                Iterator<PlayerEntity> iterator = filteredList.iterator();

                while (iterator.hasNext()) {
                    PlayerEntity player = iterator.next();
                    if (field_7319.testTargetPredicate(serverWorld, player, TargetPredicate.DEFAULT)) {
                        field_7319.setTarget(player);
                        cir.setReturnValue(true);
                    }
                }
            }

            cir.setReturnValue(false);
        }
        cir.cancel();
    }
}
