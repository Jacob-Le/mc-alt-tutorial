package net.jacob6.alttutorial;

import java.util.function.BiConsumer;
import java.util.function.Function;

import javax.print.DocFlavor.STRING;

import net.jacob6.alttutorial.MCAltTutorial;
import net.jacob6.alttutorial.tutorial.network.AccessedCraftingTablePacket;
import net.jacob6.alttutorial.tutorial.network.CraftedItemPacket;
import net.jacob6.alttutorial.tutorial.network.SyncStatusToClientPacket;

import net.minecraft.network.FriendlyByteBuf;
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
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(ServerPlayer player, MSG message){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
