package com.extra.leaguecraft.gui;

import com.extra.leaguecraft.LeagueCraft;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class CleanSynthesizerButton extends Button {
    public CleanSynthesizerButton(int x, int y, int width, int height, ITextComponent title, Button.IPressable pressedAction) {
        super(x, y, width, height, title, pressedAction);
    }

    public CleanSynthesizerButton(int x, int y, int width, int height, ITextComponent title, Button.IPressable pressedAction, Button.ITooltip onTooltip) {
        super(x, y, width, height, title, pressedAction, onTooltip);
    }

    @Override
    public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.fontRenderer;
        minecraft.getTextureManager().bindTexture(new ResourceLocation(LeagueCraft.MOD_ID, "textures/gui/trash_button.png"));
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        if(this.isHovered()) {
            this.blit(matrixStack, this.x, this.y, 20, 0, this.width, this.height);
        }
        else{
            this.blit(matrixStack, this.x, this.y, 0, 0, this.width, this.height);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            if (this.visible) {
                this.renderWidget(matrixStack, mouseX, mouseY, partialTicks);
            }
        }
    }
}
