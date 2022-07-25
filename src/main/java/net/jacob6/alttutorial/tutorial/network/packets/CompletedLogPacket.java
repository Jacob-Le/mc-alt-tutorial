package net.jacob6.alttutorial.tutorial.network.packets;

import java.util.function.Supplier;

import net.jacob6.alttutorial.tutorial.network.ModTutorialStatusManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class CompletedLogPacket {
    public CompletedLogPacket() {
        
    }

    public void encoder(FriendlyByteBuf buffer) {
    }

    public static CompletedLogPacket decoder(FriendlyByteBuf buffer) {
        return new CompletedLogPacket();
    }

    // Handle receiving this packet on the server side
    public static void handle(CompletedLogPacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ModTutorialStatusManager.get(player.server).completedLog();
        });
        ctx.setPacketHandled(true);
    }
}
