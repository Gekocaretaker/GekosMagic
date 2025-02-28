package com.gekocaretaker.gekosmagic.item;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.entity.ModEntities;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ModItems {
    public static final Item.Settings GENERIC_SETTINGS = new Item.Settings()
            .useItemPrefixedTranslationKey();
    public static final Item.Settings GENERIC_BLOCK_SETTINGS = new Item.Settings()
            .useBlockPrefixedTranslationKey();
    public static final Item.Settings ELIXIR_SETTINGS = new Item.Settings()
            .maxCount(16)
            .useItemPrefixedTranslationKey()
            .component(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT);

    public static final SpawnEggItem GECKO_SPAWN_EGG = register("gecko_spawn_egg",
            new SpawnEggItem(ModEntities.GECKO, 0x44acea, 0xea9e44, generic("gecko_spawn_egg")));
    public static final Item TOKAY_GECKO_SCALE = register("tokay_gecko_scale", new Item(generic("tokay_gecko_scale")));
    public static final Item ORCHID_GECKO_SCALE = register("orchid_gecko_scale", new Item(generic("orchid_gecko_scale")));
    public static final Item BLACK_GECKO_SCALE = register("black_gecko_scale", new Item(generic("black_gecko_scale")));
    public static final Item SAND_GECKO_SCALE = register("sand_gecko_scale", new Item(generic("sand_gecko_scale")));
    public static final Item CAT_GECKO_SCALE = register("cat_gecko_scale", new Item(generic("cat_gecko_scale")));

    public static final Item ELIXIR = register("elixir", new ElixirItem(elixir("elixir")));
    public static final Item SPLASH_ELIXIR = register("splash_elixir", new SplashElixirItem(elixir("splash_elixir")));
    public static final Item LINGERING_ELIXIR = register("lingering_elixir", new LingeringElixirItem(elixir("lingering_elixir")));
    public static final Item BUTTERED_ELIXIR = register("buttered_elixir", new ButteredElixirItem(elixir("buttered_elixir")));
    public static final Item CLEAR_ELIXIR = register("clear_elixir", new ClearElixirItem(elixir("clear_elixir")));
    public static final Item UNINTERESTING_ELIXIR = register("uninteresting_elixir", new UninterestingElixirItem(elixir("uninteresting_elixir")));
    public static final Item BLAND_ELIXIR = register("bland_elixir", new ElixirItem(elixir("bland_elixir")));
    public static final Item DIFFUSING_ELIXIR = register("diffusing_elixir", new DiffusingElixir(elixir("diffusing_elixir")));
    public static final Item GLASS_PHIAL = register("glass_phial", new GlassVialItem(generic("glass_phial")));

    private static <T extends Item> T register(String path, T item) {
        Registry.register(Registries.ITEM, Gekosmagic.identify(path), item);
        return item;
    }

    public static Item.Settings generic(String path) {
        return GENERIC_SETTINGS.registryKey(of(path));
    }

    public static Item.Settings genericBlock(String path) {
        return GENERIC_BLOCK_SETTINGS.registryKey(of(path));
    }

    public static Item.Settings elixir(String path) {
        return ELIXIR_SETTINGS.registryKey(of(path));
    }

    private static RegistryKey<Item> of(String path) {
        return RegistryKey.of(RegistryKeys.ITEM, Gekosmagic.identify(path));
    }

    public static void init() {}

    private ModItems() {}
}
