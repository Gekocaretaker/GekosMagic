package com.gekocaretaker.gekosmagic.network;

import com.gekocaretaker.gekosmagic.Gekosmagic;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record EssenceIndexPayload(int index, BlockPos pos) implements CustomPayload {
    public static final CustomPayload.Id<EssenceIndexPayload> ID = new Id<>(Gekosmagic.identify("essence_index"));
    public static final PacketCodec<RegistryByteBuf, EssenceIndexPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, EssenceIndexPayload::index,
            BlockPos.PACKET_CODEC, EssenceIndexPayload::pos,
            EssenceIndexPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
