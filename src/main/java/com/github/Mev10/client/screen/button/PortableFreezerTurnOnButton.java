package com.github.Mev10.client.screen.button;

import com.github.Mev10.client.screen.PortableFreezerScreen;
import com.github.Mev10.common.container.PortableFreezerContainer;
import com.github.Mev10.network.TogglePortableFreezerPacket;
import com.github.Mev10.network.TfcfreezerPacketHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.PacketDistributor;

public class PortableFreezerTurnOnButton extends Button {
    private final PortableFreezerContainer container;

    public PortableFreezerTurnOnButton(int x, int y, PortableFreezerContainer container) {
        super(x, y, 20, 20, Component.empty(), b -> {}, DEFAULT_NARRATION);
        this.container = container;
        setTooltip(Tooltip.create(Component.translatable("tooltip.tfcfreezer.toggle")));
    }

    @Override
    public void onPress() {
        TfcfreezerPacketHandler.send(PacketDistributor.SERVER.noArg(), new TogglePortableFreezerPacket());
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int v = container.isTurnedOn() ? 0 : 20;
        graphics.blit(PortableFreezerScreen.BACKGROUND, getX(), getY(), 236, v, 20, 20, 256, 256);
    }
}