package com.gekocaretaker.gekosmagic.screen;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.block.entity.AlchemyStandBlockEntity;
import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.elixir.EssenceContainer;
import com.gekocaretaker.gekosmagic.elixir.Essences;
import com.gekocaretaker.gekosmagic.item.ElixirItem;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.gekocaretaker.gekosmagic.recipe.AlchemyRecipeRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Optional;

public class AlchemyStandScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    private final Slot ingredientSlot;
    private final AlchemyStandBlockEntity blockEntity;
    private Runnable inventoryChangeListener;
    private ArrayList<EssenceContainer> essenceContainers;

    public AlchemyStandScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, new SimpleInventory(5), (AlchemyStandBlockEntity) playerInventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(3));
    }

    public AlchemyStandScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, AlchemyStandBlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlerTypes.ALCHEMY_STAND, syncId);
        checkSize(inventory, 5);
        checkDataCount(propertyDelegate, 3);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.blockEntity = blockEntity;
        this.inventoryChangeListener = () -> {
        };
        this.essenceContainers = new ArrayList<>();
        AlchemyRecipeRegistry alchemyRecipeRegistry = Gekosmagic.alchemyRecipeRegistry;
        this.addSlot(new ElixirSlot(inventory, 0, 51, 51));
        this.addSlot(new ElixirSlot(inventory, 1, 74, 58));
        this.addSlot(new ElixirSlot(inventory, 2, 97, 51));
        this.ingredientSlot = this.addSlot(new IngredientSlot(alchemyRecipeRegistry, inventory, 3, 94, 17));
        this.addSlot(new FuelSlot(inventory, 4, 17, 17));
        this.addProperties(propertyDelegate);

        int i;
        for (i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack1 = slot2.getStack();
            itemStack = itemStack1.copy();
            if ((slot < 0 || slot > 2) && slot != 3 && slot != 4) {
                if (AlchemyStandScreenHandler.FuelSlot.matches(itemStack)) {
                    if (this.insertItem(itemStack1, 4, 5, false) || this.ingredientSlot.canInsert(itemStack1) && !this.insertItem(itemStack1, 3, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.ingredientSlot.canInsert(itemStack1)) {
                    if (!this.insertItem(itemStack1, 3, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (AlchemyStandScreenHandler.ElixirSlot.matches(itemStack)) {
                    if (!this.insertItem(itemStack1, 0, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slot >= 5 && slot < 32) {
                    if (!this.insertItem(itemStack1, 32, 41, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slot >= 32 && slot < 41) {
                    if (!this.insertItem(itemStack1, 5, 32, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.insertItem(itemStack1, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.insertItem(itemStack1, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }

                slot2.onQuickTransfer(itemStack1, itemStack);
            }

            if (itemStack1.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack);
        }
        return itemStack;
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id >= 0 && id < this.essenceContainers.size()) {
            this.propertyDelegate.set(2, id);
            this.sendContentUpdates();
            return true;
        } else {
            return false;
        }
    }

    public int getSelectedIndex() {
        return this.propertyDelegate.get(2);
    }

    public int getFuel() {
        return this.propertyDelegate.get(1);
    }

    public int getBrewTime() {
        return this.propertyDelegate.get(0);
    }

    public ArrayList<EssenceContainer> getEssences() {
        return this.essenceContainers;
    }

    public void setEssences(ArrayList<EssenceContainer> essences) {
        this.essenceContainers = essences;
    }

    public AlchemyStandBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    public void setInventoryChangeListener(Runnable inventoryChangeListener) {
        this.inventoryChangeListener = inventoryChangeListener;
    }

    class ElixirSlot extends Slot {
        public ElixirSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return matches(stack);
        }

        @Override
        public int getMaxItemCount(ItemStack stack) {
            return 1;
        }

        @Override
        public void onTakeItem(PlayerEntity player, ItemStack stack) {
            Optional<RegistryEntry<Elixir>> optional = stack.getOrDefault(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT).elixir();
            if (optional.isPresent() && player instanceof ServerPlayerEntity serverPlayerEntity) {
                Criteria.BREWED_POTION.trigger(serverPlayerEntity, (RegistryEntry) optional.get());
            }

            super.onTakeItem(player, stack);
        }

        @Override
        public void markDirty() {
            super.markDirty();
            AlchemyStandScreenHandler.this.inventoryChangeListener.run();
        }

        public static <T extends ElixirItem> boolean matches(ItemStack stack) {
            return stack.isOf(ModItems.ELIXIR) ||
                    stack.isOf(ModItems.SPLASH_ELIXIR) ||
                    stack.isOf(ModItems.LINGERING_ELIXIR) ||
                    stack.isOf(ModItems.BUTTERED_ELIXIR) ||
                    stack.isOf(ModItems.CLEAR_ELIXIR) ||
                    stack.isOf(ModItems.UNINTERESTING_ELIXIR) ||
                    stack.isOf(ModItems.BLAND_ELIXIR) ||
                    stack.isOf(ModItems.DIFFUSING_ELIXIR) ||
                    stack.isOf(ModItems.GLASS_PHIAL);
        }
    }

    private class IngredientSlot extends Slot {
        private final AlchemyRecipeRegistry alchemyRecipeRegistry;

        public IngredientSlot(AlchemyRecipeRegistry alchemyRecipeRegistry, Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
            this.alchemyRecipeRegistry = alchemyRecipeRegistry;
        }

        @Override
        public void markDirty() {
            super.markDirty();
            AlchemyStandScreenHandler.this.inventoryChangeListener.run();
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return this.alchemyRecipeRegistry.isElixirType(Essences.itemIsEssence(stack.getItem()).getLeft());
            //return this.alchemyRecipeRegistry.isValidIngredient(Essences.itemIsEssence(stack.getItem()).getLeft());
        }
    }

    private static class FuelSlot extends Slot {
        public FuelSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return matches(stack);
        }

        public static boolean matches(ItemStack stack) {
            return stack.isOf(Items.BLAZE_POWDER);
        }
    }
}
