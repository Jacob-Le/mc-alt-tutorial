package net.jacob6.alttutorial.tutorial.network.packets;

import java.io.IOException;
import java.util.function.Supplier;

import net.jacob6.alttutorial.tutorial.network.ModTutorialStatusManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class DevExportStatsPacket{
    public DevExportStatsPacket() {
        
    }

    public void encoder(FriendlyByteBuf buffer) {
    }

    public static DevExportStatsPacket decoder(FriendlyByteBuf buffer) {
        return new DevExportStatsPacket();
    }

    // Handle receiving this packet on the server side
    public static void handle(DevExportStatsPacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            try{
                ModTutorialStatusManager.get(player.server).exportStats();
            }catch(IOException e){
                System.out.println(e);
            }

        });
        ctx.setPacketHandled(true);
    }
}
