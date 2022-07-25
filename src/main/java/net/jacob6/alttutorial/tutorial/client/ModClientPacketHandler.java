package net.jacob6.alttutorial.tutorial.client;

import java.util.function.Supplier;

import net.jacob6.alttutorial.tutorial.data.ModTutorialStatus;
import net.jacob6.alttutorial.tutorial.network.packets.SyncStatusToClientPacket;
import net.minecraftforge.network.NetworkEvent;

public class ModClientPacketHandler {


    // Handle sync packet on client side
    public static void handleSyncStatusPacket(SyncStatusToClientPacket msg, Supplier<NetworkEvent.Context> supplier){
        ModTutorialStatus.set(msg.tAccessed, msg.tCrafted, 
            msg.tLog, msg.tCraftingTable, msg.tWPick, msg.tSPick, msg.tFurnace, msg.tCopper,
            msg.numWasted, msg.numPickaxeBlock,
            msg.hSwapped, msg.hPlaced, msg.hAccessed, msg.hCrafted);
    }
}
