package com.gekocaretaker.gekosmagic.item;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.block.ModBlocks;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.enchantment.ModEnchantments;
import com.gekocaretaker.gekosmagic.registry.ModRegistryKeys;
import de.dafuqs.fractal.api.ItemSubGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.text.Text;

public class ModItemGroups {
    public static ItemStack FAST_REEL_ENCHANTMENT = new ItemStack(Items.ENCHANTED_BOOK);

    public static final RegistryKey<ItemGroup> ELIXIRS_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Gekosmagic.identify("elixirs"));
    public static final ItemGroup ELIXIRS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.ELIXIR))
            .entries((displayContext, entries) -> {
                displayContext.lookup().getOptionalWrapper(ModRegistryKeys.ELIXIR).ifPresent((wrapper) -> {
                    addElixirs(entries, wrapper, ModItems.ELIXIR);
                    addElixirs(entries, wrapper, ModItems.SPLASH_ELIXIR);
                    addElixirs(entries, wrapper, ModItems.LINGERING_ELIXIR);
                    addElixirs(entries, wrapper, ModItems.BUTTERED_ELIXIR);
                    addElixirs(entries, wrapper, ModItems.CLEAR_ELIXIR);
                    addElixirs(entries, wrapper, ModItems.UNINTERESTING_ELIXIR);
                    addElixirs(entries, wrapper, ModItems.BLAND_ELIXIR);
                    addElixirs(entries, wrapper, ModItems.DIFFUSING_ELIXIR);
                });

                FAST_REEL_ENCHANTMENT.addEnchantment(
                    displayContext.lookup().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(ModEnchantments.FAST_REEL), 3
                );
            })
            .displayName(Text.translatable("itemGroup.gekosmagic.elixirs"))
            .noRenderedName()
            .build();

    public static final ItemGroup ELIXIRS_GENERIC = new ItemSubGroup.Builder(ELIXIRS, Gekosmagic.identify("generic_elixirs"), Text.translatable("itemGroup.gekosmagic.elixirs.generic")).entries((displayContext, entries) -> {
        displayContext.lookup().getOptionalWrapper(ModRegistryKeys.ELIXIR).ifPresent((wrapper) -> {
            addElixirs(entries, wrapper, ModItems.ELIXIR);
        });
    }).build();
    public static final ItemGroup ELIXIRS_SPLASH = new ItemSubGroup.Builder(ELIXIRS, Gekosmagic.identify("splash_elixirs"), Text.translatable("itemGroup.gekosmagic.elixirs.splash")).entries((displayContext, entries) -> {
        displayContext.lookup().getOptionalWrapper(ModRegistryKeys.ELIXIR).ifPresent((wrapper) -> {
            addElixirs(entries, wrapper, ModItems.SPLASH_ELIXIR);
        });
    }).build();
    public static final ItemGroup ELIXIRS_LINGERING = new ItemSubGroup.Builder(ELIXIRS, Gekosmagic.identify("lingering_elixirs"), Text.translatable("itemGroup.gekosmagic.elixirs.lingering")).entries((displayContext, entries) -> {
        displayContext.lookup().getOptionalWrapper(ModRegistryKeys.ELIXIR).ifPresent((wrapper) -> {
            addElixirs(entries, wrapper, ModItems.LINGERING_ELIXIR);
        });
    }).build();
    public static final ItemGroup ELIXIRS_BUTTERED = new ItemSubGroup.Builder(ELIXIRS, Gekosmagic.identify("buttered_elixirs"), Text.translatable("itemGroup.gekosmagic.elixirs.buttered")).entries((displayContext, entries) -> {
        displayContext.lookup().getOptionalWrapper(ModRegistryKeys.ELIXIR).ifPresent((wrapper) -> {
            addElixirs(entries, wrapper, ModItems.BUTTERED_ELIXIR);
        });
    }).build();
    public static final ItemGroup ELIXIRS_CLEAR = new ItemSubGroup.Builder(ELIXIRS, Gekosmagic.identify("clear_elixirs"), Text.translatable("itemGroup.gekosmagic.elixirs.clear")).entries((displayContext, entries) -> {
        displayContext.lookup().getOptionalWrapper(ModRegistryKeys.ELIXIR).ifPresent((wrapper) -> {
            addElixirs(entries, wrapper, ModItems.CLEAR_ELIXIR);
        });
    }).build();
    public static final ItemGroup ELIXIRS_UNINTERESTING = new ItemSubGroup.Builder(ELIXIRS, Gekosmagic.identify("uninteresting_elixirs"), Text.translatable("itemGroup.gekosmagic.elixirs.uninteresting")).entries((displayContext, entries) -> {
        displayContext.lookup().getOptionalWrapper(ModRegistryKeys.ELIXIR).ifPresent((wrapper) -> {
            addElixirs(entries, wrapper, ModItems.UNINTERESTING_ELIXIR);
        });
    }).build();
    public static final ItemGroup ELIXIRS_BLAND = new ItemSubGroup.Builder(ELIXIRS, Gekosmagic.identify("elixirs_bland"), Text.translatable("itemGroup.gekosmagic.elixirs.bland")).entries((displayContext, entries) -> {
        displayContext.lookup().getOptionalWrapper(ModRegistryKeys.ELIXIR).ifPresent((wrapper) -> {
            addElixirs(entries, wrapper, ModItems.BLAND_ELIXIR);
        });
    }).build();
    public static final ItemGroup ELIXIRS_DIFFUSING = new ItemSubGroup.Builder(ELIXIRS, Gekosmagic.identify("diffusing_elixirs"), Text.translatable("itemGroup.gekosmagic.elixirs.diffusing")).entries((displayContext, entries) -> {
        displayContext.lookup().getOptionalWrapper(ModRegistryKeys.ELIXIR).ifPresent((wrapper) -> {
            addElixirs(entries, wrapper, ModItems.DIFFUSING_ELIXIR);
        });
    }).build();

    static {
        Registry.register(Registries.ITEM_GROUP, ELIXIRS_KEY, ELIXIRS);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register((entries) -> {
            entries.addBefore(Items.GHAST_SPAWN_EGG, ModItems.GECKO_SPAWN_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((entries) -> {
            entries.addAfter(Items.ARMADILLO_SCUTE,
                    ModItems.TOKAY_GECKO_SCALE, ModItems.ORCHID_GECKO_SCALE,
                    ModItems.BLACK_GECKO_SCALE, ModItems.SAND_GECKO_SCALE,
                    ModItems.CAT_GECKO_SCALE);
            entries.addAfter(Items.GLASS_BOTTLE, ModItems.GLASS_PHIAL);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((entries) -> {
            entries.addAfter(Items.BREWING_STAND, ModBlocks.ALCHEMY_STAND_BLOCK.asItem());
        });
    }

    public static void init() {}

    private static void addElixirs(ItemGroup.Entries entries, RegistryWrapper<Elixir> registryWrapper, Item item) {
        registryWrapper.streamEntries().map((entry) -> {
            return ElixirContentsComponent.createStack(item, entry);
        }).forEach(entries::add);
    }

    private ModItemGroups() {}
}
