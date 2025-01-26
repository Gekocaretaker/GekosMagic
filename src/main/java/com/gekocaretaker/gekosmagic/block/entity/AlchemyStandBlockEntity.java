package com.gekocaretaker.gekosmagic.block.entity;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.block.AlchemyStandBlock;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.elixir.EssenceContainer;
import com.gekocaretaker.gekosmagic.elixir.Essences;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.network.EssenceContainerPayload;
import com.gekocaretaker.gekosmagic.recipe.AlchemyRecipeRegistry;
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
import net.minecraft.registry.RegistryWrapper;
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

public class AlchemyStandBlockEntity extends LockableContainerBlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, SidedInventory {
    private static final int INPUT_SLOT_INDEX = 3;
    private static final int FUEL_SLOT_INDEX = 4;
    private static final int[] TOP_SLOTS = new int[]{3};
    private static final int[] BOTTOM_SLOTS = new int[]{0, 1, 2, 3};
    private static final int[] SIDE_SLOTS = new int[]{0, 1, 2, 4};
    public static final int BREW_TIME_PROPERTY_INDEX = 0;
    public static final int FUEL_PROPERTY_INDEX = 1;
    public static final int SELECTED_INDEX_PROPERTY_INDEX = 2;
    public static final int PROPERTY_COUNT = 2;
    private ArrayList<EssenceContainer> essences;
    private DefaultedList<ItemStack> inventory;
    int brewTime;
    int fuel;
    int inputCooldown;
    int selectedIndex = 0;
    private boolean[] slotsEmptyLastTick;
    protected final PropertyDelegate propertyDelegate;

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
                    case 0 -> retVal = AlchemyStandBlockEntity.this.brewTime;
                    case 1 -> retVal = AlchemyStandBlockEntity.this.fuel;
                    case 2 -> retVal = AlchemyStandBlockEntity.this.selectedIndex;
                    default -> retVal = 0;
                }
                return retVal;
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AlchemyStandBlockEntity.this.brewTime = value;
                    case 1 -> AlchemyStandBlockEntity.this.fuel = value;
                    case 2 -> AlchemyStandBlockEntity.this.selectedIndex = value;
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
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
        ItemStack fuelStack = blockEntity.inventory.get(FUEL_SLOT_INDEX);
        ItemStack inputStack = blockEntity.inventory.get(INPUT_SLOT_INDEX);

        fuelTick(fuelStack, world, pos, state, blockEntity);
        inputTick(inputStack, world, pos, state, blockEntity);

        if (blockEntity.selectedIndex < 0) {
            blockEntity.selectedIndex = 0;
        } else if (blockEntity.selectedIndex >= blockEntity.essences.size()) {
            blockEntity.selectedIndex = blockEntity.essences.size() - 1;
        }

        if (!blockEntity.essences.isEmpty()) {
            boolean craftingPossible = canCraft(Gekosmagic.alchemyRecipeRegistry, blockEntity.essences.get(blockEntity.selectedIndex), blockEntity.inventory, state);
            boolean brewing = blockEntity.brewTime > 0;
            if (brewing) {
                --blockEntity.brewTime;
                boolean finishedBrewing = blockEntity.brewTime == 0;
                if (craftingPossible && finishedBrewing) {
                    craft(world, pos, blockEntity.essences, blockEntity.selectedIndex, blockEntity.inventory);
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

    private static void fuelTick(ItemStack itemStack, World world, BlockPos pos, BlockState state, AlchemyStandBlockEntity blockEntity) {
        if (blockEntity.fuel <= 0 && itemStack.isOf(Items.BLAZE_POWDER)) {
            blockEntity.fuel = 20;
            itemStack.decrement(1);
            markDirty(world, pos, state);
        }
    }

    private static void inputTick(ItemStack inputStack, World world, BlockPos pos, BlockState state, AlchemyStandBlockEntity blockEntity) {
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

            Pair<Essence, Boolean> ep = Essences.itemIsEssence(inputStack.getItem());
            Essence essence = ep.getLeft();
            if (!essenceContainerExists && ep.getRight()) {
                blockEntity.essences.add(new EssenceContainer(essence, 1));
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

    private static boolean canCraft(AlchemyRecipeRegistry alchemyRecipeRegistry, EssenceContainer selectedEssenceContainer, DefaultedList<ItemStack> slots, BlockState state) {
        if (selectedEssenceContainer.isOf(Essences.AIR)) {
            return false;
        } else if (!alchemyRecipeRegistry.isValidIngredient(selectedEssenceContainer.getEssence())) {
            return false;
        } else if (!state.get(AlchemyStandBlock.POWERED)) {
            return false;
        } else {
            for (int i = 0; i < 3; ++i) {
                ItemStack itemStack = slots.get(i);
                if (!itemStack.isEmpty() && alchemyRecipeRegistry.hasRecipe(itemStack, selectedEssenceContainer.getEssence())) {
                    return true;
                }
            }

            return false;
        }
    }

    private static void craft(World world, BlockPos pos, ArrayList<EssenceContainer> essences, int selectedIndex, DefaultedList<ItemStack> slots) {
        AlchemyRecipeRegistry alchemyRecipeRegistry = Gekosmagic.alchemyRecipeRegistry;
        EssenceContainer essenceContainer = essences.get(selectedIndex);

        for (int i = 0; i < 3; i++) {
            slots.set(i, alchemyRecipeRegistry.craft(essenceContainer.getEssence(), slots.get(i)));
        }

        essenceContainer.decrement(1);

        if (!essences.isEmpty()) {
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, pos)) {
                ServerPlayNetworking.send(player, new EssenceContainerPayload(essences, pos));
            }
        }

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
            return Essences.itemIsEssence(stack.getItem()).getRight();
        } else if (slot == FUEL_SLOT_INDEX) {
            return stack.isOf(Items.BLAZE_POWDER);
        } else {
            return (stack.isOf(ModItems.ELIXIR) ||
                    stack.isOf(ModItems.SPLASH_ELIXIR) ||
                    stack.isOf(ModItems.LINGERING_ELIXIR) ||
                    stack.isOf(ModItems.BUTTERED_ELIXIR) ||
                    stack.isOf(ModItems.CLEAR_ELIXIR) ||
                    stack.isOf(ModItems.UNINTERESTING_ELIXIR) ||
                    stack.isOf(ModItems.BLAND_ELIXIR) ||
                    stack.isOf(ModItems.DIFFUSING_ELIXIR) ||
                    stack.isOf(ModItems.GLASS_PHIAL)) && ((this.getStack(slot).getCount() < this.getStack(slot).getMaxCount()) || this.getStack(slot).isEmpty());
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
}
