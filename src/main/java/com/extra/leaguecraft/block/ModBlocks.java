package com.extra.leaguecraft.block;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.block.custom.HextechChargerBlock;
import com.extra.leaguecraft.effect.ModEffects;
import com.extra.leaguecraft.item.ModItemGroup;
import com.extra.leaguecraft.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LeagueCraft.MOD_ID);

    public static final RegistryObject<Block> SILVER_ORE = registerBlock("silver_ore",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
            .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(5f)));

    public static final RegistryObject<Block> SHIMMER_FLOWER = registerBlock("shimmer_flower",
            () -> new FlowerBlock(Effects.REGENERATION, 4, AbstractBlock.Properties.from(Blocks.DANDELION).setLightLevel(value -> 2)));

    public static final RegistryObject<Block> HEXTECH_CHARGRER = registerBlock("hextech_charger",
            () -> new HextechChargerBlock(AbstractBlock.Properties.create(Material.IRON)));


    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP)));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
