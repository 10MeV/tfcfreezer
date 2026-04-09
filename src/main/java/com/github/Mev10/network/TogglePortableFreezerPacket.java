package com.github.Mev10.network;

import com.github.Mev10.common.container.PortableFreezerContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class TogglePortableFreezerPacket {

    public TogglePortableFreezerPacket() {}

    public static void encode(TogglePortableFreezerPacket msg, FriendlyByteBuf buf) {}

    public static TogglePortableFreezerPacket decode(FriendlyByteBuf buf) {
        return new TogglePortableFreezerPacket();
    }

    public static void handle(TogglePortableFreezerPacket msg, ServerPlayer player) {
        if (player != null && player.containerMenu instanceof PortableFreezerContainer container) {
            container.togglePower();
        }
    }
}