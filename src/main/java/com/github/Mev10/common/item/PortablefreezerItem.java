package com.github.Mev10.common.item;

import com.github.Mev10.common.capabilities.PortableFreezerEnergyProvider;
import com.github.Mev10.common.container.PortableFreezerContainer;
import com.github.Mev10.network.SyncPortableFreezerPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class PortablefreezerItem extends Item {

    // 全局常量：所有电量参数在此定义，其他类引用此处
    public static final int CAPACITY = 4000000;
    public static final int MAX_TRANSFER = 20000;
    public static final int ENERGY_PER_TICK = 40;   // 每 tick 消耗，实际每秒消耗 = ENERGY_PER_TICK * 20

    public PortablefreezerItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }

    @Override
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return stack.getHoverName();
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
                    return new PortableFreezerContainer(windowId, inv, stack);
                }
            }, buf -> SyncPortableFreezerPacket.writeItemStack(stack, buf));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int energy = getEnergyStored(stack);
        return Math.round(13.0f * energy / CAPACITY);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x7DEBFF;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new PortableFreezerEnergyProvider();
    }

    public static boolean isTurnedOn(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("TurnedOn");
    }

    public static void setTurnedOn(ItemStack stack, boolean on) {
        stack.getOrCreateTag().putBoolean("TurnedOn", on);
    }

    public static @Nullable IEnergyStorage getEnergyStorage(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY).orElse(null);
    }

    public static int getEnergyStored(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY)
                .map(IEnergyStorage::getEnergyStored)
                .orElse(0);
    }
}
