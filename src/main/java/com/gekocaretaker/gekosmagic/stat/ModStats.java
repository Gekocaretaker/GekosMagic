package com.gekocaretaker.gekosmagic.stat;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class ModStats {
    public static final Identifier INTERACT_WITH_ALCHEMY_STAND = register("interact_with_alchemy_stand", StatFormatter.DEFAULT);

    private static Identifier register(String id, StatFormatter formatter) {
        Identifier identifier = Gekosmagic.identify(id);
        Registry.register(Registries.CUSTOM_STAT, id, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }

    public static void init() {}

    private ModStats() {}
}
