package com.gekocaretaker.gekosmagic.datagen;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.elixir.Elixir;
import com.gekocaretaker.gekosmagic.elixir.Elixirs;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.elixir.Essences;
import com.gekocaretaker.gekosmagic.elixir.type.AwkwardElixirs;
import com.gekocaretaker.gekosmagic.elixir.type.MundaneElixirs;
import com.gekocaretaker.gekosmagic.elixir.type.ThickElixirs;
import com.gekocaretaker.gekosmagic.registry.ModRegistries;
import com.google.gson.JsonObject;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class AlchemyRecipeDataProvider {
    public static void init() {
        // New Generating

        // Already Generated
//        generateElixir("dashing", Elixirs.WATER, Essences.FUNGAL_ESSENCE, Elixirs.DASHING);
//        generateElixir("refined", Elixirs.WATER, Essences.OLD_ESSENCE, Elixirs.REFINED);
//        generateElixir("cordial", Elixirs.WATER, Essences.SILENT_ESSENCE, Elixirs.CORDIAL);
//        generateElixir("potent", Elixirs.WATER, Essences.GROWTH_ESSENCE, Elixirs.POTENT);
//        generateElixir("foul", Elixirs.WATER, Essences.TIME_ESSENCE, Elixirs.FOUL);
//        generateElixir("odorless", Elixirs.WATER, Essences.WARP_ESSENCE, Elixirs.ODORLESS);
//        generateElixir("rank", Elixirs.WATER, Essences.COLD_ESSENCE, Elixirs.RANK);
//        generateElixir("harsh", Elixirs.WATER, Essences.RESONANT_ESSENCE, Elixirs.HARSH);
//        generateElixir("acrid", Elixirs.WATER, Essences.SOUL_ESSENCE, Elixirs.ACRID);
//        generateElixir("gross", Elixirs.WATER, Essences.WATER_ESSENCE, Elixirs.GROSS);
//        generateElixir("stinky", Elixirs.WATER, Essences.YOUNG_ESSENCE, Elixirs.STINKY);
//        generateElixir("milky", Elixirs.WATER, Essences.SAD_ESSENCE, Elixirs.MILKY);
//        generateElixir("artless", Elixirs.WATER, Essences.STURDY_ESSENCE, Elixirs.ARTLESS);
//        generateElixir("thin", Elixirs.WATER, Essences.LUCKY_ESSENCE, Elixirs.THIN);
//        generateElixir("flat", Elixirs.WATER, Essences.SHINY_ESSENCE, Elixirs.FLAT);
//        generateElixir("bulky", Elixirs.WATER, Essences.SLURRY_ESSENCE, Elixirs.BULKY);
//        generateElixir("bungling", Elixirs.WATER, Essences.SHARP_ESSENCE, Elixirs.BUNGLING);
//        generateElixir("smooth", Elixirs.WATER, Essences.RICH_ESSENCE, Elixirs.SMOOTH);
//        generateElixir("suave", Elixirs.WATER, Essences.EVIL_ESSENCE, Elixirs.SUAVE);
//        generateElixir("debonair", Elixirs.WATER, Essences.STICKY_ESSENCE, Elixirs.DEBONAIR);
//        generateElixir("elegant", Elixirs.WATER, Essences.FLOW_ESSENCE, Elixirs.ELEGANT);
//        generateElixir("fancy", Elixirs.WATER, Essences.BURNING_ESSENCE, Elixirs.FANCY);
//        generateElixir("charming", Elixirs.WATER, Essences.FEAR_ESSENCE, Elixirs.CHARMING);
//        generateElixir("mundane_lizard_brain", Elixirs.MUNDANE, Essences.FEAR_ESSENCE, MundaneElixirs.LIZARD_BRAIN);
//        generateElixir("mundane_long_lizard_brain", MundaneElixirs.LIZARD_BRAIN, Essences.POWERED_ESSENCE, MundaneElixirs.LONG_LIZARD_BRAIN);
//        generateElixir("mundane_absolutely_not", Elixirs.MUNDANE, Essences.EVIL_ESSENCE, MundaneElixirs.ABSOLUTELY_NOT);
//        generateElixir("mundane_long_absolutely_not", MundaneElixirs.ABSOLUTELY_NOT, Essences.POWERED_ESSENCE, MundaneElixirs.LONG_ABSOLUTELY_NOT);
//        generateElixir("awkward_swiftness", Elixirs.AWKWARD, Essences.SWEET_ESSENCE, AwkwardElixirs.SWIFTNESS);
//        generateElixir("awkward_strong_swiftness", AwkwardElixirs.SWIFTNESS, Essences.GLOWING_ESSENCE, AwkwardElixirs.STRONG_SWIFTNESS);
//        generateElixir("awkward_long_swiftness", AwkwardElixirs.SWIFTNESS, Essences.POWERED_ESSENCE, AwkwardElixirs.LONG_SWIFTNESS);
//        generateReversionElixir("awkward_swiftness", "awkward_slowness", AwkwardElixirs.SWIFTNESS, AwkwardElixirs.SLOWNESS);
//        generateReversionElixir("awkward_strong_swiftness", "awkward_strong_slowness", AwkwardElixirs.STRONG_SWIFTNESS, AwkwardElixirs.STRONG_SLOWNESS);
//        generateReversionElixir("awkward_long_swiftness", "awkward_long_slowness", AwkwardElixirs.LONG_SWIFTNESS, AwkwardElixirs.LONG_SLOWNESS);
//        generateElixir("awkward_leaping", Elixirs.AWKWARD, Essences.LUCKY_ESSENCE, AwkwardElixirs.LEAPING);
//        generateElixir("awkward_strong_leaping", AwkwardElixirs.LEAPING, Essences.GLOWING_ESSENCE, AwkwardElixirs.STRONG_LEAPING);
//        generateElixir("awkward_long_leaping", AwkwardElixirs.LEAPING, Essences.POWERED_ESSENCE, AwkwardElixirs.LONG_LEAPING);
//        generateElixir("awkward_healing", Elixirs.AWKWARD, Essences.SHINY_ESSENCE, AwkwardElixirs.HEALING);
//        generateElixir("awkward_strong_healing", AwkwardElixirs.HEALING, Essences.GLOWING_ESSENCE, AwkwardElixirs.STRONG_HEALING);
//        generateReversionElixir("awkward_healing", "awkward_harming", AwkwardElixirs.HEALING, AwkwardElixirs.HARMING);
//        generateReversionElixir("awkward_strong_healing", "awkward_strong_harming", AwkwardElixirs.STRONG_HEALING, AwkwardElixirs.STRONG_HARMING);
//        generateReversionElixir("awkward_strength", "awkward_weakness", AwkwardElixirs.STRENGTH, AwkwardElixirs.WEAKNESS);
//        generateReversionElixir("awkward_long_strength", "awkward_long_weakness", AwkwardElixirs.LONG_STRENGTH, AwkwardElixirs.LONG_WEAKNESS);
//        generateElixir("awkward_regeneration", Elixirs.AWKWARD, Essences.SAD_ESSENCE, AwkwardElixirs.REGENERATION);
//        generateElixir("awkward_strong_regeneration", AwkwardElixirs.REGENERATION, Essences.GLOWING_ESSENCE, AwkwardElixirs.STRONG_REGENERATION);
//        generateElixir("awkward_long_regeneration", AwkwardElixirs.REGENERATION, Essences.POWERED_ESSENCE, AwkwardElixirs.LONG_REGENERATION);
//        generateReversionElixir("awkward_regeneration", "awkward_poison", AwkwardElixirs.REGENERATION, AwkwardElixirs.POISON);
//        generateReversionElixir("awkward_strong_regeneration", "awkward_strong_poison", AwkwardElixirs.STRONG_REGENERATION, AwkwardElixirs.STRONG_POISON);
//        generateReversionElixir("awkward_long_regeneration", "awkward_long_poison", AwkwardElixirs.LONG_REGENERATION, AwkwardElixirs.LONG_POISON);
//        generateElixir("awkward_fire_resistance", Elixirs.AWKWARD, Essences.MAGMA_ESSENCE, AwkwardElixirs.FIRE_RESISTANCE);
//        generateElixir("awkward_long_fire_resistance", AwkwardElixirs.FIRE_RESISTANCE, Essences.POWERED_ESSENCE, AwkwardElixirs.LONG_FIRE_RESISTANCE);
//        generateElixir("awkward_water_breathing", Elixirs.AWKWARD, Essences.FEAR_ESSENCE, AwkwardElixirs.WATER_BREATHING);
//        generateElixir("awkward_long_water_breathing", AwkwardElixirs.WATER_BREATHING, Essences.POWERED_ESSENCE, AwkwardElixirs.LONG_WATER_BREATHING);
//        generateElixir("awkward_night_vision", Elixirs.AWKWARD, Essences.RICH_ESSENCE, AwkwardElixirs.NIGHT_VISION);
//        generateElixir("awkward_long_night_vision", AwkwardElixirs.NIGHT_VISION, Essences.POWERED_ESSENCE, AwkwardElixirs.LONG_NIGHT_VISION);
//        generateReversionElixir("awkward_night_vision", "awkward_invisibility", AwkwardElixirs.NIGHT_VISION, AwkwardElixirs.INVISIBILITY);
//        generateReversionElixir("awkward_long_night_vision", "awkward_long_invisibility", AwkwardElixirs.LONG_NIGHT_VISION, AwkwardElixirs.LONG_INVISIBILITY);
//        generateElixir("awkward_slow_falling", Elixirs.AWKWARD, Essences.EVIL_ESSENCE, AwkwardElixirs.SLOW_FALLING);
//        generateElixir("awkward_long_slow_falling", AwkwardElixirs.SLOW_FALLING, Essences.POWERED_ESSENCE, AwkwardElixirs.LONG_SLOW_FALLING);
//        generateElixir("awkward_wind_charging", Elixirs.AWKWARD, Essences.FLOW_ESSENCE, AwkwardElixirs.WIND_CHARGING);
//        generateElixir("awkward_weaving", Elixirs.AWKWARD, Essences.STICKY_ESSENCE, AwkwardElixirs.WEAVING);
//        generateElixir("awkward_oozing", Elixirs.AWKWARD, Essences.SLIMY_ESSENCE, AwkwardElixirs.OOZING);
//        generateElixir("awkward_infestation", Elixirs.AWKWARD, Essences.SLURRY_ESSENCE, AwkwardElixirs.INFESTATION);
//        generateElixir("thick_turtle_master", Elixirs.THICK, Essences.STURDY_ESSENCE, ThickElixirs.TURTLE_MASTER);
//        generateElixir("thick_strong_turtle_master", ThickElixirs.TURTLE_MASTER, Essences.GLOWING_ESSENCE, ThickElixirs.STRONG_TURTLE_MASTER);
//        generateElixir("thick_long_turtle_master", ThickElixirs.TURTLE_MASTER, Essences.POWERED_ESSENCE, ThickElixirs.LONG_TURTLE_MASTER);
//        generateElixir("thick_adept_rabbit", Elixirs.THICK, Essences.FLOW_ESSENCE, ThickElixirs.ADEPT_RABBIT);
//        generateElixir("thick_strong_adept_rabbit", ThickElixirs.ADEPT_RABBIT, Essences.GLOWING_ESSENCE, ThickElixirs.STRONG_ADEPT_RABBIT);
//        generateElixir("thick_long_adept_rabbit", ThickElixirs.ADEPT_RABBIT, Essences.POWERED_ESSENCE, ThickElixirs.LONG_ADEPT_RABBIT);
//        generateReversionElixir("thick_turtle_master", "thick_adept_rabbit", ThickElixirs.TURTLE_MASTER, ThickElixirs.ADEPT_RABBIT);
//        generateReversionElixir("thick_strong_turtle_master", "thick_strong_adept_rabbit", ThickElixirs.STRONG_TURTLE_MASTER, ThickElixirs.STRONG_ADEPT_RABBIT);
//        generateReversionElixir("thick_long_turtle_master", "thick_long_adept_rabbit", ThickElixirs.LONG_TURTLE_MASTER, ThickElixirs.LONG_ADEPT_RABBIT);
//        generateElixir("thick_luck", Elixirs.THICK, Essences.LUCKY_ESSENCE, ThickElixirs.LUCK);
//        generateReversionElixir("thick_luck", "thick_bad_luck", ThickElixirs.LUCK, ThickElixirs.BAD_LUCK);
//        generateElixir(Gekosmagic.identify("thick"), Elixirs.WATER, Essences.GLOWING_ESSENCE, Elixirs.THICK);
//        generateElixir(Gekosmagic.identify("mundane"), Elixirs.WATER, Essences.MAGMA_ESSENCE, Elixirs.MUNDANE);
//        generateElixir(Gekosmagic.identify("uninteresting"), Elixirs.WATER, Essences.FEAR_ESSENCE, Elixirs.UNINTERESTING);
//        generateElixir(Gekosmagic.identify("clear"), Elixirs.WATER, Essences.SAD_ESSENCE, Elixirs.CLEAR);
//        generateElixir(Gekosmagic.identify("buttered"), Elixirs.WATER, Essences.SLIMY_ESSENCE, Elixirs.BUTTERED);
//        generateElixir(Gekosmagic.identify("sparkling"), Elixirs.WATER, Essences.SWEET_ESSENCE, Elixirs.SPARKLING);
//        generateElixir(Gekosmagic.identify("awkward_strength"), Elixirs.AWKWARD, Essences.BURNING_ESSENCE, AwkwardElixirs.STRENGTH);
//        generateElixir(Gekosmagic.identify("awkward_long_strength"), AwkwardElixirs.STRENGTH, Essences.POWERED_ESSENCE, AwkwardElixirs.LONG_STRENGTH);
//        generateElixir(Gekosmagic.identify("awkward_strong_strength"), AwkwardElixirs.STRENGTH, Essences.GLOWING_ESSENCE, AwkwardElixirs.STRONG_STRENGTH);
    }

    private static void generateElixir(String id, RegistryEntry<Elixir> from, Essence essence, RegistryEntry<Elixir> to) {
        generateElixir(Gekosmagic.identify(id), from, essence, to);
    }

    private static void generateReversionElixir(String id1, String id2, RegistryEntry<Elixir> elixir1, RegistryEntry<Elixir> elixir2) {
        generateElixir(Gekosmagic.identify(id1 + "_to_" + id2), elixir1, Essences.FERMENTED_ESSENCE, elixir2);
        generateElixir(Gekosmagic.identify(id2 + "_to_" + id1), elixir2, Essences.FERMENTED_ESSENCE, elixir1);
    }

    private static void generateElixir(Identifier id, RegistryEntry<Elixir> from, Essence essence, RegistryEntry<Elixir> to) {
        JsonObject object = new JsonObject();
        object.addProperty("type", "gekosmagic:elixir");
        object.addProperty("from", from.getIdAsString());
        object.addProperty("essence", ModRegistries.ESSENCE.getId(essence).toString());
        object.addProperty("to", to.getIdAsString());
        try {
            File file = new File("C:\\Users\\gekoc\\Downloads\\" + id.getPath() + ".json");
            if (!file.exists()) {
                file.createNewFile();
            }
            Writer writer = new FileWriter(file);
            writer.write(object.toString());
            writer.close();
        } catch (IOException e) {
            Gekosmagic.LOGGER.error(e.getMessage());
        }
        /*try (Writer writer = new FileWriter(new File("D:\\GameDev\\ModDev\\GekosMagic\\src\\main\\generated\\data\\gekosmagic\\alchemy\\" + id.getPath() + ".json"))) {
            writer.write(object.toString());
        } catch (IOException e) {
            Gekosmagic.LOGGER.error(e.getMessage());
        }*/
    }

    private AlchemyRecipeDataProvider() {}
}
