package com.extra.leaguecraft.event;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.entity.ModEntityTypes;
import com.extra.leaguecraft.entity.custom.BlitzcrankEntity;
import com.extra.leaguecraft.entity.custom.BrackernEntity;
import com.extra.leaguecraft.item.custom.ModSpawnEggItem;
import net.minecraft.entity.EntityType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = LeagueCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.BRACKERN.get(), BrackernEntity.setCustomAttributes().create());
        event.put(ModEntityTypes.BLITZCRANK.get(), BlitzcrankEntity.setCustomAttributes().create());
    }

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        ModSpawnEggItem.initSpawnEggs();
    }

}
