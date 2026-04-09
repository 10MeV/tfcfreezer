package com.github.Mev10.mixin;

import com.github.Mev10.common.item.PortablefreezerItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {

    @Shadow
    public abstract ItemStack getItem();

    /**
     * 阻止玩家从任何槽位中取出已开启的便携冰箱
     */
    @Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
    private void preventPickupLockedFreezer(Player player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = getItem();
        if (stack.getItem() instanceof PortablefreezerItem && PortablefreezerItem.isTurnedOn(stack)) {
            cir.setReturnValue(false);
        }
    }
}