package net.jacob6.alttutorial.worldgen.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.jacob6.alttutorial.MCAltTutorial;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomeModifier {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MCAltTutorial.MODID);

    public static RegistryObject<Codec<OverworldBiomeModifier>> OVERWORLD_MODIFIER = BIOME_MODIFIERS.register("overworld", () ->
        RecordCodecBuilder.create(builder -> builder.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(OverworldBiomeModifier::biomes),
            PlacedFeature.CODEC.fieldOf("feature").forGetter(OverworldBiomeModifier::feature)
        ).apply(builder, OverworldBiomeModifier::new)));

    public static void register(IEventBus eventBus){
        BIOME_MODIFIERS.register(eventBus);
    }
}
