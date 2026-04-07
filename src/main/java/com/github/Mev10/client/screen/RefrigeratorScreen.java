package com.github.Mev10.client.screen;

import com.github.Mev10.Tfcfreezer;
import com.github.Mev10.client.screen.button.RefrigeratorTurnOnButton;
import com.github.Mev10.common.TfcfreezerHelpers;
import com.github.Mev10.common.blockentities.RefrigeratorBlockEntity;
import com.github.Mev10.common.container.RefrigeratorContainer;
import net.dries007.tfc.client.screen.BlockEntityScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RefrigeratorScreen extends BlockEntityScreen<RefrigeratorBlockEntity, RefrigeratorContainer> {

    public static final ResourceLocation BACKGROUND = TfcfreezerHelpers.identifier("textures/gui/refrigerator.png");
    private static final Component TOGGLE = Component.translatable(String.format("tooltip.%s.toggle", Tfcfreezer.MOD_ID));

    public RefrigeratorScreen(RefrigeratorContainer container, Inventory playerInventory, Component name) {
        super(container, playerInventory, name, BACKGROUND);
        imageWidth += 20;
    }

    @Override
    public void init()
    {
        super.init();
        addRenderableWidget(new RefrigeratorTurnOnButton(blockEntity, getGuiLeft(), getGuiTop(), TOGGLE));
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(gui, partialTicks, mouseX, mouseY);
        renderEnergyBar(gui);
    }

    private void renderEnergyBar(GuiGraphics gui)
    {
        int energyScaled = this.menu.getEnergyStoredScaled();
        gui.fill(this.leftPos + 177,
                this.topPos + 40+(64-energyScaled),
                this.leftPos + 185,
                this.topPos + 104,
                0xFFFF4757);
    }
}
