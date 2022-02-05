package com.extra.leaguecraft.mixin.render;

import com.extra.leaguecraft.render.curio.CurioRenderers;
import com.extra.leaguecraft.render.curio.renderer.GloveCurioRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraftforge.fml.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

    @Inject(method = "renderLeftArm", at = @At("TAIL"))
    private void renderLeftGlove(MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, AbstractClientPlayerEntity player, CallbackInfo callbackInfo) {
        renderArm(matrixStack, buffer, light, player, HandSide.LEFT);
    }

    @Inject(method = "renderRightArm", at = @At("TAIL"))
    private void renderRightGlove(MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, AbstractClientPlayerEntity player, CallbackInfo callbackInfo) {
        renderArm(matrixStack, buffer, light, player, HandSide.RIGHT);
    }

    @Unique
    private static void renderArm(MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, AbstractClientPlayerEntity player, HandSide handSide) {
        Hand hand = handSide == player.getPrimaryHand() ? Hand.MAIN_HAND : Hand.OFF_HAND;

        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
            ICurioStacksHandler stacksHandler = handler.getCurios().get(SlotTypePreset.HANDS.getIdentifier());
            if (stacksHandler != null) {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                IDynamicStackHandler cosmeticStacks = stacksHandler.getCosmeticStacks();

                for (int slot = hand == Hand.MAIN_HAND ? 0 : 1; slot < stacks.getSlots(); slot += 2) {
                    ItemStack stack = cosmeticStacks.getStackInSlot(slot);
                    if (stack.isEmpty() && stacksHandler.getRenders().get(slot)) {
                        stack = stacks.getStackInSlot(slot);
                    }

                    GloveCurioRenderer renderer = CurioRenderers.getGloveRenderer(stack);
                    if (renderer != null) {
                        renderer.renderFirstPersonArm(matrixStack, buffer, light, player, handSide, stack.hasEffect());
                    }
                }
            }
        });
    }
}