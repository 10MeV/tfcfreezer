package com.github.Mev10.common.capabilities;

import com.github.Mev10.common.item.PortablefreezerItem;
import net.minecraftforge.energy.EnergyStorage;

public class PortableFreezerEnergyStorage extends EnergyStorage {
    public PortableFreezerEnergyStorage() {
        super(PortablefreezerItem.CAPACITY, PortablefreezerItem.MAX_TRANSFER);
    }
}
