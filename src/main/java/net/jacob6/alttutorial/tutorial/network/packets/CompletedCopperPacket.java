package net.jacob6.alttutorial.tutorial.network.packets;

import java.util.function.Supplier;

import net.jacob6.alttutorial.tutorial.network.ModTutorialStatusManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class CompletedCopperPacket{
    public CompletedCopperPacket() {
        
    }

    public void encoder(FriendlyByteBuf buffer) {
    }

    public static CompletedCopperPacket decoder(FriendlyByteBuf buffer) {
        return new CompletedCopperPacket();
    }

    // Handle receiving this packet on the server side
    public static void handle(CompletedCopperPacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ModTutorialStatusManager.get(player.server).completedCopper();
        });
        ctx.setPacketHandled(true);
    }
}
