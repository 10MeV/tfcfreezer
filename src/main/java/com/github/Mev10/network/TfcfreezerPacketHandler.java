package com.github.Mev10.network;

import com.github.Mev10.common.TfcfreezerHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class TfcfreezerPacketHandler {
    private static final String VERSION = Integer.toString(1);
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            TfcfreezerHelpers.identifier("network"),
            () -> VERSION,
            VERSION::equals,
            VERSION::equals
    );
    private static final MutableInt ID = new MutableInt(0);

    public static void send(PacketDistributor.PacketTarget target, Object message) {
        CHANNEL.send(target, message);
    }

    public static void init() {
        register(ApplianceButtonPacket.class,
                ApplianceButtonPacket::encode,
                ApplianceButtonPacket::new,
                ApplianceButtonPacket::handle);

        register(SyncPortableFreezerPacket.class,
                SyncPortableFreezerPacket::encode,
                SyncPortableFreezerPacket::decode,
                SyncPortableFreezerPacket::handle);

        register(TogglePortableFreezerPacket.class,
                TogglePortableFreezerPacket::encode,
                TogglePortableFreezerPacket::decode,
                TogglePortableFreezerPacket::handle);
    }

    private static <T> void register(Class<T> cls,
                                     BiConsumer<T, FriendlyByteBuf> encoder,
                                     Function<FriendlyByteBuf, T> decoder,
                                     BiConsumer<T, ServerPlayer> handler) {
        CHANNEL.registerMessage(ID.getAndIncrement(), cls, encoder, decoder,
                (packet, context) -> {
                    context.get().setPacketHandled(true);
                    context.get().enqueueWork(() -> handler.accept(packet, context.get().getSender()));
                });
    }
}