package com.extra.leaguecraft;

import com.extra.leaguecraft.block.ModBlocks;
import com.extra.leaguecraft.effect.ModEffects;
import com.extra.leaguecraft.event.EntityHurtHandler;
import com.extra.leaguecraft.event.KeyInit;
import com.extra.leaguecraft.item.ModItems;
import com.extra.leaguecraft.network.ModNetwork;
import com.extra.leaguecraft.recipes.TurboChemtankChestRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.text.html.parser.Entity;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LeagueCraft.MOD_ID)
public class LeagueCraft
{

    public static final String MOD_ID = "leaguecraft";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    private static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    public static final RegistryObject<TurboChemtankChestRecipe.Serializer> TURBO_CHEMTANK_CHEST_RECIPE = RECIPE_SERIALIZERS.register("turbo_chemtank_chest", TurboChemtankChestRecipe.Serializer::new);

    public LeagueCraft() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::commonSetup);

        ModEffects.EFFECTS.register(eventBus);
        ModEffects.POTIONS.register(eventBus);
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.register(this);

        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EntityHurtHandler());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        KeyInit.register(event);
        event.enqueueWork( () -> {
            RenderTypeLookup.setRenderLayer(ModBlocks.SHIMMER_FLOWER.get(), RenderType.getCutout());
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

    public void commonSetup(final FMLCommonSetupEvent event){
        ModNetwork.init();
    }
}
