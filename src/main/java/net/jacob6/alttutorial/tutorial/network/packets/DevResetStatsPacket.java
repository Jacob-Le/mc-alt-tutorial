package net.jacob6.alttutorial.tutorial.network.packets;

import java.util.function.Supplier;

import net.jacob6.alttutorial.tutorial.network.ModTutorialStatusManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class DevResetStatsPacket{
    public DevResetStatsPacket() {
        
    }

    public void encoder(FriendlyByteBuf buffer) {
    }

    public static DevResetStatsPacket decoder(FriendlyByteBuf buffer) {
        return new DevResetStatsPacket();
    }

    // Handle receiving this packet on the server side
    public static void handle(DevResetStatsPacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ModTutorialStatusManager.get(player.server).resetStats();;
        });
        ctx.setPacketHandled(true);
    }
}
