package com.gekocaretaker.gekosmagic.network;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import com.gekocaretaker.gekosmagic.elixir.EssenceContainer;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public record EssenceContainerPayload(List<EssenceContainer> essenceContainers, BlockPos pos) implements CustomPayload {
    public static final CustomPayload.Id<EssenceContainerPayload> ID = new Id<>(Gekosmagic.identify("essence_containers"));
    public static final PacketCodec<RegistryByteBuf, EssenceContainerPayload> CODEC = PacketCodec.tuple(
            EssenceContainer.LIST_PACKET_CODEC, EssenceContainerPayload::essenceContainers,
            BlockPos.PACKET_CODEC, EssenceContainerPayload::pos,
            EssenceContainerPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
