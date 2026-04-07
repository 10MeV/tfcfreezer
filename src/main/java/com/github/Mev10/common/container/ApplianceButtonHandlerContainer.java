package com.github.Mev10.common.container;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

public interface ApplianceButtonHandlerContainer {
    void onButtonPress(int var1, @Nullable CompoundTag var2);
}
