package com.extra.leaguecraft.event;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.effect.ModEffects;
import com.extra.leaguecraft.item.ModArmorMaterial;
import com.extra.leaguecraft.item.ModItems;
import com.extra.leaguecraft.item.custom.ModArmorItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraftforge.fml.client.gui.GuiUtils.drawTexturedModalRect;

@Mod.EventBusSubscriber(modid= LeagueCraft.MOD_ID, value= Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public void renderGameOverlay(RenderGameOverlayEvent.Pre event){

        if(event.getType().equals(ElementType.ALL)){
            Minecraft mc = Minecraft.getInstance();
            ClientPlayerEntity player = mc.player;
            World world = mc.world;
            GlStateManager.enableBlend();

            renderShimmerOverlay(event, player);
            renderHextechGauntletDashGUI(event, player);
            renderTurboChemtankGUI(event, player);

            GlStateManager.disableBlend();

        }
    }



    public void renderShimmerOverlay(RenderGameOverlayEvent.Pre event, ClientPlayerEntity player){
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.2F);

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



    public void renderHextechGauntletDashGUI(RenderGameOverlayEvent.Pre event, ClientPlayerEntity player){
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        ICuriosHelper helper = CuriosApi.getCuriosHelper();
        if(helper.findEquippedCurio(ModItems.HEXTECH_GAUNTLET.get(), player).equals(Optional.empty())) return;
        LazyOptional<IItemHandlerModifiable> equippedCurios = helper.getEquippedCurios(player);
        final AtomicBoolean gauntletEquipped = new AtomicBoolean(false);
        final AtomicInteger cooldownRemaining = new AtomicInteger(0);
        equippedCurios.ifPresent(h -> {
            for(int i = 0; i < h.getSlots(); i++){
                if(h.getStackInSlot(i).getItem() == ModItems.HEXTECH_GAUNTLET.get()){
                    gauntletEquipped.set(true);

                    if(h.getStackInSlot(i).getOrCreateTag().contains("Cooldown")) {
                        cooldownRemaining.set(h.getStackInSlot(i).getTag().getInt("Cooldown"));
                        break;
                    }
                }
            }
        });

        if(gauntletEquipped.get()){
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(LeagueCraft.MOD_ID, "textures/gui/gui_icons.png"));
            drawTexturedModalRect(event.getMatrixStack(), event.getWindow().getScaledWidth() - 45,
                    event.getWindow().getScaledHeight() - 20,
                    0, 0, 13, 14, 0.3F);


            int cd = cooldownRemaining.get();
            drawTexturedModalRect(event.getMatrixStack(), event.getWindow().getScaledWidth() - 45,
                    (int)(event.getWindow().getScaledHeight() - 7 - ((100 - cd) / 7.7)),
                    13, ((int) (13 - ((100 - cd) / 7.7))), 13, 14, 0.4F);
        }
    }

    public void renderTurboChemtankGUI(RenderGameOverlayEvent.Pre event, ClientPlayerEntity player){
        if(ModArmorItem.hasFullSuitOfArmorOn(player, ModArmorMaterial.TURBO_CHEMTANK)){
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(LeagueCraft.MOD_ID, "textures/gui/gui_icons.png"));
            drawTexturedModalRect(event.getMatrixStack(),
                    event.getWindow().getScaledWidth() - 25, event.getWindow().getScaledHeight() - 59,
                    26, 0, 18, 52, 0.4F);

            int shim = player.inventory.armorItemInSlot(2).getOrCreateTag().getInt("shimmer_load");
            drawTexturedModalRect(event.getMatrixStack(), event.getWindow().getScaledWidth() - 25,
                    (int)(event.getWindow().getScaledHeight() - (8 + (shim / 400))),
                    44, ((int) Math.ceil(51 - (shim / 400))), 18, 50, 0.4F);

        }
    }
}
//}
