package com.gekocaretaker.gekosmagic.entity.passive;

import com.gekocaretaker.gekosmagic.entity.ModEntities;
import com.gekocaretaker.gekosmagic.entity.data.ModTrackedDataHandlerRegistry;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.sound.ModSounds;
import com.gekocaretaker.gekosmagic.util.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class GeckoEntity extends TameableEntity implements VariantHolder<RegistryEntry<GeckoVariant>> {
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState danceAnimationState = new AnimationState();
    private int danceAnimationTimeout = 0;
    private int sitAnimationTimeout = 0;

    private static final TrackedData<RegistryEntry<GeckoVariant>> GECKO_VARIANT;
    private static final TrackedData<Integer> COLLAR_COLOR;
    private int scaleShedTime = -1;
    private boolean hasFed = false;
    private static final RegistryKey<GeckoVariant> DEFAULT_VARIANT;

    private boolean songPlaying;
    @Nullable
    private BlockPos songSource;

    public GeckoEntity(EntityType<? extends GeckoEntity> entityType, World world) {
        super(entityType, world);
        this.setTamed(false, false);
    }

    public Identifier getTexture() {
        return ((GeckoVariant) this.getVariant().value()).texture();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new TameableEscapeDangerGoal(1.5, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(5, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
        this.goalSelector.add(6, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    public RegistryEntry<GeckoVariant> getVariant() {
        return (RegistryEntry) this.dataTracker.get(GECKO_VARIANT);
    }



    @Override
    public void setVariant(RegistryEntry<GeckoVariant> registryEntry) {
        this.dataTracker.set(GECKO_VARIANT, registryEntry);
    }

    public static DefaultAttributeContainer.Builder createGeckoAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasFed) {
            if (this.scaleShedTime % 40 == 0) {
                this.playSound(ModSounds.ENTITY_GECKO_EAT);
            }
            this.scaleShedTime--;
        }

        if (!this.getWorld().isClient && this.isAlive() && !this.isBaby() && this.scaleShedTime == 0) {
            this.playSound(ModSounds.ENTITY_GECKO_SCALES_DROP, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            int scaleCount = this.random.nextInt(3) + 1;
            this.dropStack(new ItemStack(this.getVariant().value().scale(), scaleCount));
            this.emitGameEvent(GameEvent.ENTITY_PLACE);
            this.hasFed = false;
            this.scaleShedTime--;
        }

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    @Override
    public void tickMovement() {
        if (this.songSource == null || !this.songSource.isWithinDistance(this.getPos(), 3.46) || !this.getWorld().getBlockState(this.songSource).isOf(Blocks.JUKEBOX)) {
            this.songPlaying = false;
            this.songSource = null;
        }

        super.tickMovement();
    }

    @Override
    public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
        this.songSource = songPosition;
        this.songPlaying = playing;
    }

    public boolean isSongPlaying() {
        return this.songPlaying;
    }

    private void setupAnimationStates() {
        if (this.danceAnimationTimeout <= 0 && this.songPlaying) {
            this.danceAnimationTimeout = 40;
            this.danceAnimationState.start(this.age);
        } else {
            this.danceAnimationTimeout--;
        }

        if (this.danceAnimationTimeout <= 0 && !this.songPlaying) {
            this.danceAnimationState.stop();
        }

        if (this.sitAnimationTimeout <= 0 && this.isSitting()) {
            this.sitAnimationTimeout = 20;
            this.sitAnimationState.startIfNotRunning(this.age);
        } else {
            this.sitAnimationTimeout--;
        }

        if (this.sitAnimationTimeout <= 0 && !this.isSitting()) {
            this.sitAnimationState.stop();
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return this.createChild(world, (GeckoEntity) entity);
    }

    @Nullable
    public GeckoEntity createChild(ServerWorld world, GeckoEntity entity) {
        GeckoEntity geckoEntity = (GeckoEntity) ModEntities.GECKO.create(world);
        if (geckoEntity != null && entity instanceof GeckoEntity geckoEntity2) {
            if (this.random.nextBoolean()) {
                geckoEntity.setVariant(this.getVariant());
            } else {
                geckoEntity.setVariant(geckoEntity2.getVariant());
            }

            if (this.isTamed()) {
                geckoEntity.setOwnerUuid(this.getOwnerUuid());
                geckoEntity.setTamed(true, true);
                if (this.random.nextBoolean()) {
                    geckoEntity.setCollarColor(this.getCollarColor());
                } else {
                    geckoEntity.setCollarColor(geckoEntity2.getCollarColor());
                }
            }
        }
        return geckoEntity;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(COLLAR_COLOR, DyeColor.RED.getId());
        builder.add(GECKO_VARIANT, ModRegistries.GECKO_VARIANT.entryOf(DEFAULT_VARIANT));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("CollarColor", (byte) this.getCollarColor().getId());
        nbt.putString("variant", ((RegistryKey) this.getVariant().getKey().orElse(DEFAULT_VARIANT)).getValue().toString());
        nbt.putInt("ScaleShedTime", this.scaleShedTime);
        nbt.putBoolean("HasFed", this.hasFed);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(nbt.getInt("CollarColor")));
        }

        if (nbt.contains("variant")) {
            Identifier variant = Identifier.of(nbt.getString("variant"));
            if (ModRegistries.GECKO_VARIANT.containsId(variant)) {
                this.setVariant(ModRegistries.GECKO_VARIANT.getEntry(variant).get());
            } else {
                this.setVariant(ModRegistries.GECKO_VARIANT.getEntry(DEFAULT_VARIANT).get());
            }
        }

        if (nbt.contains("ScaleShedTime")) {
            this.scaleShedTime = nbt.getInt("ScaleShedTime");
        }

        if (nbt.contains("HasFed")) {
            this.hasFed = nbt.getBoolean("HasFed");
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        this.setVariant(ModRegistries.GECKO_VARIANT.entryOf(GeckoVariant.getRandomSpawnable(world.getRandom())));
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_GECKO_CHIRPS;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_GECKO_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_GECKO_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.getWorld().isClient) {
                this.setSitting(false);
            }
            return super.damage(source, amount);
        }
    }

    @Override
    protected void updateAttributesForTamed() {
        if (this.isTamed()) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0);
            this.setHealth(20.0F);
        } else {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(6.0F);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        ActionResult actionResult;
        if (this.isTamed()) {
            if (this.isOwner(player)) {
                if (item instanceof DyeItem) {
                    DyeItem dyeItem = (DyeItem) item;
                    DyeColor dyeColor = dyeItem.getColor();
                    if (!this.getWorld().isClient()) {
                        this.setCollarColor(dyeColor);
                        itemStack.decrementUnlessCreative(1, player);
                        this.setPersistent();
                    }

                    return ActionResult.success(this.getWorld().isClient());
                } else if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                    if (!this.getWorld().isClient()) {
                        this.eat(player, hand, itemStack);
                        FoodComponent foodComponent = (FoodComponent) itemStack.get(DataComponentTypes.FOOD);
                        this.heal(foodComponent != null ? (float) foodComponent.nutrition() : 1.0F);
                    }

                    return ActionResult.success(this.getWorld().isClient());
                } else if (this.isSpecialFeedingItem(itemStack) && !this.hasFed) {
                    if (!this.getWorld().isClient()) {
                        this.eat(player, hand, itemStack);
                        this.hasFed = true;
                        this.scaleShedTime = 80;
                    }

                    return ActionResult.success(this.getWorld().isClient());
                }
            }

            actionResult = super.interactMob(player, hand);
            if (!actionResult.isAccepted()) {
                this.setSitting(!this.isSitting());
                return ActionResult.success(this.getWorld().isClient());
            }

            return actionResult;
        } else if (this.isBreedingItem(itemStack)) {
            if (!this.getWorld().isClient()) {
                this.eat(player, hand, itemStack);
                this.tryTame(player);
                this.setPersistent();
            }

            return ActionResult.success(this.getWorld().isClient());
        }

        actionResult = super.interactMob(player, hand);
        if (actionResult.isAccepted()) {
            this.setPersistent();
        }

        return actionResult;
    }

    private void tryTame(PlayerEntity player) {
        if (this.random.nextInt(3) == 0) {
            this.setOwner(player);
            this.navigation.stop();
            this.setTarget((LivingEntity) null);
            //this.setSitting(true);
            this.getWorld().sendEntityStatus(this, (byte) 7);
        } else {
            this.getWorld().sendEntityStatus(this, (byte) 6);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ModTags.COMMON_BERRIES);
    }

    public boolean isSpecialFeedingItem(ItemStack stack) {
        return stack.isIn(ModTags.GLOW_BERRIES);
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId((Integer) this.dataTracker.get(COLLAR_COLOR));
    }

    private void setCollarColor(DyeColor color) {
        this.dataTracker.set(COLLAR_COLOR, color.getId());
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        if (other == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(other instanceof GeckoEntity)) {
            return false;
        } else {
            GeckoEntity geckoEntity = (GeckoEntity) other;
            if (!geckoEntity.isTamed()) {
                return false;
            } else if (geckoEntity.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && geckoEntity.isInLove();
            }
        }
    }

    @Override
    protected Vec3d getLeashOffset() {
        return new Vec3d(0.0, (double) (0.4F * this.getStandingEyeHeight()), (double) (this.getWidth() * 0.2F));
    }

    static {
        COLLAR_COLOR = DataTracker.registerData(GeckoEntity.class, TrackedDataHandlerRegistry.INTEGER);
        GECKO_VARIANT = DataTracker.registerData(GeckoEntity.class, ModTrackedDataHandlerRegistry.GECKO_VARIANT);
        DEFAULT_VARIANT = GeckoVariant.TOKAY;
    }
}
