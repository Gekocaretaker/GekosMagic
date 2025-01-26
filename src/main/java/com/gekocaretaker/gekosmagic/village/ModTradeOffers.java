package com.gekocaretaker.gekosmagic.village;

import com.gekocaretaker.gekosmagic.component.type.ElixirContentsComponent;
import com.gekocaretaker.gekosmagic.elixir.type.AwkwardElixirs;
import com.gekocaretaker.gekosmagic.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

import java.util.Optional;

public class ModTradeOffers {
    public static void init() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 5, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 16),
                    Optional.of(new TradedItem(ModItems.GLASS_PHIAL, 3)),
                    ElixirContentsComponent.createStack(ModItems.DIFFUSING_ELIXIR, AwkwardElixirs.DECAY).copyWithCount(3),
                    3, 30, 2.5F
            ));
        });
    }

    private ModTradeOffers() {}
}
