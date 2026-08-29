package com.github.Mev10.common.capabilities;

import com.github.Mev10.common.item.PortablefreezerItem;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PortableFreezerEnergyProvider implements ICapabilitySerializable<CompoundTag> {
    private static final String ENERGY_KEY = "Energy";

    private final PortableFreezerEnergyStorage energyStorage = new PortableFreezerEnergyStorage();
    private final LazyOptional<IEnergyStorage> energyCapability = LazyOptional.of(() -> energyStorage);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        if (capability == ForgeCapabilities.ENERGY) {
            return energyCapability.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(ENERGY_KEY, energyStorage.getEnergyStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        int energy = Math.max(0, Math.min(PortablefreezerItem.CAPACITY, tag.getInt(ENERGY_KEY)));
        energyStorage.deserializeNBT(IntTag.valueOf(energy));
    }
}
