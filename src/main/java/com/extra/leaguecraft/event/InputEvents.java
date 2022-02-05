package com.extra.leaguecraft.event;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.item.ModItems;
import com.extra.leaguecraft.network.ModNetwork;
import com.extra.leaguecraft.network.message.InputMessage;
import com.extra.leaguecraft.util.ModSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@EventBusSubscriber(modid = LeagueCraft.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event){
        Minecraft mc = Minecraft.getInstance();
        if(mc.world == null) return;
        onInput(mc, event.getKey(), event.getAction());
    }

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseInputEvent event){
        Minecraft mc = Minecraft.getInstance();
        if(mc.world == null) return;
        onInput(mc, event.getButton(), event.getAction());
    }

    private static void onInput(Minecraft mc, int key, int action){
        if(mc.currentScreen == null && KeyInit.activateKey.isPressed()){
            ModNetwork.CHANNEL.sendToServer(new InputMessage(key));
        }

        if(mc.currentScreen == null && key == 1){
            gauntletDash(mc, key);
        }
    }


    public static void gauntletDash(Minecraft mc, int key){
        if(mc.player.getHeldItem(Hand.MAIN_HAND) == ItemStack.EMPTY && mc.player.getHeldItem(Hand.OFF_HAND) == ItemStack.EMPTY) {
            ICuriosHelper helper = CuriosApi.getCuriosHelper();
            if (!helper.findEquippedCurio(ModItems.HEXTECH_GAUNTLET.get(), mc.player).equals(Optional.empty())) {

                AtomicBoolean flag = new AtomicBoolean();
                flag.set(true);
                AtomicBoolean flag2 = new AtomicBoolean();
                flag.set(false);
                LazyOptional<IItemHandlerModifiable> equippedCurios = helper.getEquippedCurios(mc.player);
                equippedCurios.ifPresent(h -> {
                            for (int i = 0; i < h.getSlots(); i++) {
                                if (h.getStackInSlot(i).getItem() == ModItems.HEXTECH_GAUNTLET.get()) {
                                    if (h.getStackInSlot(i).getDamage() <= ModItems.HEXTECH_GAUNTLET.get().getMaxDamage(h.getStackInSlot(i)) - 10) {
                                        flag.set(false);
                                    }
                                    if(h.getStackInSlot(i).getOrCreateTag().contains("Cooldown")){
                                        if(h.getStackInSlot(i).getTag().getInt("Cooldown") > 0){
                                            flag2.set(true);
                                        }
                                    }

                                }
                            }
                });
                if(flag.get() || flag2.get()) return;

                float yaw = mc.player.rotationYaw;
                float pitch = mc.player.rotationPitch;

                Vector3d eyePosition = mc.player.getEyePosition(1.0F);
                float f2 = MathHelper.cos(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
                float f3 = MathHelper.sin(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
                float f4 = -MathHelper.cos(-pitch * ((float) Math.PI / 180F));
                float f5 = MathHelper.sin(-pitch * ((float) Math.PI / 180F));
                float f6 = f3 * f4;
                float f7 = f2 * f4;

                mc.player.addVelocity(f6 * 0.75, (f5 * 0.75) + 0.2, f7 * 0.75);
                mc.player.world.playSound(mc.player, new BlockPos(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ()), ModSoundEvents.HEXTECH_GAUNTLET_DASH.get(), SoundCategory.PLAYERS, 1F, 1F);

                ModNetwork.CHANNEL.sendToServer(new InputMessage(key));
            }
        }
    }
}
