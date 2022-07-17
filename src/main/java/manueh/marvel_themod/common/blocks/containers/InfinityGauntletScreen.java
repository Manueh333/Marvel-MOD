package manueh.marvel_themod.common.blocks.containers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import manueh.marvel_themod.Main;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class InfinityGauntletScreen extends AbstractContainerScreen<InfinityGauntletContainer> {

    private final ResourceLocation GUI = new ResourceLocation(Main.MODID, "textures/gui/infinity_gauntlet_gui.png");

    public InfinityGauntletScreen(InfinityGauntletContainer container, Inventory playerInv, Component title) {
        super((InfinityGauntletContainer)container, playerInv, title);
    }

    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        getMinecraft().getTextureManager().bindForSetup(GUI);
        blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        super.renderLabels(matrixStack, x, y);
    }
}
