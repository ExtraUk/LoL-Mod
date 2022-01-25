package com.extra.leaguecraft.container;

import com.extra.leaguecraft.LeagueCraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {

    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, LeagueCraft.MOD_ID);

    public static final RegistryObject<ContainerType<HextechChargerContainer>> HEXTECH_CHARGER_CONTAINER = CONTAINERS.register("hextech_charger_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new HextechChargerContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<HextechSynthesizerContainer>> HEXTECH_SYNTHESIZER_CONTAINER = CONTAINERS.register("hextech_synthesizer_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new HextechSynthesizerContainer(windowId, world, pos, inv, inv.player);
            })));

    public static void register(IEventBus eventBus){
        CONTAINERS.register(eventBus);
    }
}
