package net.jacob6.alttutorial;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;

import net.jacob6.alttutorial.block.ModBlocks;
import net.jacob6.alttutorial.entity.ModBlockEntities;
import net.jacob6.alttutorial.event.ModEvents;
import net.jacob6.alttutorial.item.ModItems;
import net.jacob6.alttutorial.worldgen.biome.ModBiomeModifier;
import net.jacob6.alttutorial.worldgen.feature.ModPlacedFeatures;
import net.jacob6.alttutorial.worldgen.structures.ModStructures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MCAltTutorial.MODID)
public class MCAltTutorial
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "alttutorial";
    
    public static DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
        DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MODID);
    
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public MCAltTutorial()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);
        ModBiomeModifier.register(modEventBus);
        ModStructures.register(modEventBus);

        ModEvents events = new ModEvents(); // Initialize event handler instance

        modEventBus.register(events); // Register events

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    private void commonSetup(final FMLCommonSetupEvent event)
    {
        
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            Messages.register();
        }
    }
}
