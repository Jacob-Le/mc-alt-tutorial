package net.jacob6.alttutorial.event;

import net.jacob6.alttutorial.MCAltTutorial;
import net.jacob6.alttutorial.Messages;
import net.jacob6.alttutorial.block.custom.PickaxeBlock;
import net.jacob6.alttutorial.tutorial.ModTutorial;
import net.jacob6.alttutorial.tutorial.network.ModTutorialStatusManager;
import net.jacob6.alttutorial.tutorial.network.packets.DevExportStatsPacket;
import net.jacob6.alttutorial.tutorial.network.packets.DevResetStatsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.jfr.event.WorldLoadFinishedEvent;
import net.minecraft.world.entity.EntityEvent;
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
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemSmeltedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.event.level.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = MCAltTutorial.MODID)
public class ModEvents {

    // On 2 items gotten - display toast for swapping active item in hand
    @SubscribeEvent
    public static void onItemsGotten(ItemPickupEvent event){
        if(!event.getEntity().level.isClientSide){
            Player player = event.getEntity();
            // When the player has enough items, handle the event
            if(player.getInventory().items.size() >= 1){
                ModTutorial.promptSwapItems();
            }

            // Check if item is a stone pickaxe
            if(event.getStack().getItem() == Items.STONE_PICKAXE){
                ModTutorial.recordCraftedItem(event.getStack().getItem());
            }
        }
    }

    // Check to see if the inventory has been opened - display click to drag hint
    @SubscribeEvent
    public static void onInventoryOpen(ScreenEvent.Opening event){
        // Screen open is always client side
        if(event.getScreen() instanceof InventoryScreen inventory){
            ModTutorial.promptCraftDragItem(true);
        }else if(event.getScreen() instanceof CraftingScreen crafting){
            ModTutorial.promptCraftDragItem(false);
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
            ModTutorial.recordCraftedItem(event.getCrafting().getItem());
        }
    }

    // Record when Copper ingot is smelted
    @SubscribeEvent
    public static void onSmeltItem(ItemSmeltedEvent event){
        if(!event.getEntity().level.isClientSide()){
            ModTutorial.recordSmeltedItem(event.getSmelting().getItem());
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
            }else if(event.getPlacedBlock().getBlock() instanceof PickaxeBlock){
                System.out.println("RESETTING STATS");
                Messages.sendToServer(new DevResetStatsPacket());
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickCraftingTable(RightClickBlock event){
        if(!event.getEntity().level.isClientSide()){
            BlockState state = event.getEntity().level.getBlockState(event.getHitVec().getBlockPos());
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

            // Check when player breaks a log
            if(bState.getBlock() == Blocks.OAK_LOG || bState.getBlock() == Blocks.BIRCH_LOG){
                ModTutorial.recordLogGotten();
            }

            if(bState.getBlock() instanceof PickaxeBlock){
                ModTutorial.recordPickaxeBlockGotten();
            }
        }
    }

    @SubscribeEvent
    public static void onPostDrawOverlay(ScreenEvent.Render.Post event){
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
    public static void onWorldTick(LevelTickEvent event){
        if (event.level.isClientSide()){ // Check if is client side
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

    @SubscribeEvent
    public static void onSave(LevelEvent.Save event){
        System.out.println("DEV SAVING STATS");
        Messages.sendToServer(new DevExportStatsPacket());
    }
}
