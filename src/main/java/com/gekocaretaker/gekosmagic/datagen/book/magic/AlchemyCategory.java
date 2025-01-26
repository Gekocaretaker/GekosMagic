package com.gekocaretaker.gekosmagic.datagen.book.magic;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.block.ModBlocks;
import com.gekocaretaker.gekosmagic.datagen.book.magic.alchemy.AlchemyIntroEntry;
import com.gekocaretaker.gekosmagic.datagen.book.magic.alchemy.ElixirTemplateEntry;
import com.gekocaretaker.gekosmagic.datagen.book.magic.alchemy.EssenceTemplateEntry;
import com.gekocaretaker.gekosmagic.datagen.book.magic.alchemy.EssencesEntry;
import com.gekocaretaker.gekosmagic.item.ModItems;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class AlchemyCategory extends CategoryProvider {
    public static final String ID = "alchemy";

    public AlchemyCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "____v_w_x_y_____________",
                "___u_i_j_k_z____________",
                "5_t_h_a_b_l_á___>_______",
                "_4_s_g_e_c_m_é___789____",
                "__3_r_f_d_n_í____0_=____",
                "___2_q_p_o_ó_____+?<____",
                "____1_ᚦ_ᚳ_ú_____________"
        };
    }

    @Override
    protected void generateEntries() {
        BookEntryModel introEntry = this.add(new AlchemyIntroEntry(this).generate('>'));
        BookEntryModel essencesEntry = this.add(new EssencesEntry(this).generate('e'));

        BookEntryModel elixirEntry = addElixir(
                "elixir", "Elixir", ModItems.ELIXIR,
                "The elixir is your basic effect giving drink. Similar to regular potions.",
                '7'
        );
        BookEntryModel splashElixirEntry = addElixir(
                "splash_elixir", "Splash Elixir", ModItems.SPLASH_ELIXIR,
                "Splash elixirs allow you to throw elixirs, allowing for you to figh enemies and buff allies.",
                '8'
        );
        BookEntryModel lingeringElixirEntry = addElixir(
                "lingering_elixir", "Lingering Elixir", ModItems.LINGERING_ELIXIR,
                "Works like lingering potions, leaving a field of effects that will make combat more complex.",
                '9'
        );
        BookEntryModel butteredElixirEntry = addElixir(
                "buttered_elixir", "Buttered Elixir", ModItems.BUTTERED_ELIXIR,
                "Buttered elixirs are much faster to drink, allowing for last second self-buffing.",
                '0'
        );
        BookEntryModel clearElixirEntry = addElixir(
                "clear_elixir", "Clear Elixir", ModItems.CLEAR_ELIXIR,
                "Make sure to keep close attention to clear elixirs, as they are completely unidentifiable. Great for parties and roulette.",
                '='
        );
        BookEntryModel uninterestingElixirEntry = addElixir(
                "uninteresting_elixir", "Uninteresting Elixir", ModItems.UNINTERESTING_ELIXIR,
                "Uninteresting elixirs only have the color to be identified by.",
                '+'
        );
        BookEntryModel blandElixirEntry = addElixir(
                "bland_elixir", "Bland Elixir", ModItems.BLAND_ELIXIR,
                "Bland elixirs look like water until you look at the name and effects. Great for those with muscle memory and for worrying your opponents.",
                '?'
        );
        BookEntryModel diffusingElixirEntry = addElixir(
                "diffusing_elixir", "Diffusing Elixir", ModItems.DIFFUSING_ELIXIR,
                "These diffusing elixirs allow you to remove effects that are ailing you. However, if the effect you have is stronger or longer than what the elixir has, it will only remove the amplifier it has and/or the time it has.",
                '<'
        );

        BookEntryModel burningEntry = addEssence(
                "burning", "Burning", "Pure living fire",
                Items.BLAZE_POWDER, "This essence is dangerous to touch, being fire and all.",
                'a', essencesEntry
        );
        BookEntryModel coldEntry = addEssence(
                "cold", "Cold", "A winter's breath",
                Items.SNOW_BLOCK, "Frost forms on the outside of the container, due to tiny droplets of water forming on the surface.",
                'b', burningEntry
        );
        BookEntryModel evilEntry = addEssence(
                "evil", "Evil", "Nightmare fuel",
                Items.PHANTOM_MEMBRANE, "The creatures this essence forms from appear when you do not get enough sleep. Dangerous to obtain, but quite useful.",
                'c', coldEntry
        );
        BookEntryModel fearEntry = addEssence(
                "fear", "Fear", "Spiders, oceans, and more",
                Items.SPIDER_EYE, "Irrational or rational, everyone has fears, just remember that friends & familiars can help you. This essence radiates fear to those around.",
                'd', evilEntry
        );
        BookEntryModel fermentedEntry = addEssence(
                "fermented", "Fermentation", "A mirror",
                Items.FERMENTED_SPIDER_EYE, "Fermented essence seems to have the unique ability to switch a potion from being good to bad and vice versa. I believe this process is called reversion if my history books are correct.",
                'f', fearEntry
        );
        BookEntryModel flowEntry = addEssence(
                "flow", "Flowing", "Go with it",
                Items.WIND_CHARGE, "Constantly shifting and moving, it seems to have similar properties to liquid helium, just without the 0°K temperature.",
                'g', fermentedEntry
        );
        BookEntryModel fungalEntry = addEssence(
                "fungal", "Fungus", "How would you categorize these?",
                Items.BROWN_MUSHROOM, "Fungi are important for those who seek to learn about healing. Just don't go taste testing them.",
                'h', flowEntry
        );
        BookEntryModel glowingEntry = addEssence(
                "glowing", "Glowing", "A good nightlight",
                Items.GLOWSTONE_DUST, "Glowing essence has the ability to make some elixirs stronger, usually at a cost of time.",
                'i', fungalEntry
        );
        BookEntryModel grittyEntry = addEssence(
                "gritty", "Grit", "Course and rough and irritating",
                ModItems.SAND_GECKO_SCALE, "Grit is used to sand things down, but this essence is used to make uninteresting elixirs.",
                'j', glowingEntry
        );
        BookEntryModel growthEntry = addEssence(
                "growth", "Growth", "To learn and gain experiences",
                Items.BONE_MEAL, "Both helpful for gardening and for teaching.",
                'k', grittyEntry
        );
        BookEntryModel lingeringEntry = addEssence(
                "lingering", "Lingering", "Stay awhile",
                Items.DRAGON_BREATH, "Like the breath it comes from, elixirs made with lingering essence will stay in the world for a bit of time.",
                'l', growthEntry
        );
        BookEntryModel luckyEntry = addEssence(
                "lucky", "Luck", "One might say it is statistics, I say no",
                Items.RABBIT_FOOT, "It is quite improbable that you will ever have too much luck essence.",
                'm', lingeringEntry
        );
        BookEntryModel magmaEntry = addEssence(
                "magma", "Magma", "Slow flowing, very hot though",
                Items.MAGMA_CREAM, "Magma usually is obtained by magma cubes, but it can also be ejected from the earth as lava.",
                'n', luckyEntry
        );
        BookEntryModel midnightEntry = addEssence(
                "midnight", "Midnight", "A sky full of stars",
                ModItems.BLACK_GECKO_SCALE, "Used in making buttered elixirs. The darkest point of the night, it is also good for sky gazing.",
                'o', magmaEntry
        );
        BookEntryModel netherEntry = addEssence(
                "nether", "Nether", "Hellfire, Dark fire",
                Items.NETHER_WART, "The most important essence you will probably need. Most of the basic elixirs, known as Vanilla, use this essence.",
                'p', midnightEntry
        );
        BookEntryModel oldEntry = addEssence(
                "old", "Old-Time", "Endings and memories",
                Items.TUBE_CORAL, "Feelings of nostalgia radiate off of this essence.",
                'q', netherEntry
        );
        BookEntryModel peaceEntry = addEssence(
                "peace", "Peace", "A calming force",
                ModItems.ORCHID_GECKO_SCALE, "Finding oneself might need peace, but making clear elixirs requires it.",
                'r', oldEntry
        );
        BookEntryModel poweredEntry = addEssence(
                "powered", "Power", "Force to be reckoned with",
                Items.REDSTONE, "Imbuing select elixirs with power essence allows for the effects to last longer.",
                's', peaceEntry
        );
        BookEntryModel rageEntry = addEssence(
                "rage", "Rage", "Seeing red",
                ModItems.CAT_GECKO_SCALE, "What truly is angering is that this essence is used to make bland elixirs.",
                't', poweredEntry
        );
        BookEntryModel resonantEntry = addEssence(
                "resonant", "Resonance", "A perfect tune",
                Items.AMETHYST_BLOCK, "This essence is good at picking up sounds at specific frequencies. Keep it away from christmas music or you will forever hear jingle bells.",
                'u', rageEntry
        );
        BookEntryModel richEntry = addEssence(
                "rich", "Richness", "Flavor and nutrition, not value",
                Items.GOLDEN_CARROT, "Golden carrots are a great food item, which translates in some way to this essence.",
                'v', resonantEntry
        );
        BookEntryModel sadEntry = addEssence(
                "sad", "Sadness", "A good cry",
                Items.GHAST_TEAR, "While sadness is often seen as a negative thing, it can help make the good moments in life feel all the more better. This essence embodies that.",
                'w', richEntry
        );
        BookEntryModel sharpEntry = addEssence(
                "sharp", "Sharpness", "Pointy",
                Items.PUFFERFISH, "Pufferfish are deadly to eat, but are great for sharpness essence.",
                'x', sadEntry
        );
        BookEntryModel shinyEntry = addEssence(
                "shiny", "Shine", "Don't look directly",
                Items.GLISTERING_MELON_SLICE, "A giant crab comes to mind with this essence.",
                'y', sharpEntry
        );
        BookEntryModel silentEntry = addEssence(
                "silent", "Silence", "...",
                Items.SCULK, "*Watch out for wardens when collecting resources for this essence. Bring wool and silk touch.*",
                'z', shinyEntry
        );
        BookEntryModel slimyEntry = addEssence(
                "slimy", "Slime", "Weird texture",
                Items.SLIME_BLOCK, "Made with glue, borax, and glitter.",
                'á', silentEntry
        );
        BookEntryModel slurryEntry = addEssence(
                "slurry", "Slurry", "Rock and tumble",
                Items.STONE, "Slurry is found in rock tumbling, and is a mixture of water, grit, and the remains of the rocks you tumbled. Make sure to have bath cycles for the rocks to be shiny.",
                'é', slimyEntry
        );
        BookEntryModel spreadingEntry = addEssence(
                "spreading", "Spreading", "Gets everywhere",
                Items.GUNPOWDER, "The explosive force of this essence turns elixirs to splash elixirs, making them a formidable weapon or tool.",
                'í', slurryEntry
        );
        BookEntryModel soulEntry = addEssence(
                "soul", "Souls", "Better call Soul",
                Items.SOUL_SAND, "Souls seemingly burn blue, meaning that the fire is much hotter than normal flames.",
                'ó', spreadingEntry
        );
        BookEntryModel stickyEntry = addEssence(
                "sticky", "Sticking", "Stronger than steel",
                Items.COBWEB, "There are several versions of spider silk, with each one having a different purpose. This essence mostly has traits of the variant used for catching prey.",
                'ú', soulEntry
        );
        BookEntryModel sturdyEntry = addEssence(
                "sturdy", "Sturdy", "Hard to break",
                Items.TURTLE_SCUTE, "Some turtle shells are a geometric shape called a gömböc, with only one stable & one unstable point of rest. I do not think the turtle scutes that form this essence have shells like that.",
                'ᚳ', stickyEntry
        );
        BookEntryModel sweetEntry = addEssence(
                "sweet", "Sweetness", "Candy crash",
                Items.SUGAR, "Sugar is a great baking ingredient, and this essence is great for elixirs. In neither case is it good for your teeth.",
                'ᚦ', sturdyEntry
        );
        BookEntryModel timeEntry = addEssence(
                "time", "Time", "Arrow of entropy",
                Items.EGG, "Time is relative, or is it? Yes with this essence around, you just can't control it.",
                '1', sweetEntry
        );
        BookEntryModel tokayEntry = addEssence(
                "tokay", "Tokay", "Loud lizard call",
                ModItems.TOKAY_GECKO_SCALE, "These lizards are very loud and get their namesake from the 'tokay' sound they make.",
                '2', timeEntry
        );
        BookEntryModel warpEntry = addEssence(
                "warp", "Warping", "A change in space",
                Items.CHORUS_FRUIT, "This essence takes on traits of the fruit that makes it and other ender creatures.",
                '3', tokayEntry
        );
        BookEntryModel waterEntry = addEssence(
                "water", "Water", "Hydrate or diedrate.",
                Items.ICE, "It would make more sense to directly use water to make essence, but ice is more controllable. Besides, the water container would get consumed as well.",
                '4', warpEntry
        );
        BookEntryModel youngEntry = addEssence(
                "young", "Young-Time", "Beginnings and learning",
                Items.OAK_SAPLING, "Life begins anew constantly, and this essence seems to be in a perpetual state of said beginning.",
                '5', waterEntry
        );
    }

    private BookEntryModel addEssence(String id, String name, String entryDesc, Item item,
                                      String pageText, char key, BookEntryModel parent) {
        return this.add(new EssenceTemplateEntry(
                this, id, name, entryDesc,
                item, "Essence of " + name, pageText
        ).generate(key).withParent(parent));
    }

    private BookEntryModel addElixir(String id, String name, Item item,
                                     String pageText, char key) {
        return this.add(new ElixirTemplateEntry(
                this, id, name, item, name, pageText
        ).generate(key));
    }

    @Override
    protected String categoryName() {
        return "Alchemy";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(ModBlocks.ALCHEMY_STAND_BLOCK);
    }

    @Override
    public String categoryId() {
        return ID;
    }

    public static Identifier makeEntryId(String id) {
        return Gekosmagic.identify(ID + "/" + id);
    }
}
