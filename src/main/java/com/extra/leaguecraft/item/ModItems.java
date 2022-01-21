package com.extra.leaguecraft.item;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.item.custom.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
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

    public static final RegistryObject<Item> REPEATER = ITEMS.register("repeater",
            () -> new Repeater(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(1024)));

    public static final RegistryObject<Item> SHIMMER_VIAL = ITEMS.register("shimmer_vial",
            () -> new ShimmerVial(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxStackSize(9).food(new Food.Builder().hunger(0).saturation(0).setAlwaysEdible().build())));

    public static final RegistryObject<Item> TURBO_CHEMTANK_HELMET = ITEMS.register("turbo_chemtank_helmet",
            () -> new ModArmorItem(ModArmorMaterial.TURBO_CHEMTANK, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(700)));

    public static final RegistryObject<Item> TURBO_CHEMTANK_CHEST = ITEMS.register("turbo_chemtank_chest",
            () -> new ModArmorItem(ModArmorMaterial.TURBO_CHEMTANK, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(700)));

    public static final RegistryObject<Item> TURBO_CHEMTANK_LEGS = ITEMS.register("turbo_chemtank_legs",
            () -> new ModArmorItem(ModArmorMaterial.TURBO_CHEMTANK, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(700)));

    public static final RegistryObject<Item> TURBO_CHEMTANK_BOOTS = ITEMS.register("turbo_chemtank_boots",
            () -> new ModArmorItem(ModArmorMaterial.TURBO_CHEMTANK, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(700)));

    public static final RegistryObject<Item> WORLD_RUNE = ITEMS.register("world_rune",
            () -> new WorldRune(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxStackSize(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
