package com.gekocaretaker.gekosmagic.mixin;

import com.gekocaretaker.gekosmagic.enchantment.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends ProjectileEntity {
    protected FishingBobberEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "pullHookedEntity(Lnet/minecraft/entity/Entity;)V", at = @At(value = "HEAD"), cancellable = true)
    private void gekosmagic$pullHookedEntityInject(Entity entity, CallbackInfo ci) {
        Entity entity1 = this.getOwner();
        if (entity1 != null) {
            Vec3d vec3d = new Vec3d(entity1.getX() - this.getX(), entity1.getY() - this.getY(), entity1.getZ() - this.getZ()).multiply(0.1);
            entity.setVelocity(entity.getVelocity().add(gekosmagic$modifyVec3dWithFastReel(((PlayerEntity) entity1), vec3d)));
        }
    }

    @Unique
    private Vec3d gekosmagic$modifyVec3dWithFastReel(PlayerEntity player, Vec3d vec3d) {
        RegistryEntry<Enchantment> fast_reel = player.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(ModEnchantments.FAST_REEL);
        int level = player.getMainHandStack().getEnchantments().getLevel(fast_reel);
        if (level == 0) {
            level = player.getOffHandStack().getEnchantments().getLevel(fast_reel);
        }
        if (level != 0) {
            vec3d = vec3d.multiply(level + 1);
        }
        return vec3d;
    }
}
