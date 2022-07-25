package net.jacob6.alttutorial.tutorial.network.packets;

import java.util.function.Supplier;

import net.jacob6.alttutorial.tutorial.network.ModTutorialStatusManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class CompletedCraftingTablePacket{
    public CompletedCraftingTablePacket() {
        
    }

    public void encoder(FriendlyByteBuf buffer) {
    }

    public static CompletedCraftingTablePacket decoder(FriendlyByteBuf buffer) {
        return new CompletedCraftingTablePacket();
    }

    // Handle receiving this packet on the server side
    public static void handle(CompletedCraftingTablePacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ModTutorialStatusManager.get(player.server).completedCraftingTable();
        });
        ctx.setPacketHandled(true);
    }
}
