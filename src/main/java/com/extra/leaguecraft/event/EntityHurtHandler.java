package com.extra.leaguecraft.event;

import com.extra.leaguecraft.item.ModItems;
import com.extra.leaguecraft.util.ModSoundEvents;
import com.sun.jna.platform.unix.X11;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityHurtHandler {

    @SubscribeEvent
    public void Event(LivingAttackEvent event) {

        gauntletOnAttackDischargeHandler(event);

        if(event.getSource().getDamageType().equals("FireWorldRune")){
            event.setCanceled(true);
            return;
        }
        else if(event.getSource().getDamageType().equals("ChemTechGrenade")){
            event.setCanceled(true);
            event.getEntity().attackEntityFrom(DamageSource.GENERIC, 8);
        }
        if(event.getEntity() instanceof PlayerEntity && event.getSource().isExplosion()){
            if(((PlayerEntity) event.getEntity()).getHeldItem(Hand.MAIN_HAND).getItem() == ModItems.WORLD_RUNE_EARTH.get()) {
                event.setCanceled(true);
            }
        }

    }


    private void gauntletOnAttackDischargeHandler(LivingAttackEvent event){
        if(event.getSource().getTrueSource() instanceof PlayerEntity){
            PlayerEntity player = ((PlayerEntity) event.getSource().getTrueSource());
            if(player.getHeldItem(Hand.MAIN_HAND) == ItemStack.EMPTY && player.getHeldItem(Hand.OFF_HAND) == ItemStack.EMPTY){
                ICuriosHelper helper = CuriosApi.getCuriosHelper();
                if(!helper.findEquippedCurio(ModItems.HEXTECH_GAUNTLET.get(), player).equals(Optional.empty()) || !helper.findEquippedCurio(ModItems.IRON_GAUNTLET.get(), player).equals(Optional.empty())){
                    AtomicInteger gauntletsCharged = new AtomicInteger();
                    gauntletsCharged.set(0);

                    LazyOptional<IItemHandlerModifiable> equippedCurios = helper.getEquippedCurios(player);
                    equippedCurios.ifPresent(h -> {
                        for(int i = 0; i < h.getSlots(); i++){
                            if(h.getStackInSlot(i).getItem() == ModItems.HEXTECH_GAUNTLET.get()){
                                ItemStack gauntlet = ModItems.HEXTECH_GAUNTLET.get().getDefaultInstance();
                                if(h.getStackInSlot(i).getDamage() < gauntlet.getMaxDamage()) {
                                    gauntlet.setDamage(h.getStackInSlot(i).getDamage() + 1);
                                    h.setStackInSlot(i, gauntlet);
                                    gauntletsCharged.incrementAndGet();
                                }
                            }
                            else if(h.getStackInSlot(i).getItem() == ModItems.IRON_GAUNTLET.get()){
                                ItemStack gauntlet = ModItems.IRON_GAUNTLET.get().getDefaultInstance();
                                gauntlet.setDamage(h.getStackInSlot(i).getDamage() + 1);
                                h.setStackInSlot(i, gauntlet);
                                gauntletsCharged.incrementAndGet();
                            }
                        }
                    });

                    if(gauntletsCharged.get() > 0){
                        player.world.playSound(player, new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ()), ModSoundEvents.HEXTECH_GAUNTLET_HIT.get(), SoundCategory.PLAYERS, 1F, 1F);
                    }
                }
            }
        }
    }

}
