package net.jacob6.alttutorial;

import net.jacob6.alttutorial.tutorial.network.AccessedCraftingTablePacket;
import net.jacob6.alttutorial.tutorial.network.AddWastedBlockPacket;
import net.jacob6.alttutorial.tutorial.network.CraftedItemPacket;
import net.jacob6.alttutorial.tutorial.network.PlacedBlockPacket;
import net.jacob6.alttutorial.tutorial.network.SwappedItemPacket;
import net.jacob6.alttutorial.tutorial.network.SyncStatusToClientPacket;

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
        INSTANCE.registerMessage(id(), 
            SyncStatusToClientPacket.class, 
            SyncStatusToClientPacket::encoder,
            SyncStatusToClientPacket::decoder,
            SyncStatusToClientPacket::handle);

        // Set hasAccessed to server packet message
        INSTANCE.registerMessage(id(), 
            AccessedCraftingTablePacket.class,
            AccessedCraftingTablePacket::encoder,
            AccessedCraftingTablePacket::decoder,
            AccessedCraftingTablePacket::handle);

        // Set hasCrafted to server packet message
        INSTANCE.registerMessage(id(), 
            CraftedItemPacket.class,
            CraftedItemPacket::encoder,
            CraftedItemPacket::decoder,
            CraftedItemPacket::handle);

        // Set hasSwapped to server packet message
        INSTANCE.registerMessage(id(),
            SwappedItemPacket.class,
            SwappedItemPacket::encoder,
            SwappedItemPacket::decoder, 
            SwappedItemPacket::handle);
        
        // Set hasPlaced to server packet message
        INSTANCE.registerMessage(id(),
            PlacedBlockPacket.class,
            PlacedBlockPacket::encoder,
            PlacedBlockPacket::decoder, 
            PlacedBlockPacket::handle);

        // AddWastedBlock to server packet message
        INSTANCE.registerMessage(id(),
            AddWastedBlockPacket.class,
            AddWastedBlockPacket::encoder,
            AddWastedBlockPacket::decoder, 
            AddWastedBlockPacket::handle);
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(ServerPlayer player, MSG message){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}