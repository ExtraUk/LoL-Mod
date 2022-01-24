package com.extra.leaguecraft.tileentity;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.block.ModBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, LeagueCraft.MOD_ID);

    public static RegistryObject<TileEntityType<HextechChargerTile>> HEXTECH_CHARGER_TILE = TILE_ENTITIES.register("hextech_charger_tile",
            () -> TileEntityType.Builder.create(
                    HextechChargerTile::new, ModBlocks.HEXTECH_CHARGRER.get()).build(null));

    public static void register(IEventBus eventBus){
        TILE_ENTITIES.register(eventBus);
    }
}
