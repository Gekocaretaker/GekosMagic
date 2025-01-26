package com.gekocaretaker.gekosmagic.entity.projectile.thrown;

import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Elixirs;
import com.gekocaretaker.gekosmagic.entity.ModEntities;
import com.gekocaretaker.gekosmagic.item.ModItems;
import it.unimi.dsi.fastutil.doubles.DoubleDoubleImmutablePair;
import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class ElixirEntity extends ThrownItemEntity implements FlyingItemEntity {
    public static final Predicate<LivingEntity> AFFECTED_BY_WATER = (entity) -> {
        return entity.hurtByWater() || entity.isOnFire();
    };

    public ElixirEntity(EntityType<? extends ElixirEntity> entityType, World world) {
        super(entityType, world);
    }

    public ElixirEntity(World world, LivingEntity owner) {
        super(ModEntities.ELIXIR, owner, world);
    }

    public ElixirEntity(World world, double x, double y, double z) {
        super(ModEntities.ELIXIR, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ModItems.SPLASH_ELIXIR;
    }

    @Override
    protected double getGravity() {
        return 0.05;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            ItemStack itemStack = this.getStack();
            Direction direction = blockHitResult.getSide();
            BlockPos blockPos = blockHitResult.getBlockPos();
            BlockPos blockPos1 = blockPos.offset(direction);
            ElixirContentsComponent elixirContentsComponent = itemStack.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT);
            if (elixirContentsComponent.matches(Elixirs.WATER)) {
                this.extinguishFire(blockPos1);
                this.extinguishFire(blockPos1.offset(direction.getOpposite()));
                Iterator iterator = Direction.Type.HORIZONTAL.iterator();

                while (iterator.hasNext()) {
                    Direction direction1 = (Direction) iterator.next();
                    this.extinguishFire(blockPos1.offset(direction1));
                }
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            ItemStack itemStack = this.getStack();
            ElixirContentsComponent elixirContentsComponent = itemStack.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT);
            if (elixirContentsComponent.matches(Elixirs.WATER)) {
                this.applyWater();
            } else if (elixirContentsComponent.hasEffects()) {
                if (this.isLingering()) {
                    this.applyLingeringElixir(elixirContentsComponent);
                } else {
                    this.applySplashElixir(elixirContentsComponent.getEffects(), hitResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) hitResult).getEntity() : null);
                }
            }

            int i = elixirContentsComponent.elixir().isPresent() && elixirContentsComponent.elixir().get().value().hasInstantEffect() ? 2007 : 2002;
            this.getWorld().syncWorldEvent(i, this.getBlockPos(), elixirContentsComponent.getColor());
            this.discard();
        }
    }

    private void applyWater() {
        Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
        List<LivingEntity> list = this.getWorld().getEntitiesByClass(LivingEntity.class, box, AFFECTED_BY_WATER);
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            LivingEntity livingEntity = (LivingEntity) iterator.next();
            double d = this.squaredDistanceTo(livingEntity);
            if (d < 16.0) {
                if (livingEntity.hurtByWater()) {
                    livingEntity.damage(this.getDamageSources().indirectMagic(this, this.getOwner()), 1.0F);
                }

                if (livingEntity.isOnFire() && livingEntity.isAlive()) {
                    livingEntity.extinguishWithSound();
                }
            }
        }

        List<AxolotlEntity> list1 = this.getWorld().getNonSpectatingEntities(AxolotlEntity.class, box);
        Iterator iterator1 = list1.iterator();

        while (iterator1.hasNext()) {
            AxolotlEntity axolotlEntity = (AxolotlEntity) iterator1.next();
            axolotlEntity.hydrateFromPotion();
        }
    }

    private void applySplashElixir(Iterable<StatusEffectInstance> effects, @Nullable Entity entity) {
        Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
        List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
        if (!list.isEmpty()) {
            Entity entity1 = this.getEffectCause();
            Iterator iterator = list.iterator();

            while (true) {
                LivingEntity livingEntity;
                double d;
                do {
                    do {
                        if (!iterator.hasNext()) {
                            return;
                        }

                        livingEntity = (LivingEntity) iterator.next();
                    } while (!livingEntity.isAffectedBySplashPotions());

                    d = this.squaredDistanceTo(livingEntity);
                } while (!(d < 16.0));

                double e;
                if (livingEntity == entity) {
                    e = 1.0;
                } else {
                    e = 1.0 - Math.sqrt(d) / 4.0;
                }

                Iterator iterator1 = effects.iterator();

                while (iterator1.hasNext()) {
                    StatusEffectInstance statusEffectInstance = (StatusEffectInstance) iterator1.next();
                    RegistryEntry<StatusEffect> registryEntry = statusEffectInstance.getEffectType();
                    if (registryEntry.value().isInstant()) {
                        registryEntry.value().applyInstantEffect(this, this.getOwner(), livingEntity, statusEffectInstance.getAmplifier(), e);
                    } else {
                        int i = statusEffectInstance.mapDuration((duration) -> {
                            return (int) (e * (double) duration + 0.5);
                        });
                        StatusEffectInstance statusEffectInstance1 = new StatusEffectInstance(registryEntry, i, statusEffectInstance.getAmplifier(), statusEffectInstance.isAmbient(), statusEffectInstance.shouldShowParticles());
                        if (!statusEffectInstance1.isDurationBelow(20)) {
                            livingEntity.addStatusEffect(statusEffectInstance1, entity1);
                        }
                    }
                }
            }
        }
    }

    private void applyLingeringElixir(ElixirContentsComponent elixir) {
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity livingEntity) {
            areaEffectCloudEntity.setOwner(livingEntity);
        }

        areaEffectCloudEntity.setRadius(3.0F);
        areaEffectCloudEntity.setRadiusOnUse(-0.5F);
        areaEffectCloudEntity.setWaitTime(10);
        areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());

        Iterable<StatusEffectInstance> iterable = elixir.getEffects();
        iterable.forEach(areaEffectCloudEntity::addEffect);
        this.getWorld().spawnEntity(areaEffectCloudEntity);
    }

    private boolean isLingering() {
        return this.getStack().isOf(ModItems.LINGERING_ELIXIR);
    }

    private void extinguishFire(BlockPos pos) {
        BlockState blockState = this.getWorld().getBlockState(pos);
        if (blockState.isIn(BlockTags.FIRE)) {
            this.getWorld().breakBlock(pos, false, this);
        } else if (AbstractCandleBlock.isLitCandle(blockState)) {
            AbstractCandleBlock.extinguish((PlayerEntity) null, blockState, this.getWorld(), pos);
        } else if (CampfireBlock.isLitCampfire(blockState)) {
            this.getWorld().syncWorldEvent((PlayerEntity) null, 1009, pos, 0);
            CampfireBlock.extinguish(this.getOwner(), this.getWorld(), pos, blockState);
            this.getWorld().setBlockState(pos, blockState.with(CampfireBlock.LIT, false));
        }
    }

    @Override
    public DoubleDoubleImmutablePair getKnockback(LivingEntity target, DamageSource source) {
        double d = target.getPos().x - this.getPos().x;
        double e = target.getPos().z - this.getPos().z;
        return DoubleDoubleImmutablePair.of(d, e);
    }
}
