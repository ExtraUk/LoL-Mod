package com.extra.leaguecraft.screen;

import com.extra.leaguecraft.LeagueCraft;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class SynthesizerRecipeBookScreen extends Screen {

    private final ResourceLocation GUI = new ResourceLocation(LeagueCraft.MOD_ID, "textures/gui/synthesizer_recipe_book_gui.png");

    public SynthesizerRecipeBookScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bindTexture(GUI);

        blit(matrixStack, (this.minecraft.getMainWindow().getScaledWidth()/2) - 73, (this.minecraft.getMainWindow().getScaledHeight()/2) - 90, 0, 0, 146, 180);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
