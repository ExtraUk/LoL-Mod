package com.extra.leaguecraft.screen;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.gui.CleanSynthesizerButton;
import com.extra.leaguecraft.gui.LevelUpButton;
import com.extra.leaguecraft.gui.LevelUpCloseButton;
import com.extra.leaguecraft.network.ModNetwork;
import com.extra.leaguecraft.network.ModPacketHandler;
import com.extra.leaguecraft.network.ServerBoundSynthUpdatePacket;
import com.extra.leaguecraft.network.message.InputMessage;
import com.extra.leaguecraft.network.message.LevelUpMessage;
import com.extra.leaguecraft.tileentity.HextechSynthesizerTile;
import com.extra.leaguecraft.util.ModAttributesUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class LevelUpScreen extends Screen {

    private final ResourceLocation GUI = new ResourceLocation(LeagueCraft.MOD_ID, "textures/gui/level_gui.png");

    public LevelUpScreen(ITextComponent titleIn) {
        super(titleIn);
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bindTexture(GUI);

        int x = (this.minecraft.getMainWindow().getScaledWidth()/2) - 127;
        int y = (this.minecraft.getMainWindow().getScaledHeight()/2) - 79;

        blit(matrixStack, x, y, 0, 0, 254, 158);

        this.addButton(new LevelUpCloseButton(x + 239, y + 6, 7, 6, new StringTextComponent(""), new Button.IPressable() {
            @Override
            public void onPress(Button button) {
                Minecraft.getInstance().currentScreen.closeScreen();
            }
        }));

        if(this.minecraft.player.getPersistentData().getInt("points") > 0){

            this.addButton(new LevelUpButton(x + 43, y + 127, 20, 20, new StringTextComponent(""), new Button.IPressable() {
                @Override
                public void onPress(Button button) {
                    Minecraft.getInstance().player.getPersistentData().putInt("points", Minecraft.getInstance().player.getPersistentData().getInt("points") - 1);
                    Minecraft.getInstance().player.getPersistentData().putInt("strength", Minecraft.getInstance().player.getPersistentData().getInt("strength") + 1);
                    ModNetwork.CHANNEL.sendToServer(new LevelUpMessage("strength"));
                    closeScreen();
                    Minecraft.getInstance().displayGuiScreen(new LevelUpScreen(new StringTextComponent("")));
                }
            }));

            this.addButton(new LevelUpButton(x + 117, y + 127, 20, 20, new StringTextComponent(""), new Button.IPressable() {
                @Override
                public void onPress(Button button) {
                    Minecraft.getInstance().player.getPersistentData().putInt("points", Minecraft.getInstance().player.getPersistentData().getInt("points") - 1);
                    Minecraft.getInstance().player.getPersistentData().putInt("health", Minecraft.getInstance().player.getPersistentData().getInt("health") + 1);
                    ModNetwork.CHANNEL.sendToServer(new LevelUpMessage("health"));
                    closeScreen();
                    Minecraft.getInstance().displayGuiScreen(new LevelUpScreen(new StringTextComponent("")));
                }
            }));

            this.addButton(new LevelUpButton(x + 191, y + 127, 20, 20, new StringTextComponent(""), new Button.IPressable() {
                @Override
                public void onPress(Button button) {
                    Minecraft.getInstance().player.getPersistentData().putInt("points", Minecraft.getInstance().player.getPersistentData().getInt("points") - 1);
                    Minecraft.getInstance().player.getPersistentData().putInt("magic", Minecraft.getInstance().player.getPersistentData().getInt("magic") + 1);
                    ModNetwork.CHANNEL.sendToServer(new LevelUpMessage("magic"));
                    closeScreen();
                    Minecraft.getInstance().displayGuiScreen(new LevelUpScreen(new StringTextComponent("")));

                }
            }));

        }
        else{
            blit(matrixStack, x + 43, y + 127, 40, 236, 20, 20);
            blit(matrixStack, x + 117, y + 127, 40, 236, 20, 20);
            blit(matrixStack, x + 191, y + 127, 40, 236, 20, 20);
        }

        int maxXp = 100;
        int width = 0;
        if(this.minecraft.player.getPersistentData().contains("playerLevel")){
            maxXp = ModAttributesUtil.getLevelXp(this.minecraft.player.getPersistentData().getInt("playerLevel"));
        }
        if(this.minecraft.player.getPersistentData().contains("xp")){
            int xp = this.minecraft.player.getPersistentData().getInt("xp");
            int aux = xp * 202;
            width = aux/maxXp;
        }

        blit(matrixStack, x + 26, y + 16, 0, 231, width, 5);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
