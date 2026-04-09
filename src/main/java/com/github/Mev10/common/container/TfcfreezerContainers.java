package com.github.Mev10.common.container;

import com.github.Mev10.Tfcfreezer;
import com.github.Mev10.common.blockentities.freezerBlockEntity;
import com.github.Mev10.common.blockentities.TfcfreezerBlocksEntities;
import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TfcfreezerContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS =
            DeferredRegister.create(Registries.MENU, Tfcfreezer.MOD_ID);

    // 方块冰箱容器
    public static final RegistryObject<MenuType<freezerContainer>> freezer_CONTAINER =
            RegistrationHelpers.<freezerBlockEntity, freezerContainer>registerBlockEntityContainer(
                    CONTAINERS,
                    "freezer",
                    TfcfreezerBlocksEntities.freezer_BLOCK,
                    freezerContainer::create
            );

    // 便携冰箱容器
    public static final RegistryObject<MenuType<PortableFreezerContainer>> PORTABLE_FREEZER_CONTAINER =
            CONTAINERS.register("portable_freezer",
                    () -> IForgeMenuType.create(PortableFreezerContainer::new));
}