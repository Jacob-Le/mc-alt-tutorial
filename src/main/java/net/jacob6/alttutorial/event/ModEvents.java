package net.jacob6.alttutorial.event;

import net.jacob6.alttutorial.MCAltTutorial;
import net.jacob6.alttutorial.tutorial.ModTutorial;
import net.jacob6.alttutorial.tutorial.network.ModTutorialStatusManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = MCAltTutorial.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onCraftingTablePlace(EntityPlaceEvent event){
        if(!event.getEntity().level.isClientSide()){
            // Check if the block is a crafting table block
            if(event.getPlacedBlock().getBlock() == Blocks.CRAFTING_TABLE){
                ModTutorial.promptAccessEvent();
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickCraftingTable(RightClickBlock event){
        if(!event.getEntity().level.isClientSide()){
            Player player = event.getPlayer();
            // Check if we have accessed a Crafting Table block
            BlockState state = player.getLevel().getBlockState(event.getHitVec().getBlockPos());

            if(player.getItemInHand(InteractionHand.MAIN_HAND) == null && state.getBlock() == Blocks.CRAFTING_TABLE){
                ModTutorial.handleAccessEvent();
            }
        }
    }

    public static void onServerTick(ServerTickEvent event){
        // if (event.world.isClientSide()){ // Check if is client side
        //     return;
        // }
        if (event.phase == TickEvent.Phase.START){
            return;
        }
        ModTutorialStatusManager manager = ModTutorialStatusManager.get(event.getServer());
        manager.tick(event.getServer());
    }
}
