package com.extra.leaguecraft.event;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.entity.ModEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LeagueCraft.MOD_ID)
public class ModEvents  {

    @SubscribeEvent
    public static void onEntitySpawn(LivingSpawnEvent.CheckSpawn event){
        if(event.getEntity().getType().equals(ModEntityTypes.BRACKERN.get())){
            System.out.println(event.getY());
            if(event.getY() > 50){
                event.getEntity().remove();
            }
        }
    }

}
