package net.jacob6.alttutorial.worldgen.feature;

import net.jacob6.alttutorial.MCAltTutorial;
import net.jacob6.alttutorial.worldgen.ores.ModOrePlacement;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = 
        DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MCAltTutorial.MODID);

    public static final RegistryObject<PlacedFeature> PICKAXE_BLOCK_ORE_PLACED = PLACED_FEATURES.register("pickaxe_block_placed",
        () -> new PlacedFeature((Holder<ConfiguredFeature<?,?>>)(Holder<? extends ConfiguredFeature<?,?>>)
        ModConfiguredFeatures.PICKAXE_BLOCK_ORE, ModOrePlacement.commonOrePlacement(7, 
            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static void register(IEventBus eventBus){
        PLACED_FEATURES.register(eventBus);
    }

}
