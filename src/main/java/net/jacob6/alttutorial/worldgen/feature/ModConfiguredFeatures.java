package net.jacob6.alttutorial.worldgen.feature;

import java.util.List;

import net.jacob6.alttutorial.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class ModConfiguredFeatures {
    
    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_PICKAXE_BLOCK_ORES = List.of(
        OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.PICKAXE_BLOCK.get().defaultBlockState()));
    
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> PICKAXE_BLOCK_ORE = FeatureUtils.register("pickaxe_block_ore", 
        Feature.ORE, new OreConfiguration(OVERWORLD_PICKAXE_BLOCK_ORES, 3));
    
}
