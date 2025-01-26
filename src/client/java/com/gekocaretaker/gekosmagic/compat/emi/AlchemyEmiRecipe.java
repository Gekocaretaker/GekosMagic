package com.gekocaretaker.gekosmagic.compat.emi;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.elixir.Essence;
import com.gekocaretaker.gekosmagic.elixir.EssenceContainer;
import com.gekocaretaker.gekosmagic.resource.EssenceDataLoader;
import com.gekocaretaker.gekosmagic.util.Quadruple;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Environment(EnvType.CLIENT)
public class AlchemyEmiRecipe implements EmiRecipe {
    private static final Identifier BACKGROUND = Gekosmagic.identify("textures/gui/container/emi_alchemy_stand.png");
    private static final EmiStack BLAZE_POWDER = EmiStack.of(Items.BLAZE_POWDER);
    private final Identifier id;
    private final EmiIngredient from;
    private final Essence essence;
    private final EmiStack to;

    public AlchemyEmiRecipe(Identifier id, EmiIngredient from, Essence essence, EmiStack to) {
        this.id = id;
        this.from = from;
        this.essence = essence;
        this.to = to;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return GekosmagicEmi.ALCHEMY_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return this.id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(from);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(to);
    }

    @Override
    public int getDisplayWidth() {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return 61;
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        widgetHolder.addTexture(BACKGROUND, 0, 0, 103, 61, 0, 0, 103, 61, 103, 61);
        widgetHolder.addSlot(BLAZE_POWDER, 0, 2).drawBack(false);
        widgetHolder.addSlot(this.from, 39, 36).drawBack(false);

        Quadruple<Identifier, Integer, Identifier, Integer> essenceTextureData = EssenceDataLoader.getTexturesByEssence(this.essence);
        int containerSize = essenceTextureData.getSecond();
        int essenceSize = essenceTextureData.getFourth();
        EssenceContainer essenceContainer = new EssenceContainer(this.essence, 1);
        widgetHolder.addTexture(essenceTextureData.getFirst(),
                63, 3, 16, 16, 0, 0,
                containerSize, containerSize,
                containerSize, containerSize);
        widgetHolder.addTexture(essenceTextureData.getThird(),
                66, 6, 10, 10, 0, 0,
                essenceSize, essenceSize,
                essenceSize, essenceSize);
        TooltipType.Default tooltipTypeToUse = MinecraftClient.getInstance().options.advancedItemTooltips ? TooltipType.ADVANCED : TooltipType.BASIC;
                //.tooltipText(essenceContainer.getTooltip(TooltipType.Default.BASIC));
        widgetHolder.addTooltipText(essenceContainer.getTooltip(tooltipTypeToUse), 63, 3, 16, 16);

        widgetHolder.addSlot(this.to, 85, 36).recipeContext(this);
    }
}
