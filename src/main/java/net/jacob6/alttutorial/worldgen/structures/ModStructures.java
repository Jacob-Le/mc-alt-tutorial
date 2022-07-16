package net.jacob6.alttutorial.worldgen.structures;

import net.jacob6.alttutorial.MCAltTutorial;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {

    /**
     * We are using the Deferred Registry system to register our structure as this is the preferred way on Forge.
     * This will handle registering the base structure for us at the correct time so we don't have to handle it ourselves.
     */
    public static final DeferredRegister<StructureType<?>> STRUCTURES =
        DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, MCAltTutorial.MODID);

    /**
     * Registers the base structure itself and sets what its path is. In this case,
     * this base structure will have the resourcelocation of alttutorial:mine_marker
     */
    public static final RegistryObject<StructureType<MineMarkerStructure>> MINE_MARKER = 
        STRUCTURES.register("mine_marker", () -> () -> MineMarkerStructure.CODEC);


    public static void register(IEventBus eventBus){
        STRUCTURES.register(eventBus);
    }
}
