package com.github.Mev10.common.capabilities;

import com.github.Mev10.common.item.PortablefreezerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;

public class PortableFreezerEnergyStorage extends EnergyStorage {
    private final ItemStack stack;

    public PortableFreezerEnergyStorage(ItemStack stack) {
        super(PortablefreezerItem.CAPACITY, PortablefreezerItem.MAX_TRANSFER);
        this.stack = stack;
        this.energy = stack.getOrCreateTag().getInt("Energy");
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = super.receiveEnergy(maxReceive, simulate);
        if (!simulate && received > 0) {
            stack.getOrCreateTag().putInt("Energy", this.energy);
        }
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);
        if (!simulate && extracted > 0) {
            stack.getOrCreateTag().putInt("Energy", this.energy);
        }
        return extracted;
    }
}