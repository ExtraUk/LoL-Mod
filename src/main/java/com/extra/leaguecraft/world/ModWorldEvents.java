package com.extra.leaguecraft.world;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.world.gen.ModFlowerGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LeagueCraft.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event){
        ModFlowerGeneration.generateFlowers(event);
    }
}
