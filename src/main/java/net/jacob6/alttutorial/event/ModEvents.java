package net.jacob6.alttutorial.event;

import net.jacob6.alttutorial.MCAltTutorial;
import net.jacob6.alttutorial.tutorial.ModTutorial;
import net.jacob6.alttutorial.tutorial.network.ModTutorialStatusManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.client.event.ScreenEvent.DrawScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = MCAltTutorial.MODID)
public class ModEvents {

    // On 2 items gotten - display toast for swapping active item in hand
    @SubscribeEvent
    public static void onItemsGotten(ItemPickupEvent event){
        if(!event.getEntity().level.isClientSide){
            Player player = event.getPlayer();
            // When the player has enough items, handle the event
            if(player.getInventory().items.size() >= 1){
                ModTutorial.promptSwapItems();
            }
        }
    }

    // Check to see if the inventory has been opened - display click to drag hint
    @SubscribeEvent
    public static void onInventoryOpen(ScreenOpenEvent event){
        // Screen open is always client side
        if(event.getScreen() instanceof InventoryScreen inventory){
            ModTutorial.promptCraftDragItem(true);
            System.out.println(String.format("(%d, %d) vs (%d, %d)", 
                inventory.width, inventory.height,
                inventory.getXSize(), inventory.getYSize()));
        }else if(event.getScreen() instanceof CraftingScreen crafting){
            ModTutorial.promptCraftDragItem(false);
            System.out.println(String.format("(%d, %d) vs (%d, %d)", 
                crafting.width, crafting.height,
                crafting.getXSize(), crafting.getYSize()));
        }
    }

    @SubscribeEvent
    public static void onInventoryClose(PlayerContainerEvent.Close event){
        if(event.getContainer() instanceof InventoryMenu || event.getContainer() instanceof CraftingMenu){
            ModTutorial.closeVignettes();
        }
    }

    // Remove the drag to craft toast when the item is crafted
    @SubscribeEvent
    public static void onInitialCraftItem(ItemCraftedEvent event){
        if(!event.getEntity().level.isClientSide()){
            ModTutorial.promptCraftDone();
        }
    }

    // Event for placing a block (on crafting table get)
    @SubscribeEvent
    public static void onCraftingTableGet(ItemCraftedEvent event){
        if(!event.getEntity().level.isClientSide()){
            if(event.getCrafting().getItem() == Items.CRAFTING_TABLE){
                ModTutorial.promptPlaceBlockHint();
            }
        }
    }

    // On crafting table place - prompt the player to use the crafting table
    @SubscribeEvent
    public static void onCraftingTablePlace(EntityPlaceEvent event){
        if(!event.getEntity().level.isClientSide()){
            // Check if the block is a crafting table block
            if(event.getPlacedBlock().getBlock() == Blocks.CRAFTING_TABLE){
                ModTutorial.promptAccessEvent(event.getBlockSnapshot());
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickCraftingTable(RightClickBlock event){
        if(!event.getWorld().isClientSide()){
            BlockState state = event.getWorld().getBlockState(event.getHitVec().getBlockPos());
            if(state.getBlock() == Blocks.CRAFTING_TABLE){
                ModTutorial.promptAccessDone();
            }
        }
    }

    // Event after a block that is broken with insufficient tools
    @SubscribeEvent
    public static void onInsufficientToolBreak(BreakEvent event){        
        Player player = event.getPlayer();
        if(!player.level.isClientSide()){

            // Code copied from vanilla Item class - calculate what block the player is looking at
            float f = player.getXRot();
            float f1 = player.getYRot();
            Vec3 vec3 = player.getEyePosition();
            float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
            float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
            float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
            float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
            float f6 = f3 * f4;
            float f7 = f2 * f4;
            double d0 = player.getReachDistance();
            Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
            
            // Get the block given the coordinates
            BlockHitResult hitResult = player.level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
            BlockState bState = player.level.getBlockState(hitResult.getBlockPos());

            // Check if the player has the correct tool they need in order for items to drop
            if(!player.hasCorrectToolForDrops(bState)){
                ModTutorial.promptGetBetterToolsHint();
            }
        }
    }

    @SubscribeEvent
    public static void onPostDrawOverlay(DrawScreenEvent.Post event){
        if(event.getScreen() instanceof InventoryScreen inventory){
            ModTutorial.handleVignetteDraw(inventory.getGuiLeft(), inventory.getGuiTop(), inventory.getXSize(), inventory.getYSize());
        }else if(event.getScreen() instanceof CraftingScreen crafting){
            ModTutorial.handleVignetteDraw(crafting.getGuiLeft(), crafting.getGuiTop(), crafting.getXSize(), crafting.getYSize());
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event){
        if(event.phase == TickEvent.Phase.START){
            return;
        }
        // Check to see if the matrix is filled with an item
        if(Minecraft.getInstance().screen instanceof InventoryScreen inventory){
            InventoryMenu menu = inventory.getMenu();
            if(menu.getSlot(menu.getResultSlotIndex()).getItem() != ItemStack.EMPTY){
                ModTutorial.promptCraftToInventory(true);
            }
        }else if(Minecraft.getInstance().screen instanceof CraftingScreen crafting){
            CraftingMenu menu = crafting.getMenu();
            if(menu.getSlot(menu.getResultSlotIndex()).getItem() != ItemStack.EMPTY){
                ModTutorial.promptCraftToInventory(false);
            }
        }
    }

    // Handle the world tick
    @SubscribeEvent
    public static void onWorldTick(WorldTickEvent event){
        if (event.world.isClientSide()){ // Check if is client side
            return;
        }
        if (event.phase == TickEvent.Phase.START){
            return;
        }
        ModTutorial.tick();
    }

    // Handle the server tick
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent event){
        if (event.phase == TickEvent.Phase.START){
            return;
        }
        ModTutorialStatusManager manager = ModTutorialStatusManager.get(event.getServer());
        manager.tick(event.getServer());
    }
}
