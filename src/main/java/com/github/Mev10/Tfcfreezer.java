package com.github.Mev10;

import com.mojang.logging.LogUtils;
import com.github.Mev10.client.TfcfreezerClientEvents;
import com.github.Mev10.common.block.TfcfreezerBlocks;
import com.github.Mev10.common.blockentities.TfcfreezerBlocksEntities;
import com.github.Mev10.common.container.TfcfreezerContainers;
import com.github.Mev10.common.item.TfcfreezerItems;
import com.github.Mev10.common.tabs.TfcfreezerCreativeTabs;
import com.github.Mev10.config.TfcfreezerConfig;
import com.github.Mev10.network.TfcfreezerPacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@SuppressWarnings("unused")
@Mod(Tfcfreezer.MOD_ID)
public class Tfcfreezer {
    public static final String MOD_ID = "tfcfreezer";
    public static final String MOD_NAME = "Tfcfreezer";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Tfcfreezer() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        TfcfreezerItems.ITEMS.register(eventBus);
        TfcfreezerBlocks.BLOCKS.register(eventBus);
        TfcfreezerBlocksEntities.BLOCK_ENTITIES.register(eventBus);
        TfcfreezerContainers.CONTAINERS.register(eventBus);
        TfcfreezerCreativeTabs.TABS.register(eventBus);

        TfcfreezerConfig.init();
        TfcfreezerPacketHandler.init();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            TfcfreezerClientEvents.init();
        }
    }
}