package com.extra.leaguecraft.item;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.item.custom.Bolt;
import com.extra.leaguecraft.item.custom.Magazine;
import com.extra.leaguecraft.item.custom.SilverBolt;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LeagueCraft.MOD_ID);

    public static final RegistryObject<Item> LEAGUE_ICON = ITEMS.register("league_icon",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot",
            () -> new Item(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP)));

    public static final RegistryObject<Item> BOLT = ITEMS.register("bolt",
            () -> new Bolt(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP)));

    public static final RegistryObject<Item> SILVER_BOLT = ITEMS.register("silver_bolt",
            () -> new SilverBolt(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP)));

    public static final RegistryObject<Item> MAGAZINE = ITEMS.register("magazine",
            () -> new Magazine(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(64)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
