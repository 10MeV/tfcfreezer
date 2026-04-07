package com.github.Mev10.client.screen.button;

import com.github.Mev10.client.screen.RefrigeratorScreen;
import com.github.Mev10.common.blockentities.RefrigeratorBlockEntity;
import com.github.Mev10.network.ApplianceButtonPacket;
import com.github.Mev10.network.TfcfreezerPacketHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.PacketDistributor;

public class RefrigeratorTurnOnButton extends Button {
    private final RefrigeratorBlockEntity refrigerator;

    public RefrigeratorTurnOnButton(RefrigeratorBlockEntity refrigerator, int guiLeft, int guiTop, Component tooltip)
    {
        super(guiLeft + 171, guiTop + 17, 20, 20, tooltip, b -> {}, DEFAULT_NARRATION);
        setTooltip(Tooltip.create(tooltip));
        this.refrigerator = refrigerator;
    }

    @Override
    public void onPress()
    {
        TfcfreezerPacketHandler.send(PacketDistributor.SERVER.noArg(), new ApplianceButtonPacket(0, null));
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
    {
        final int v = refrigerator.isTurnedOn() ? 0 : 20;

        graphics.blit(RefrigeratorScreen.BACKGROUND, getX(), getY(), 236, v, 20, 20, 256, 256);
    }
}
