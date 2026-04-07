package com.github.Mev10.common.container;

import com.github.Mev10.Tfcfreezer;
import com.github.Mev10.common.blockentities.RefrigeratorBlockEntity;
import com.github.Mev10.common.blockentities.TfcfreezerBlocksEntities;
import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TfcfreezerContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, Tfcfreezer.MOD_ID);
    public static final RegistryObject<MenuType<RefrigeratorContainer>> REFRIGERATOR_CONTAINER = TfcfreezerContainers.registerBlock("refrigerator", TfcfreezerBlocksEntities.REFRIGERATOR_BLOCK, (BlockEntityContainer.Factory<RefrigeratorBlockEntity, RefrigeratorContainer>) RefrigeratorContainer::create);

    private static <T extends InventoryBlockEntity<?>, C extends BlockEntityContainer<T>> RegistryObject<MenuType<C>> registerBlock(String name, Supplier<BlockEntityType<T>> type, BlockEntityContainer.Factory<T, C> factory) {
        return RegistrationHelpers.registerBlockEntityContainer(CONTAINERS, name, type, factory);
    }
}