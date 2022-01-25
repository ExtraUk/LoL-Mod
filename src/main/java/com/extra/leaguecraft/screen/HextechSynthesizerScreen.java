package com.extra.leaguecraft.screen;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.container.HextechChargerContainer;
import com.extra.leaguecraft.container.HextechSynthesizerContainer;
import com.extra.leaguecraft.tileentity.HextechSynthesizerTile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class HextechSynthesizerScreen extends ContainerScreen<HextechSynthesizerContainer> {

    private final ResourceLocation GUI = new ResourceLocation(LeagueCraft.MOD_ID, "textures/gui/hextech_synthesizer_gui.png");
    private int jShift = 0;
    private int vOffset = 0;

    public HextechSynthesizerScreen(HextechSynthesizerContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

        int shimAmount = container.getShimmerAmount();
        int brewAmount = container.getBrewAmount();
        int progress = container.getProgress();
        this.blit(matrixStack, i + 60, j + 26, 208, 0, (int)(progress*0.15714), 15);
        if(shimAmount > 0){
            if(shimAmount == 10){
                this.jShift = 67 - 1;
                this.vOffset = 50 - 1;
            }
            else if(shimAmount % 20 == 0 && shimAmount != 20){
                this.jShift = 67 - (shimAmount/20);
                this.vOffset = 50 - (shimAmount/20);
            }
            this.blit(matrixStack, i + 92, j + jShift, 176, vOffset, 16, 49);
        }
        else if(brewAmount > 0){
            this.jShift = 67 - (brewAmount/20);
            this.vOffset = 50 - (brewAmount/20);
            this.blit(matrixStack, i + 92, j + jShift, 192, vOffset, 16, 49);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

}
