package net.jacob6.alttutorial.tutorial.network.packets;

import java.util.function.Supplier;

import net.jacob6.alttutorial.tutorial.network.ModTutorialStatusManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class PlacedBlockPacket{
    public PlacedBlockPacket() {
        
    }

    public void encoder(FriendlyByteBuf buffer) {
    }

    public static PlacedBlockPacket decoder(FriendlyByteBuf buffer) {
        return new PlacedBlockPacket();
    }

    // Handle receiving this packet on the server side
    public static void handle(PlacedBlockPacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ModTutorialStatusManager.get(player.server).setHasPlaced(true);
        });
        ctx.setPacketHandled(true);
    }
}
