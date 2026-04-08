package com.github.Mev10.common.item;

import com.github.Mev10.common.block.TfcfreezerBlocks;
import net.minecraft.world.item.BlockItem;

@SuppressWarnings("unused")
public class freezerBlockItem extends BlockItem {

    public freezerBlockItem(Properties properties) {
        super(TfcfreezerBlocks.freezer_BLOCK.get(), properties);
    }
}
