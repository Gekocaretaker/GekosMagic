package com.gekocaretaker.gekosmagic.mixin;

import com.gekocaretaker.gekosmagic.effect.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

// TODO: Absolutely Not does not work with Phantoms right now.
@Mixin(targets = "net.minecraft.entity.mob.PhantomEntity$FindTargetGoal")
public abstract class PhantomEntityFindTargetGoalMixin {
    /*@Final
    @Shadow(aliases = "field_7319")
    private PhantomEntity field_7319;

    @Shadow private int delay;

    @Shadow @Final private TargetPredicate PLAYERS_IN_RANGE_PREDICATE;

    @Inject(method = "canStart()Z", at = @At(value = "HEAD"), cancellable = true)
    private void gekosmagic$canStartInject(CallbackInfoReturnable<Boolean> cir) {
        //cir.cancel();
        if (this.delay > 0) {
            --this.delay;
            cir.setReturnValue(false);
        } else {
            this.delay = MathHelper.ceilDiv(60, 2);
            List<? extends PlayerEntity> players = field_7319.getWorld().getPlayers();
            players.forEach(player -> {
                if (field_7319.getWorld().isPlayerInRange(field_7319.getX(), field_7319.getY(), field_7319.getZ(), 16.0)) {
                    players.remove(player);
                }
            });
            List<PlayerEntity> list = new ArrayList<>();
            for (PlayerEntity player : players) {
                if (!player.hasStatusEffect(ModEffects.ABSOLUTELY_NOT)) {
                    list.add(player);
                }
            }
            if (!list.isEmpty()) {
                list.sort(Comparator.comparing(Entity::getY).reversed());
                for (PlayerEntity playerEntity : list) {
                    if (!Objects.equals(playerEntity, field_7319.getTarget())) continue;
                    field_7319.setTarget(playerEntity);
                    cir.setReturnValue(true);
                }
            } else {
                cir.setReturnValue(false);
            }
        }
        cir.cancel();
    }*/
}
