package com.gekocaretaker.gekosmagic.client.gui.screen.ingame;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.elixir.EssenceContainer;
import com.gekocaretaker.gekosmagic.network.EssenceIndexPayload;
import com.gekocaretaker.gekosmagic.resource.EssenceAssetLoader;
import com.gekocaretaker.gekosmagic.screen.AlchemyStandScreenHandler;
import com.gekocaretaker.gekosmagic.util.Quadruple;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.List;

@Environment(EnvType.CLIENT)
public class AlchemyStandScreen extends HandledScreen<AlchemyStandScreenHandler> {
    private static final Identifier FUEL_LENGTH_TEXTURE = Identifier.ofVanilla("container/brewing_stand/fuel_length");
    private static final Identifier BUBBLES_TEXTURE = Identifier.ofVanilla("container/brewing_stand/bubbles");
    private static final Identifier ESSENCE_SLOT_TEXTURE = Gekosmagic.identify("container/alchemy_stand/essence_slot");
    private static final Identifier ESSENCE_SELECTED_TEXTURE = Gekosmagic.identify("container/alchemy_stand/essence_selected");
    private static final Identifier ESSENCE_HIGHLIGHTED_TEXTURE = Gekosmagic.identify("container/alchemy_stand/essence_highlighted");
    private static final Identifier SCROLLER_TEXTURE = Gekosmagic.identify("container/alchemy_stand/scroller");
    private static final Identifier SCROLLER_DISABLED_TEXTURE = Gekosmagic.identify("container/alchemy_stand/scroller_disabled");
    private static final Identifier TEXTURE = Gekosmagic.identify("textures/gui/container/alchemy_stand.png");

    private static final int[] BUBBLES_PROGRESS = new int[]{29, 24, 20, 16, 11, 6, 0};
    private static final int ESSENCE_LIST_COLUMNS = 2;
    private static final int ESSENCE_LIST_ROWS = 3;
    private static final int SCROLLBAR_WIDTH = 12;
    private static final int SCROLLBAR_HEIGHT = 15;
    private static final int SCROLLBAR_OFFSET_X = 155;
    private static final int SCROLLBAR_OFFSET_Y = 17;
    private static final int SCROLLBAR_AREA_HEIGHT = 48;
    private static final int ESSENCE_ENTRY_SIZE = 16;
    private static final int ESSENCE_LIST_OFFSET_X = 120;
    private static final int ESSENCE_LIST_OFFSET_Y = 17;

    private float scrollAmount;
    private int scrollOffset; // I don't know why I am using this, but I am going to keep it just in case.
    private boolean scrollbarClicked;
    private int visibleTopRow;

    public AlchemyStandScreen(AlchemyStandScreenHandler handler, PlayerInventory inventory, Text tittle) {
        super(handler, inventory, tittle);
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private int getRows() {
        return MathHelper.ceilDiv(this.handler.getEssences().size(), ESSENCE_LIST_ROWS);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;

        // Background
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);

        // Fuel
        int k = this.handler.getFuel();
        int l = MathHelper.clamp((18 * k + 20 - 1 ) / 20, 0, 18);
        if (l > 0) {
            context.drawGuiTexture(FUEL_LENGTH_TEXTURE, 18, 4, 0, 0, i + 55, j + 44, l, 4);
        }

        // Scroll
        int scrollPos = (int) (33.0F * this.scrollAmount);
        Identifier scrollIdTexture = this.shouldScroll() ? SCROLLER_TEXTURE : SCROLLER_DISABLED_TEXTURE;
        context.drawGuiTexture(scrollIdTexture, i + SCROLLBAR_OFFSET_X, j + SCROLLBAR_OFFSET_Y + scrollPos, SCROLLBAR_WIDTH, SCROLLBAR_HEIGHT);
        DiffuseLighting.disableGuiDepthLighting();

        // Essence List
        int essenceListX = i + ESSENCE_LIST_OFFSET_X;
        int essenceListY = j + ESSENCE_LIST_OFFSET_Y;

        label:
        for (int pos = this.scrollOffset; pos < ESSENCE_LIST_ROWS; pos++) {
            for (int pos2 = 0; pos2 < ESSENCE_LIST_COLUMNS; pos2++) {
                int o = pos + this.visibleTopRow;
                int p = o * ESSENCE_LIST_COLUMNS + pos2;
                if (p >= this.handler.getEssences().size()) {
                    break label;
                }

                int q = essenceListX + pos2 * ESSENCE_ENTRY_SIZE;
                int r = essenceListY + pos * ESSENCE_ENTRY_SIZE;
                boolean bl = mouseX >= q && mouseY >= r && mouseX < q + ESSENCE_ENTRY_SIZE && mouseY < r + ESSENCE_ENTRY_SIZE;
                Identifier slotIdentifier;
                if (p == this.handler.getSelectedIndex()) {
                    slotIdentifier = ESSENCE_SELECTED_TEXTURE;
                } else if (bl) {
                    slotIdentifier = ESSENCE_HIGHLIGHTED_TEXTURE;
                } else {
                    slotIdentifier = ESSENCE_SLOT_TEXTURE;
                }

                context.drawGuiTexture(slotIdentifier, q, r, ESSENCE_ENTRY_SIZE, ESSENCE_ENTRY_SIZE);
                drawEssenceOrb(context, this.handler.getEssences().get(p), q, r);
            }
        }
        DiffuseLighting.enableGuiDepthLighting();

        // Brewing
        int brewingTexturePosX = i + 17;
        int brewingTexturePosY = j + 50;
        int brewingTextureSize = EssenceAssetLoader.ESSENCE_CONTAINER_TEXTURE_SIZE;
        context.drawTexture(EssenceAssetLoader.ESSENCE_CONTAINER_TEXTURE, brewingTexturePosX, brewingTexturePosY, 16, 16, 0, 0, brewingTextureSize, brewingTextureSize, brewingTextureSize, brewingTextureSize);
        int m = this.handler.getBrewTime();
        if (m > 0) {
            int n = (int) (28.0F * (1.0F - (float) m / 400.0F));
            if (n > 0) {
                Quadruple<Identifier, Integer, Identifier, Integer> textures = EssenceAssetLoader.getTexturesByEssence(this.handler.getEssences().get(this.handler.getSelectedIndex()).getEssence());
                int visible = MathHelper.floor(MathHelper.clamp((10 * (400 - m) + 400 - 1) / 400, 0, 10));
                context.drawTexture(textures.getThird(), brewingTexturePosX + 3, brewingTexturePosY + 3 + 10 - visible, 10, visible, 0, textures.getFourth() - visible, textures.getFourth(), visible, textures.getFourth(), textures.getFourth());
            }

            // Bubbles
            n = BUBBLES_PROGRESS[m / 2 % 7];
            if (n > 0) {
                context.drawGuiTexture(BUBBLES_TEXTURE, 12, 29, 0, 29 - n, i + 58, j + 14 + 29 - n, 12, n);
            }
        }
    }

    private void drawEssenceOrb(DrawContext context, EssenceContainer container, int x, int y) {
        Quadruple<Identifier, Integer, Identifier, Integer> textures = EssenceAssetLoader.getTexturesByEssence(container.getEssence());
        int sizeA = textures.getSecond();
        int sizeB = textures.getFourth();

        context.drawTexture(textures.getFirst(), x, y, 16, 16, 0, 0, sizeA, sizeA, sizeA, sizeA);
        int count = container.getCount();
        int maxCount = container.getLimit();
        int visible = MathHelper.floor(MathHelper.clamp((10 * count + maxCount - 1) / maxCount, 0, 10));
        if (visible > 0) {
            context.drawTexture(textures.getThird(), x + 3, y + 3 + 10 - visible, 10, visible, 0, sizeB - visible, sizeB, visible, sizeB, sizeB);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrollbarClicked = false;
        int i = this.x + ESSENCE_LIST_OFFSET_X;
        int j = this.y + ESSENCE_LIST_OFFSET_Y;

        for (int k = this.scrollOffset; k < ESSENCE_LIST_ROWS; k++) {
            for (int l = 0; l < ESSENCE_LIST_COLUMNS; l++) {
                double d = mouseX - (double) (i + l * ESSENCE_ENTRY_SIZE);
                double e = mouseY - (double) (j + k * ESSENCE_ENTRY_SIZE);
                int m = k + this.visibleTopRow;
                int n = m * ESSENCE_LIST_COLUMNS + l;
                if (d >= 0.0 && e >= 0.0 && d < ESSENCE_ENTRY_SIZE && e < ESSENCE_ENTRY_SIZE && this.handler.onButtonClick(this.client.player, n)) {
                    MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    ClientPlayNetworking.send(new EssenceIndexPayload(n, this.handler.getBlockEntity().getPos()));
                    return true;
                }
            }
        }

        i = this.x + SCROLLBAR_OFFSET_X;
        j = this.y + SCROLLBAR_OFFSET_Y;
        if (mouseX >= (double) i && mouseX < (double) (i + SCROLLBAR_WIDTH) && mouseY >= (double) j && mouseY < (double) (j + SCROLLBAR_AREA_HEIGHT)) {
            this.scrollbarClicked = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.scrollbarClicked && this.shouldScroll()) {
            int j = this.y + SCROLLBAR_OFFSET_Y;
            int k = j + SCROLLBAR_AREA_HEIGHT;
            this.scrollAmount = ((float) mouseY - (float) j - 7.5F) / ((float) (k - j) - SCROLLBAR_HEIGHT);
            this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0F, 1.0F);
            this.visibleTopRow = Math.max((int) ((double) (this.scrollAmount * (float) this.getMaxScroll()) + 0.5), 0);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (this.shouldScroll()) {
            int i = this.getMaxScroll();
            float f = (float) verticalAmount / (float) i;
            this.scrollAmount = MathHelper.clamp(this.scrollAmount - f, 0.0F, 1.0F);
            this.visibleTopRow = Math.max((int) (this.scrollAmount * (float) i + 0.5F), 0);
        }
        return true;
    }

    private boolean shouldScroll() {
        return this.handler.getEssences().size() > 6;
    }

    protected int getMaxScroll() {
        return (this.handler.getEssences().size() + ESSENCE_LIST_ROWS - 2) / ESSENCE_LIST_COLUMNS - 3;
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        int i = this.x + ESSENCE_LIST_OFFSET_X;
        int j = this.y + ESSENCE_LIST_OFFSET_Y;

        if (this.handler.getCursorStack().isEmpty()) {
            for (int k = this.scrollOffset; k < ESSENCE_LIST_ROWS; k++) {
                for (int l = 0; l < ESSENCE_LIST_COLUMNS; l++) {
                    double d = x - (double) (i + l * ESSENCE_ENTRY_SIZE);
                    double e = y - (double) (j + k * ESSENCE_ENTRY_SIZE);
                    int m = k + this.visibleTopRow;
                    int n = m * ESSENCE_LIST_COLUMNS + l;
                    if (d >= 0.0 && e >= 0.0 && d < ESSENCE_ENTRY_SIZE && e < ESSENCE_ENTRY_SIZE) {
                        if (n >= 0 && n < this.handler.getEssences().size()) {
                            EssenceContainer essenceContainer = this.handler.getEssences().get(n);
                            context.drawTooltip(this.textRenderer, this.getTooltipFromEssence(essenceContainer), essenceContainer.getTooltipData(), x, y);
                        }
                    }
                }
            }
        }

        super.drawMouseoverTooltip(context, x, y);
    }

    protected List<Text> getTooltipFromEssence(EssenceContainer container) {
        return getTooltipFromEssence(this.client, container);
    }

    public static List<Text> getTooltipFromEssence(MinecraftClient client, EssenceContainer container) {
        return container.getTooltip(Item.TooltipContext.create(client.world), client.player, client.options.advancedItemTooltips ? TooltipType.Default.ADVANCED : TooltipType.Default.BASIC);
    }
}
