package com.gekocaretaker.gekosmagic.client.render.entity.state;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class GeckoEntityRenderState extends LivingEntityRenderState {
    private static final Identifier DEFAULT_TEXTURE = Gekosmagic.identify("textures/entity/gecko/tokay.png");
    public boolean inSittingPose;
    public boolean isDancing;
    public Identifier texture;
    @Nullable
    public DyeColor collarColor;

    public GeckoEntityRenderState() {
        this.texture = DEFAULT_TEXTURE;
    }
}
