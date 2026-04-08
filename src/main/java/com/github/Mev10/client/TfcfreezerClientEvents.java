package com.github.Mev10.client;

import com.github.Mev10.client.screen.freezerScreen;
import com.github.Mev10.common.container.TfcfreezerContainers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class TfcfreezerClientEvents {
    public static void init() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(TfcfreezerClientEvents::clientSetup);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(TfcfreezerContainers.freezer_CONTAINER.get(), freezerScreen::new);
        });
    }
}
