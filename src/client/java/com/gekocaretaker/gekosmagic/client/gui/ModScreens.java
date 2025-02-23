package com.gekocaretaker.gekosmagic.client.gui;

import com.gekocaretaker.gekosmagic.client.gui.screen.ingame.AlchemyStandScreen;
import com.gekocaretaker.gekosmagic.screen.ModScreenHandlerTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class ModScreens {
    public static void init() {
        HandledScreens.register(ModScreenHandlerTypes.ALCHEMY_STAND, AlchemyStandScreen::new);
    }

    private ModScreens() {}
}
