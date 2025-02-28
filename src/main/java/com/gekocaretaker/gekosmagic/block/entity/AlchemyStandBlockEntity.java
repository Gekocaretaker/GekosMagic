package com.gekocaretaker.gekosmagic.block.entity;

import com.gekocaretaker.gekosmagic.block.AlchemyStandBlock;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.elixir.EssenceContainer;
import com.gekocaretaker.gekosmagic.elixir.Essences;
import com.gekocaretaker.gekosmagic.item.ElixirItem;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.network.EssenceContainerPayload;
import com.gekocaretaker.gekosmagic.recipe.*;
import com.gekocaretaker.gekosmagic.recipe.input.AlchemyRecipeInput;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.gekocaretaker.gekosmagic.screen.AlchemyStandScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class AlchemyStandBlockEntity extends LockableContainerBlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, SidedInventory {
    private static final int INPUT_SLOT_INDEX = 3;
    private static final int FUEL_SLOT_INDEX = 4;
    private static final int[] TOP_SLOTS = new int[]{3};
    private static final int[] BOTTOM_SLOTS = new int[]{0, 1, 2, 3};
    private static final int[] SIDE_SLOTS = new int[]{0, 1, 2, 4};
    public static final int BREW_TIME_PROPERTY_INDEX = 0;
    public static final int FUEL_PROPERTY_INDEX = 1;
    public static final int SELECTED_INDEX_PROPERTY_INDEX = 2;
    private ArrayList<EssenceContainer> essences;
    private DefaultedList<ItemStack> inventory;
    int brewTime;
    int fuel;
    int inputCooldown;
    int selectedIndex = 0;
    private boolean[] slotsEmptyLastTick;
    protected final PropertyDelegate propertyDelegate;
    private final ServerRecipeManager.MatchGetter<AlchemyRecipeInput, ElixirRecipe> elixirRecipeMatchGetter;
    private final ServerRecipeManager.MatchGetter<AlchemyRecipeInput, ItemAlchemyRecipe> itemAlchemyRecipeMatchGetter;
    private final ServerRecipeManager.MatchGetter<AlchemyRecipeInput, BasicAlchemyRecipe> basicAlchemyRecipeMatchGetter;
    private final ServerRecipeManager.MatchGetter<AlchemyRecipeInput, AdvancedAlchemyRecipe> advancedAlchemyRecipeMatchGetter;

    public AlchemyStandBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ALCHEMY_STAND, pos, state);
        this.inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
        this.essences = new ArrayList<>();
        this.inputCooldown = 0;
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                int retVal;
                switch (index) {
                    case BREW_TIME_PROPERTY_INDEX -> retVal = AlchemyStandBlockEntity.this.brewTime;
                    case FUEL_PROPERTY_INDEX -> retVal = AlchemyStandBlockEntity.this.fuel;
                    case SELECTED_INDEX_PROPERTY_INDEX -> retVal = AlchemyStandBlockEntity.this.selectedIndex;
                    default -> retVal = 0;
                }
                return retVal;
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case BREW_TIME_PROPERTY_INDEX -> AlchemyStandBlockEntity.this.brewTime = value;
                    case FUEL_PROPERTY_INDEX -> AlchemyStandBlockEntity.this.fuel = value;
                    case SELECTED_INDEX_PROPERTY_INDEX -> AlchemyStandBlockEntity.this.selectedIndex = value;
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
        this.elixirRecipeMatchGetter = ServerRecipeManager.createCachedMatchGetter(ModRecipeTypes.ELIXIR);
        this.itemAlchemyRecipeMatchGetter = ServerRecipeManager.createCachedMatchGetter(ModRecipeTypes.ITEM_ALCHEMY);
        this.basicAlchemyRecipeMatchGetter = ServerRecipeManager.createCachedMatchGetter(ModRecipeTypes.BASIC_ALCHEMY);
        this.advancedAlchemyRecipeMatchGetter = ServerRecipeManager.createCachedMatchGetter(ModRecipeTypes.ADVANCED_ALCHEMY);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.alchemy_stand");
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    public static void tick(World world, BlockPos pos, BlockState state, AlchemyStandBlockEntity blockEntity) {
        if (world instanceof ServerWorld serverWorld) {
            ItemStack fuelStack = blockEntity.inventory.get(FUEL_SLOT_INDEX);
            ItemStack inputStack = blockEntity.inventory.get(INPUT_SLOT_INDEX);

            fuelTick(fuelStack, serverWorld, pos, state, blockEntity);
            inputTick(inputStack, serverWorld, pos, state, blockEntity);

            if (blockEntity.selectedIndex < 0) {
                blockEntity.selectedIndex = 0;
            } else if (blockEntity.selectedIndex >= blockEntity.essences.size()) {
                blockEntity.selectedIndex = blockEntity.essences.size() - 1;
            }

            if (!blockEntity.essences.isEmpty()) {
                boolean craftingPossible = canCraft(serverWorld, blockEntity, state);
                boolean brewing = blockEntity.brewTime > 0;
                if (brewing) {
                    --blockEntity.brewTime;
                    boolean finishedBrewing = blockEntity.brewTime == 0;
                    if (craftingPossible && finishedBrewing) {
                        craft(serverWorld, blockEntity, pos, blockEntity.essences, blockEntity.inventory);
                    } else if (!craftingPossible) {
                        blockEntity.brewTime = 0;
                    }

                    markDirty(world, pos, state);
                } else if (craftingPossible && blockEntity.fuel > 0) {
                    --blockEntity.fuel;
                    blockEntity.brewTime = 400;
                    markDirty(world, pos, state);
                }
            }

            boolean[] slotsEmpty = blockEntity.getSlotsEmpty();
            if (!Arrays.equals(slotsEmpty, blockEntity.slotsEmptyLastTick)) {
                blockEntity.slotsEmptyLastTick = slotsEmpty;
                BlockState blockState = state;
                if (!(blockState.getBlock() instanceof AlchemyStandBlock)) {
                    return;
                }

                for (int i = 0; i < AlchemyStandBlock.BOTTLE_PROPERTIES.length; i++) {
                    blockState = blockState.with(AlchemyStandBlock.BOTTLE_PROPERTIES[i], slotsEmpty[i]);
                }

                world.setBlockState(pos, blockState, 2);
            }
        }
    }

    private static void fuelTick(ItemStack itemStack, ServerWorld world, BlockPos pos, BlockState state, AlchemyStandBlockEntity blockEntity) {
        if (blockEntity.fuel <= 0 && itemStack.isOf(Items.BLAZE_POWDER)) {
            blockEntity.fuel = 20;
            itemStack.decrement(1);
            markDirty(world, pos, state);
        }
    }

    private static void inputTick(ItemStack inputStack, ServerWorld world, BlockPos pos, BlockState state, AlchemyStandBlockEntity blockEntity) {
        if (blockEntity.inputCooldown == 0) {
            boolean essenceContainerExists = false;
            for (EssenceContainer essenceContainer : blockEntity.essences) {
                if (essenceContainer.isOf(inputStack) && !essenceContainer.isCountMaxed()) {
                    essenceContainerExists = true;
                    essenceContainer.increment(1);
                    inputStack.decrement(1);

                    markDirty(world, pos, state);
                    break;
                } else if (essenceContainer.isOf(inputStack) && essenceContainer.isCountMaxed()) {
                    essenceContainerExists = true;
                    break;
                }
            }

            Pair<Essence, Boolean> ep = Essences.itemIsEssence(inputStack);
            Essence essence = ep.getLeft();
            RegistryEntry<Essence> essenceRegistryEntry = ModRegistries.ESSENCE.getEntry(essence);
            if (!essenceContainerExists && ep.getRight()) {
                blockEntity.essences.add(new EssenceContainer(essenceRegistryEntry));
                inputStack.decrement(1);

                markDirty(world, pos, state);
            }

            blockEntity.essences.removeIf(EssenceContainer::isEmpty);
            if (!blockEntity.essences.isEmpty()) {
                for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, blockEntity.pos)) {
                    ServerPlayNetworking.send(player, new EssenceContainerPayload(blockEntity.essences, blockEntity.pos));
                }
            }

            blockEntity.inputCooldown = 5;
        }

        --blockEntity.inputCooldown;
    }

    private boolean[] getSlotsEmpty() {
        boolean[] booleans = new boolean[3];
        for (int i = 0; i < 3; i++) {
            if (!((ItemStack) this.inventory.get(i)).isEmpty()) {
                booleans[i] = true;
            }
        }
        return booleans;
    }

    private static boolean canCraft(ServerWorld world, AlchemyStandBlockEntity blockEntity, BlockState state) {
        EssenceContainer selectedEssenceContainer = blockEntity.essences.get(blockEntity.selectedIndex);
        DefaultedList<ItemStack> slots = blockEntity.inventory;

        Essence essence = Essences.AIR;
        if (selectedEssenceContainer.isOf(essence)) {
            return false;
        } else if (!state.get(AlchemyStandBlock.POWERED)) {
            return false;
        } else {
            for (int i = 0; i < 3; ++i) {
                ItemStack itemStack = slots.get(i);
                if (!itemStack.isEmpty()) {
                    AlchemyRecipeInput alchemyRecipeInput = new AlchemyRecipeInput(selectedEssenceContainer, itemStack);
                    Optional<RecipeEntry<ItemAlchemyRecipe>> itemRecipeEntry = blockEntity.itemAlchemyRecipeMatchGetter.getFirstMatch(alchemyRecipeInput, world);
                    if (itemRecipeEntry.isPresent() && itemRecipeEntry.get().value().matches(alchemyRecipeInput, world)) {
                        return true;
                    }
                    Optional<RecipeEntry<AdvancedAlchemyRecipe>> advancedAlchemyRecipeEntry = blockEntity.advancedAlchemyRecipeMatchGetter.getFirstMatch(alchemyRecipeInput, world);
                    if (advancedAlchemyRecipeEntry.isPresent() && advancedAlchemyRecipeEntry.get().value().matches(alchemyRecipeInput, world)) {
                        return true;
                    }
                    Optional<RecipeEntry<BasicAlchemyRecipe>> basicAlchemyRecipeEntry = blockEntity.basicAlchemyRecipeMatchGetter.getFirstMatch(alchemyRecipeInput, world);
                    if (basicAlchemyRecipeEntry.isPresent() && basicAlchemyRecipeEntry.get().value().matches(alchemyRecipeInput, world)) {
                        return true;
                    }
                    Optional<RecipeEntry<ElixirRecipe>> elixirRecipeEntry = blockEntity.elixirRecipeMatchGetter.getFirstMatch(alchemyRecipeInput, world);
                    if (elixirRecipeEntry.isPresent() && elixirRecipeEntry.get().value().matches(alchemyRecipeInput, world)) {
                        return true;
                    }
                }
                /*if (!itemStack.isEmpty() && Gekosmagic.alchemyRecipeRegistry.hasRecipe(world, itemStack, selectedEssenceContainer.getEssence())) {
                    return true;
                }*/
            }

            return false;
        }
    }

    private static void craft(ServerWorld world, AlchemyStandBlockEntity blockEntity, BlockPos pos, ArrayList<EssenceContainer> essences, DefaultedList<ItemStack> slots) {
        //AlchemyRecipeRegistry alchemyRecipeRegistry = Gekosmagic.alchemyRecipeRegistry;
        DynamicRegistryManager dynamicRegistryManager = world.getRegistryManager();
        EssenceContainer essenceContainer = blockEntity.essences.get(blockEntity.selectedIndex);

        for (int i = 0; i < 3; i++) {
            ItemStack itemStack = slots.get(i);
            if (!itemStack.isEmpty()) {
                AlchemyRecipeInput alchemyRecipeInput = new AlchemyRecipeInput(essenceContainer, itemStack);
                Optional<RecipeEntry<ItemAlchemyRecipe>> itemAlchemyRecipeEntry = blockEntity.itemAlchemyRecipeMatchGetter.getFirstMatch(alchemyRecipeInput, world);
                if (itemAlchemyRecipeEntry.isPresent() && itemAlchemyRecipeEntry.get().value().matches(alchemyRecipeInput, world)) {
                    slots.set(i, itemAlchemyRecipeEntry.get().value().craft(alchemyRecipeInput, dynamicRegistryManager));
                }
                Optional<RecipeEntry<AdvancedAlchemyRecipe>> advancedAlchemyRecipeEntry = blockEntity.advancedAlchemyRecipeMatchGetter.getFirstMatch(alchemyRecipeInput, world);
                if (advancedAlchemyRecipeEntry.isPresent() && advancedAlchemyRecipeEntry.get().value().matches(alchemyRecipeInput, world)) {
                    slots.set(i, advancedAlchemyRecipeEntry.get().value().craft(alchemyRecipeInput, dynamicRegistryManager));
                }
                Optional<RecipeEntry<BasicAlchemyRecipe>> basicAlchemyRecipeEntry = blockEntity.basicAlchemyRecipeMatchGetter.getFirstMatch(alchemyRecipeInput, world);
                if (basicAlchemyRecipeEntry.isPresent() && basicAlchemyRecipeEntry.get().value().matches(alchemyRecipeInput, world)) {
                    slots.set(i, basicAlchemyRecipeEntry.get().value().craft(alchemyRecipeInput, dynamicRegistryManager));
                }
                Optional<RecipeEntry<ElixirRecipe>> elixirRecipeEntry = blockEntity.elixirRecipeMatchGetter.getFirstMatch(alchemyRecipeInput, world);
                if (elixirRecipeEntry.isPresent() && elixirRecipeEntry.get().value().matches(alchemyRecipeInput, world)) {
                    slots.set(i, elixirRecipeEntry.get().value().craft(alchemyRecipeInput, dynamicRegistryManager));
                }
            }
            //slots.set(i, alchemyRecipeRegistry.craft(world, essenceContainer.getEssence(), slots.get(i)));
        }

        essenceContainer.decrement(1);
        if (!essences.isEmpty()) {
            for (ServerPlayerEntity player : PlayerLookup.tracking( world, pos)) {
                ServerPlayNetworking.send(player, new EssenceContainerPayload(essences, pos));
            }
        }

        //world.getBlockEntity(pos).markDirty();
        blockEntity.markDirty();
        world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 0);
        world.syncWorldEvent(1035, pos, 0);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory, registryLookup);
        NbtList nbtList = nbt.getList("Essences", 10);
        this.essences = new ArrayList<>();
        for (int i = 0; i < nbtList.size(); i++) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            EssenceContainer container = EssenceContainer.fromNbt(registryLookup, nbtCompound).orElse(EssenceContainer.EMPTY);
            if (!container.isEmpty()) {
                this.essences.add(i, EssenceContainer.fromNbt(registryLookup, nbtCompound).orElse(EssenceContainer.EMPTY));
            }
        }
        this.brewTime = nbt.getShort("BrewTime");
        this.selectedIndex = nbt.getByte("Selected");
        this.inputCooldown = nbt.getByte("InputCooldown");
        this.fuel = nbt.getByte("Fuel");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putShort("BrewTime", (short) this.brewTime);
        Inventories.writeNbt(nbt, this.inventory, registryLookup);
        NbtList nbtList = new NbtList();
        for (EssenceContainer essenceContainer : this.essences) {
            NbtCompound nbtCompound = new NbtCompound();
            if (!essenceContainer.isEmpty()) {
                nbtList.add(essenceContainer.encode(registryLookup, nbtCompound));
            }
        }
        nbt.put("Essences", nbtList);
        nbt.putByte("Fuel", (byte) this.fuel);
        nbt.putByte("Selected", (byte) this.selectedIndex);
        nbt.putByte("InputCooldown", (byte) this.inputCooldown);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == INPUT_SLOT_INDEX) {
            return Essences.itemIsEssence(stack).getRight();
        } else if (slot == FUEL_SLOT_INDEX) {
            return stack.isOf(Items.BLAZE_POWDER);
        } else {
            return stack.getItem() instanceof ElixirItem && ((this.getStack(slot).getCount() < this.getStack(slot).getMaxCount()) || this.getStack(slot).isEmpty());
        }
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.UP) {
            return TOP_SLOTS;
        } else {
            return side == Direction.DOWN ? BOTTOM_SLOTS : SIDE_SLOTS;
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == INPUT_SLOT_INDEX ? stack.isOf(ModItems.GLASS_PHIAL) : true;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.propertyDelegate.set(SELECTED_INDEX_PROPERTY_INDEX, selectedIndex);
        this.markDirty();
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new AlchemyStandScreenHandler(syncId, playerInventory, this, this, this.propertyDelegate);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
