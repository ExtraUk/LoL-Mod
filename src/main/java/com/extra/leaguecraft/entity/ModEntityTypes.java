package com.extra.leaguecraft.entity;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.entity.custom.BlitzcrankEntity;
import com.extra.leaguecraft.entity.custom.BlitzcrankFistEntity;
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
            () -> EntityType.Builder.create(BrackernEntity::new, EntityClassification.MONSTER).size(1.0f, 1.5f)
                    .build(new ResourceLocation(LeagueCraft.MOD_ID, "brackern").toString()));

    public static final RegistryObject<EntityType<BlitzcrankEntity>> BLITZCRANK = ENTITY_TYPES.register("blitzcrank",
            () -> EntityType.Builder.create(BlitzcrankEntity::new, EntityClassification.CREATURE).size(2.0f, 3.0f)
                    .build(new ResourceLocation(LeagueCraft.MOD_ID, "blitzcrank").toString()));

    public static final RegistryObject<EntityType<BlitzcrankFistEntity>> BLITZCRANK_FIST = ENTITY_TYPES.register("blitzcrank_fist",
            () -> EntityType.Builder.<BlitzcrankFistEntity>create(BlitzcrankFistEntity::new, EntityClassification.MISC).size(1.0f,1.0f)
                    .setCustomClientFactory((spawnEntity, world) -> new BlitzcrankFistEntity(world))
                    .build(new ResourceLocation(LeagueCraft.MOD_ID, "blitzcrank_fist").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
