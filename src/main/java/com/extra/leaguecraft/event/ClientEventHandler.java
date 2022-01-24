package com.extra.leaguecraft.event;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.effect.ModEffects;
import com.extra.leaguecraft.item.ModArmorMaterial;
import com.extra.leaguecraft.item.ModItems;
import com.extra.leaguecraft.item.custom.ModArmorItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import static net.minecraftforge.fml.client.gui.GuiUtils.drawTexturedModalRect;

@Mod.EventBusSubscriber(modid= LeagueCraft.MOD_ID, value= Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public void renderGameOverlay(RenderGameOverlayEvent.Post event){
        if(event.getType().equals(ElementType.ALL)){
            Minecraft mc = Minecraft.getInstance();
            ClientPlayerEntity player = mc.player;
            World world = mc.world;

            //GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.1F);
            //GL11.glDisable(GL11.GL_LIGHTING);
            GL15.glColor4f(1.0f, 1.0f, 1.0f, 0.4f);
            if(ModArmorItem.hasFullSuitOfArmorOn(player, ModArmorMaterial.TURBO_CHEMTANK)){
                if(player.inventory.armorItemInSlot(2).getOrCreateTag().getBoolean("active")){
                    Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(LeagueCraft.MOD_ID, "textures/gui/shimmer_overlay.png"));
                    drawTexturedModalRect(event.getMatrixStack(), 0, 0, 0, 0, event.getWindow().getWidth(), event.getWindow().getHeight(), 0.1f);
                }
            }
            else if(player.getActivePotionEffect(ModEffects.SHIMMER.get()) != null){
                Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(LeagueCraft.MOD_ID, "textures/gui/shimmer_overlay.png"));
                drawTexturedModalRect(event.getMatrixStack(), 0, 0, 0, 0, event.getWindow().getWidth(), event.getWindow().getHeight(), 0.1f);
            }
        }
    }
}
//}
