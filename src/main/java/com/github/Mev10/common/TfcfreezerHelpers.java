package com.github.Mev10.common;

import com.github.Mev10.Tfcfreezer;
import net.minecraft.resources.ResourceLocation;

public class TfcfreezerHelpers {
    public static ResourceLocation identifier(String id)
    {
        return new ResourceLocation(Tfcfreezer.MOD_ID, id);
    }
}
