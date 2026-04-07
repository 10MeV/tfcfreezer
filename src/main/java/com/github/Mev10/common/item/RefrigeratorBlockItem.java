package com.github.Mev10.common.item;

import com.github.Mev10.common.block.TfcfreezerBlocks;
import net.minecraft.world.item.BlockItem;

@SuppressWarnings("unused")
public class RefrigeratorBlockItem extends BlockItem {

    public RefrigeratorBlockItem(Properties properties) {
        super(TfcfreezerBlocks.REFRIGERATOR_BLOCK.get(), properties);
    }
}
