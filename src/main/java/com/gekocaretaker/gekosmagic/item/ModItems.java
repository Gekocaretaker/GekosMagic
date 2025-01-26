package com.gekocaretaker.gekosmagic.item;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.component.ModDataComponentTypes;
import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.entity.ModEntities;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final SpawnEggItem GECKO_SPAWN_EGG = register("gecko_spawn_egg", new SpawnEggItem(ModEntities.GECKO, 0x44acea, 0xea9e44, new Item.Settings()));
    public static final Item TOKAY_GECKO_SCALE = register("tokay_gecko_scale", new Item(new Item.Settings()));
    public static final Item ORCHID_GECKO_SCALE = register("orchid_gecko_scale", new Item(new Item.Settings()));
    public static final Item BLACK_GECKO_SCALE = register("black_gecko_scale", new Item(new Item.Settings()));
    public static final Item SAND_GECKO_SCALE = register("sand_gecko_scale", new Item(new Item.Settings()));
    public static final Item CAT_GECKO_SCALE = register("cat_gecko_scale", new Item(new Item.Settings()));

    public static final Item ELIXIR = register("elixir", new ElixirItem(new Item.Settings().maxCount(16).component(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT)));
    public static final Item SPLASH_ELIXIR = register("splash_elixir", new SplashElixirItem(new Item.Settings().maxCount(16).component(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT)));
    public static final Item LINGERING_ELIXIR = register("lingering_elixir", new LingeringElixirItem(new Item.Settings().maxCount(16).component(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT)));
    public static final Item BUTTERED_ELIXIR = register("buttered_elixir", new ButteredElixirItem(new Item.Settings().maxCount(16).component(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT)));
    public static final Item CLEAR_ELIXIR = register("clear_elixir", new ClearElixirItem(new Item.Settings().maxCount(16).component(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT)));
    public static final Item UNINTERESTING_ELIXIR = register("uninteresting_elixir", new UninterestingElixirItem(new Item.Settings().maxCount(16).component(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT)));
    public static final Item BLAND_ELIXIR = register("bland_elixir", new ElixirItem(new Item.Settings().maxCount(16).component(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT)));
    public static final Item DIFFUSING_ELIXIR = register("diffusing_elixir", new DiffusingElixir(new Item.Settings().maxCount(16).component(ModDataComponentTypes.ELIXIR_CONTENTS, ElixirContentsComponent.DEFAULT)));
    public static final Item GLASS_PHIAL = register("glass_phial", new GlassVialItem(new Item.Settings()));

    private static <T extends Item> T register(String path, T item) {
        Registry.register(Registries.ITEM, Gekosmagic.identify(path), item);
        return item;
    }

    public static void init() {}

    private ModItems() {}
}
