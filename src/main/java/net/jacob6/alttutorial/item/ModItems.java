package net.jacob6.alttutorial.item;

import net.jacob6.alttutorial.MCAltTutorial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, MCAltTutorial.MODID);


    // public static final RegistryObject<Item> PICKAXE_BLOCK_ITEM = ITEMS.register("pickaxe_block", 
    //     () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }


}
