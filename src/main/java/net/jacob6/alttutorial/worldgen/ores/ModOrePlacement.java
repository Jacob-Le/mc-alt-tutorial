package net.jacob6.alttutorial.worldgen.ores;

import java.util.List;

import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ModOrePlacement {
    public static List<PlacementModifier> orePlacement(PlacementModifier mod1, PlacementModifier mod2){
        return List.of(mod1, InSquarePlacement.spread(), mod2, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int numVeins, PlacementModifier mod){
        return orePlacement(CountPlacement.of(numVeins), mod);
    }

    public static List<PlacementModifier> rareOreModifier(int rarity, PlacementModifier mod){
        return orePlacement(RarityFilter.onAverageOnceEvery(rarity), mod);
    }
}
