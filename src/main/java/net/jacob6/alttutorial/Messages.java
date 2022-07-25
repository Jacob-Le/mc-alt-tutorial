package net.jacob6.alttutorial;

import net.jacob6.alttutorial.tutorial.network.packets.AccessedCraftingTablePacket;
import net.jacob6.alttutorial.tutorial.network.packets.AddPickaxeBlockPacket;
import net.jacob6.alttutorial.tutorial.network.packets.AddWastedBlockPacket;
import net.jacob6.alttutorial.tutorial.network.packets.CompletedCopperPacket;
import net.jacob6.alttutorial.tutorial.network.packets.CompletedCraftingTablePacket;
import net.jacob6.alttutorial.tutorial.network.packets.CompletedFurnacePacket;
import net.jacob6.alttutorial.tutorial.network.packets.CompletedLogPacket;
import net.jacob6.alttutorial.tutorial.network.packets.CompletedSPickaxePacket;
import net.jacob6.alttutorial.tutorial.network.packets.CompletedWPickaxePacket;
import net.jacob6.alttutorial.tutorial.network.packets.CraftedItemPacket;
import net.jacob6.alttutorial.tutorial.network.packets.DevExportStatsPacket;
import net.jacob6.alttutorial.tutorial.network.packets.DevResetStatsPacket;
import net.jacob6.alttutorial.tutorial.network.packets.PlacedBlockPacket;
import net.jacob6.alttutorial.tutorial.network.packets.SwappedItemPacket;
import net.jacob6.alttutorial.tutorial.network.packets.SyncStatusToClientPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;


public class Messages {
    private static final String PROTOCOL_VERSION = "1";

    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(MCAltTutorial.MODID, "modnetwork"), 
        () -> PROTOCOL_VERSION, 
        PROTOCOL_VERSION::equals, 
        PROTOCOL_VERSION::equals);

    private static int packetId = 0;
    private static int id() { return packetId++; }

    public static void register(){

        // Sync from server to client packet message
        INSTANCE.registerMessage(id(), SyncStatusToClientPacket.class, SyncStatusToClientPacket::encoder, SyncStatusToClientPacket::decoder, SyncStatusToClientPacket::handle);

        // Set hasAccessed to server packet message
        INSTANCE.registerMessage(id(), AccessedCraftingTablePacket.class, AccessedCraftingTablePacket::encoder, AccessedCraftingTablePacket::decoder, AccessedCraftingTablePacket::handle);

        // Set hasCrafted to server packet message
        INSTANCE.registerMessage(id(), CraftedItemPacket.class, CraftedItemPacket::encoder, CraftedItemPacket::decoder, CraftedItemPacket::handle);

        // Set hasSwapped to server packet message
        INSTANCE.registerMessage(id(), SwappedItemPacket.class, SwappedItemPacket::encoder, SwappedItemPacket::decoder,  SwappedItemPacket::handle);
        
        // Set hasPlaced to server packet message
        INSTANCE.registerMessage(id(), PlacedBlockPacket.class, PlacedBlockPacket::encoder, PlacedBlockPacket::decoder,  PlacedBlockPacket::handle);

        // AddWastedBlock to server packet message
        INSTANCE.registerMessage(id(), AddWastedBlockPacket.class, AddWastedBlockPacket::encoder, AddWastedBlockPacket::decoder,  AddWastedBlockPacket::handle);

        // AddPickaxeBlock to server packet message
        INSTANCE.registerMessage(id(), AddPickaxeBlockPacket.class, AddPickaxeBlockPacket::encoder, AddPickaxeBlockPacket::decoder,  AddPickaxeBlockPacket::handle);
    
        // Send time to complete milestone get Log to server packet message
        INSTANCE.registerMessage(id(), CompletedLogPacket.class, CompletedLogPacket::encoder, CompletedLogPacket::decoder,  CompletedLogPacket::handle);
    
        // Send time to complete milestone get Crafting table to server packet message
        INSTANCE.registerMessage(id(), CompletedCraftingTablePacket.class, CompletedCraftingTablePacket::encoder, CompletedCraftingTablePacket::decoder,  CompletedCraftingTablePacket::handle);

        // Send time to complete milestone get Wooden Pickaxe to server packet message
        INSTANCE.registerMessage(id(), CompletedWPickaxePacket.class, CompletedWPickaxePacket::encoder, CompletedWPickaxePacket::decoder,  CompletedWPickaxePacket::handle);
    
        // Send time to complete milestone get Stone Pickaxe to server packet message
        INSTANCE.registerMessage(id(), CompletedSPickaxePacket.class, CompletedSPickaxePacket::encoder, CompletedSPickaxePacket::decoder,  CompletedSPickaxePacket::handle);

        // Send time to complete milestone get Furnace to server packet message
        INSTANCE.registerMessage(id(), CompletedFurnacePacket.class, CompletedFurnacePacket::encoder, CompletedFurnacePacket::decoder,  CompletedFurnacePacket::handle);

        // Send time to complete milestone get Copper to server packet message
        INSTANCE.registerMessage(id(), CompletedCopperPacket.class, CompletedCopperPacket::encoder, CompletedCopperPacket::decoder,  CompletedCopperPacket::handle);
    
        // Send dev utils export stats to server packet message
        INSTANCE.registerMessage(id(), DevExportStatsPacket.class, DevExportStatsPacket::encoder, DevExportStatsPacket::decoder,  DevExportStatsPacket::handle);

        // Send dev utils reset stats to server packet message
        INSTANCE.registerMessage(id(), DevResetStatsPacket.class, DevResetStatsPacket::encoder, DevResetStatsPacket::decoder,  DevResetStatsPacket::handle);
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(ServerPlayer player, MSG message){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}