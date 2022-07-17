package net.jacob6.alttutorial.block;

import net.jacob6.alttutorial.MCAltTutorial;
import net.jacob6.alttutorial.block.custom.AltCraftingTableBlock;
import net.jacob6.alttutorial.block.custom.PickaxeBlock;
import net.jacob6.alttutorial.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks{
    public static final DeferredRegister<Block> BLOCKS = 
        DeferredRegister.create(ForgeRegistries.BLOCKS, MCAltTutorial.MODID);

    public static final DeferredRegister<Block> VANILLA_BLOCKS = 
        DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");

    public static final RegistryObject<Block> PICKAXE_BLOCK = registerBlock("pickaxe_block", 
        () -> new PickaxeBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), CreativeModeTab.TAB_MISC);


    // Replace crafting tables
    // public static final RegistryObject<Block> CRAFTING_TABLE = registerVanillaBlock("crafting_table", 
    //     () -> new AltCraftingTableBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)), 
    //     CreativeModeTab.TAB_BUILDING_BLOCKS);

    
    // public static final RegistryObject<Block> BEDROCK = registerVanillaBlock("bedrock", 
    //     () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK)), CreativeModeTab.TAB_BUILDING_BLOCKS);


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block){
        return BLOCKS.register(name, block);
    }

    public static <T extends Block> RegistryObject<T> registerVanillaBlock(String name, Supplier<T> block, CreativeModeTab tab)
    {
        RegistryObject<T> toReturn = VANILLA_BLOCKS.register(name, block);
        // ModItems.VANILLA_ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
        VANILLA_BLOCKS.register(eventBus);
    }
}