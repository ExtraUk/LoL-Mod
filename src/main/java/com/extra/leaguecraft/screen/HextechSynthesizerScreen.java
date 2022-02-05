package com.extra.leaguecraft.screen;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.container.HextechSynthesizerContainer;
import com.extra.leaguecraft.gui.CleanSynthesizerButton;
import com.extra.leaguecraft.gui.SynthesizerContainerButton;
import com.extra.leaguecraft.network.ModPacketHandler;
import com.extra.leaguecraft.network.ServerBoundSynthUpdatePacket;
import com.extra.leaguecraft.tileentity.HextechSynthesizerTile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.gen.feature.structure.BuriedTreasure;
import org.lwjgl.system.CallbackI;

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

        HextechSynthesizerTile tile = ((HextechSynthesizerTile) container.getTileEntity());
        this.addButton(new SynthesizerContainerButton(i + 91, j + 16, 18, 52, new StringTextComponent(""),
                new Button.IPressable() {
            @Override
            public void onPress(Button p_onPress_1_) {

            }
        },
                new Button.ITooltip(){
                    @Override
                    public void onTooltip(Button but, MatrixStack matrixStack, int mouseX, int mouseY) {
                        SynthesizerContainerButton button = ((SynthesizerContainerButton) but);
                        if (button.getShimmer() > 0) {
                            drawString(matrixStack, font, "Shimmer: " + button.getShimmer(), mouseX, mouseY - 20, 0xFF00DC);
                        }
                        else if (button.getExplosiveAmount() > 0) {
                            drawString(matrixStack, font, "Explosive Fluid: " + button.getExplosiveAmount(), mouseX, mouseY - 20, 0xCC4F22);
                        }
                        else if(button.getBrew() > 0){
                            drawString(matrixStack, font, "Total Brew Amount: " + button.getBrew(), mouseX, mouseY - 50, 0xCCFF00);
                            if(button.getEffect1() != null) {
                                drawString(matrixStack, font, "Ingredient 1: " + HextechSynthesizerTile.effectToString(button.getEffect1()) + " " + (button.getAmp1()+1), mouseX, mouseY - 40, 0xCCFF00);
                            }
                            if(button.getEffect2() != null) {
                                drawString(matrixStack, font, "Ingredient 2: " + HextechSynthesizerTile.effectToString(button.getEffect2()) + " " + (button.getAmp2()+1), mouseX, mouseY - 30, 0xCCFF00);
                            }
                            if(button.getEffect3() != null) {
                                drawString(matrixStack, font, "Ingredient 3: " + HextechSynthesizerTile.effectToString(button.getEffect3()) + " " + (button.getAmp3()+1), mouseX, mouseY - 20, 0xCCFF00);
                            }
                        }
                    }
                },
                tile.getShimmerAmount(), tile.getExplosiveFluidAmount(), tile.getTotalBrewAmount(), tile.getEffect1(), tile.getEffect2(), tile.getEffect3(),
                tile.getEffect1Amp(), tile.getEffect2Amp(), tile.getEffect3Amp()));

        int shimAmount = container.getShimmerAmount();
        int brewAmount = container.getBrewAmount();
        int explosiveAmount = container.getExplosiveFluidAmount();
        int progress = container.getProgress();
        this.addButton(new CleanSynthesizerButton(i + 149, j + 7, 20, 20, new StringTextComponent(""), new Button.IPressable() {
            @Override
            public void onPress(Button button) {
                ((HextechSynthesizerTile) container.getTileEntity()).disposeContents();
                ModPacketHandler.INSTANCE.sendToServer(new ServerBoundSynthUpdatePacket(container.getTileEntity().getPos()));
            }
        }));

        this.blit(matrixStack, i + 60, j + 26, 208, 241, (int)(progress*0.15714), 15);

        if(shimAmount > 0){
            if(shimAmount == 10){
                this.jShift = 67 - 1;
                this.vOffset = 50 - 1;
            }
            else if(shimAmount % 20 == 0){
                this.jShift = 67 - (shimAmount/20);
                this.vOffset = 50 - (shimAmount/20);
            }
            else{
                this.jShift = 67 - ((shimAmount-10)/20);
                this.vOffset = 50 - ((shimAmount-10)/20);
            }
            this.blit(matrixStack, i + 92, j + jShift, 176, vOffset, 16, 49);
        }

        else if(brewAmount > 0){
            this.jShift = 67 - (brewAmount/20);
            this.vOffset = 50 - (brewAmount/20);
            this.blit(matrixStack, i + 92, j + jShift, 192, vOffset, 16, 49);
        }

        else if(explosiveAmount > 0){
            if(explosiveAmount == 10){
                this.jShift = 67 - 1;
                this.vOffset = 50 - 1;
            }
            else if(explosiveAmount % 20 == 0){
                this.jShift = 67 - (explosiveAmount/20);
                this.vOffset = 50 - (explosiveAmount/20);
            }
            else{
                this.jShift = 67 - ((explosiveAmount-10)/20);
                this.vOffset = 50 - ((explosiveAmount-10)/20);
            }
            this.blit(matrixStack, i + 92, j + jShift, 208, vOffset, 16, 49);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }


}
