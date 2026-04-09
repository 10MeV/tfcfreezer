package com.github.Mev10.mixin;

import com.github.Mev10.common.item.PortablefreezerItem;
import net.dries007.tfc.network.PlaceBlockSpecialPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlaceBlockSpecialPacket.class)
public class PlaceBlockSpecialPacketMixin {

    @Inject(method = "handle", at = @At("HEAD"), cancellable = true, remap = false)
    private void preventPlaceActiveFreezer(ServerPlayer player, CallbackInfo ci) {
        if (player == null) return;
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof PortablefreezerItem && PortablefreezerItem.isTurnedOn(stack)) {
            ci.cancel(); // 取消整个网络包处理，不执行任何放置逻辑
        }
    }
}