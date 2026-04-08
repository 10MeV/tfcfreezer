package com.github.Mev10.common.blockentities;

import com.github.Mev10.Tfcfreezer;
import com.github.Mev10.common.block.TfcfreezerBlocks;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TfcfreezerBlocksEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Tfcfreezer.MOD_ID);
    public static final RegistryObject<BlockEntityType<freezerBlockEntity>> freezer_BLOCK = register("freezer", freezerBlockEntity::new, TfcfreezerBlocks.freezer_BLOCK);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block) {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }
}
