package com.extra.leaguecraft.gui;

import com.extra.leaguecraft.LeagueCraft;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

import java.awt.*;

public class SynthesizerContainerButton extends Button {


    int shimmerAmount = 0;
    int brewAmount = 0;
    Effect effect1 = null;
    Effect effect2 = null;
    Effect effect3 = null;
    int amp1 = 0;
    int amp2 = 0;
    int amp3 = 0;

    public int getShimmer() {
        return shimmerAmount;
    }

    public int getBrew() {
        return brewAmount;
    }

    public Effect getEffect1() {
        return effect1;
    }

    public Effect getEffect2() {
        return effect2;
    }

    public Effect getEffect3() {
        return effect3;
    }

    public int getAmp1() {
        return amp1;
    }

    public int getAmp2() {
        return amp2;
    }

    public int getAmp3() {
        return amp3;
    }

    public SynthesizerContainerButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction,
                                      ITooltip iTooltip, int shimmerAmount, int brewAmount, Effect ef1, Effect ef2, Effect ef3, int amp1, int amp2, int amp3) {
        super(x, y, width, height, title, pressedAction, iTooltip);

        this.shimmerAmount = shimmerAmount;
        this.brewAmount = brewAmount;
        this.effect1 = ef1;
        this.effect2 = ef2;
        this.effect3 = ef3;
        this.amp1 = amp1;
        this.amp2 = amp2;
        this.amp3 = amp3;
    }


    @Override
    public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        if(this.isHovered()) {
            this.renderToolTip(matrixStack, mouseX, mouseY);

        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            this.renderWidget(matrixStack, mouseX, mouseY, partialTicks);
        }
    }
}
