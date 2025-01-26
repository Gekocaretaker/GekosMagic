package com.gekocaretaker.gekosmagic.sound;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent ENTITY_GECKO_CHIRPS = register("entity.gecko.chirps");
    public static final SoundEvent ENTITY_GECKO_DEATH = register("entity.gecko.death");
    public static final SoundEvent ENTITY_GECKO_EAT = register("entity.gecko.eat");
    public static final SoundEvent ENTITY_GECKO_HURT = register("entity.gecko.hurt");
    public static final SoundEvent ENTITY_GECKO_SCALES_DROP = register("entity.gecko.scales_drop");

    public static void init() {}

    private static SoundEvent register(String name) {
        Identifier id = Gekosmagic.identify(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    private ModSounds() {}
}
