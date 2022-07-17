package net.jacob6.alttutorial.entity;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.jacob6.alttutorial.MCAltTutorial;
// import net.jacob6.alttutorial.entity.custom.CraftingTableBlockEntity;

import java.util.List;
import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = 
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MCAltTutorial.MODID);

    // public static final RegistryObject<BlockEntityType<CraftingTableBlockEntity>> CRAFTING_TABLE_BLOCK_ENTITY = 
    //     BLOCK_ENTITIES.register("crafting_table", () -> BlockEntityType.Builder.of(CraftingTableBlockEntity::new, Blocks.CRAFTING_TABLE).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
