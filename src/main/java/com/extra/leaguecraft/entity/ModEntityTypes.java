package com.extra.leaguecraft.entity;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.entity.custom.BrackernEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, LeagueCraft.MOD_ID);

    public static final RegistryObject<EntityType<BrackernEntity>> BRACKERN = ENTITY_TYPES.register("brackern",
            () -> EntityType.Builder.create(BrackernEntity::new, EntityClassification.MONSTER).size(1f, 1f)
                    .build(new ResourceLocation(LeagueCraft.MOD_ID, "brackern").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
