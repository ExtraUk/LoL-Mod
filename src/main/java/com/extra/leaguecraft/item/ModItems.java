package com.extra.leaguecraft.item;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.entity.ModEntityTypes;
import com.extra.leaguecraft.item.custom.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LeagueCraft.MOD_ID);

    public static final RegistryObject<Item> LEAGUE_ICON = ITEMS.register("league_icon",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> VIAL = ITEMS.register("vial",
            () -> new Item(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP)));

    public static final RegistryObject<Item> SHIMMER_VIAL = ITEMS.register("shimmer_vial",
            () -> new ShimmerVial(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxStackSize(10).food(new Food.Builder().hunger(0).saturation(0).setAlwaysEdible().build())));

    public static final RegistryObject<Item> TURBO_CHEMTANK_HELMET = ITEMS.register("turbo_chemtank_helmet",
            () -> new ModArmorItem(ModArmorMaterial.TURBO_CHEMTANK, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(700)));

    public static final RegistryObject<Item> TURBO_CHEMTANK_CHEST = ITEMS.register("turbo_chemtank_chest",
            () -> new ModArmorItem(ModArmorMaterial.TURBO_CHEMTANK, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(700)));

    public static final RegistryObject<Item> TURBO_CHEMTANK_LEGS = ITEMS.register("turbo_chemtank_legs",
            () -> new ModArmorItem(ModArmorMaterial.TURBO_CHEMTANK, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(700)));

    public static final RegistryObject<Item> TURBO_CHEMTANK_BOOTS = ITEMS.register("turbo_chemtank_boots",
            () -> new ModArmorItem(ModArmorMaterial.TURBO_CHEMTANK, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(700)));

    public static final RegistryObject<Item> WORLD_RUNE_EARTH = ITEMS.register("world_rune_earth",
            () -> new WorldRuneEarth(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxStackSize(1)));

    public static final RegistryObject<Item> WORLD_RUNE_FIRE = ITEMS.register("world_rune_fire",
            () -> new WorldRuneFire(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxStackSize(1)));

    public static final RegistryObject<ModSpawnEggItem> BRACKERN_SPAWN_EGG = ITEMS.register("brackern_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.BRACKERN, 0x9b59b6, 0x3498db,
                    new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP)));

    public static final RegistryObject<Item> HEXTECH_CRYSTAL = ITEMS.register("hextech_crystal",
            () -> new Item(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(2048)));

    public static final RegistryObject<Item> HEXTECH_SWORD = ITEMS.register("hextech_sword",
            () -> new HextechSword());

    public static final RegistryObject<Item> HEXTECH_SWORD_BROKEN = ITEMS.register("hextech_sword_broken",
            () -> new Item(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxStackSize(1)));

    public static final RegistryObject<Item> HEXTECH_GAUNTLET = ITEMS.register("hextech_gauntlet",
            () -> new HextechGauntletItem(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(512)));

    public static final RegistryObject<Item> CHEM_BREW = ITEMS.register("chem_brew",
            () -> new ChemBrewItem(new Item.Properties().maxStackSize(10)));

    public static final RegistryObject<Item> CHEMTECH_GRENADE = ITEMS.register("chemtech_grenade",
            () -> new ChemTechGrenade(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxStackSize(16)));

    public static final RegistryObject<Item> SYNTHESIZER_RECIPE_BOOK = ITEMS.register("synthesizer_recipe_book",
            () -> new SynthesizerRecipeBook(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxStackSize(1)));

    public static final RegistryObject<Item> IRON_GAUNTLET = ITEMS.register("iron_gauntlet",
            () -> new IronGauntletItem(new Item.Properties().group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(512)));


    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot",
            () -> new Item(new Item.Properties()/*.group(ModItemGroup.LEAGUECRAFT_GROUP)*/));

    public static final RegistryObject<Item> BOLT = ITEMS.register("bolt",
            () -> new Bolt(new Item.Properties()/*.group(ModItemGroup.LEAGUECRAFT_GROUP)*/));

    public static final RegistryObject<Item> SILVER_BOLT = ITEMS.register("silver_bolt",
            () -> new SilverBolt(new Item.Properties()/*.group(ModItemGroup.LEAGUECRAFT_GROUP)*/));

    public static final RegistryObject<Item> MAGAZINE = ITEMS.register("magazine",
            () -> new Magazine(new Item.Properties()/*.group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(64)*/));

    public static final RegistryObject<Item> REPEATER = ITEMS.register("repeater",
            () -> new Repeater(new Item.Properties()/*.group(ModItemGroup.LEAGUECRAFT_GROUP).maxDamage(1024)*/));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
