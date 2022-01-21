package com.extra.leaguecraft.event;

import com.extra.leaguecraft.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityHurtHandler {

    @SubscribeEvent
    public void Event(LivingAttackEvent event) {
        if(event.getEntity() instanceof PlayerEntity && event.getSource().isExplosion()){
            if(((PlayerEntity) event.getEntity()).getHeldItem(Hand.MAIN_HAND).getItem() == ModItems.WORLD_RUNE.get()) {
                event.setCanceled(true);
            }
        }

    }

}
