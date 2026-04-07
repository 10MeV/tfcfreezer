package com.github.Mev10.config;

import net.dries007.tfc.util.Helpers;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

public class TfcfreezerConfig {
    public static final TfcfreezerServerConfig SERVER = register(ModConfig.Type.SERVER, TfcfreezerServerConfig::new);
    public static void init() {}

    private static <C> C register(ModConfig.Type type, Function<ForgeConfigSpec.Builder, C> factory)
    {
        Pair<C, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(factory);
        if (!Helpers.BOOTSTRAP_ENVIRONMENT) ModLoadingContext.get().registerConfig(type, specPair.getRight());
        return specPair.getLeft();
    }
}
