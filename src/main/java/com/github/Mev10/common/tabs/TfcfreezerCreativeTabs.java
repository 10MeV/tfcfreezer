package com.github.Mev10.common.tabs;

import com.github.Mev10.Tfcfreezer;
import com.github.Mev10.common.block.TfcfreezerBlocks;
import com.github.Mev10.common.item.TfcfreezerItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Tfcfreezer.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TfcfreezerCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Tfcfreezer.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TFC_ELECTRICAL_APPLIANCES_TAB = TABS.register("tfcea_creative_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(TfcfreezerItems.PORTABLE_freezer_ITEM.get()))
                    .title(Component.translatable(String.format("itemGroup.%s.creative_tab", Tfcfreezer.MOD_ID)))
                    .displayItems(TfcfreezerCreativeTabs::fillTfceaTab)
                    .build());

    public static void fillTfceaTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out) {
        out.accept(TfcfreezerBlocks.freezer_BLOCK.get());
        out.accept(TfcfreezerItems.PORTABLE_freezer_ITEM.get());
    }

    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(TfcfreezerBlocks.freezer_BLOCK.get());
        }
    }
}
