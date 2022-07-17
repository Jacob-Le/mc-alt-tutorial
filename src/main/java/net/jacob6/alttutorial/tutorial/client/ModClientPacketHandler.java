package net.jacob6.alttutorial.tutorial.client;

import java.util.function.Supplier;

import net.jacob6.alttutorial.tutorial.network.SyncStatusToClientPacket;
import net.jacob6.alttutorial.tutorial.data.ModTutorialStatus;
import net.minecraftforge.network.NetworkEvent;

public class ModClientPacketHandler {


    // Handle sync packet on client side
    public static void handleSyncStatusPacket(SyncStatusToClientPacket msg, Supplier<NetworkEvent.Context> supplier){
        ModTutorialStatus.set(msg.tAccessed, msg.tCrafted, msg.hAccessed, msg.hCrafted);
    }
}
