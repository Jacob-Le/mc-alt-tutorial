package net.jacob6.alttutorial.tutorial.network.packets;

import java.util.function.Supplier;

import net.jacob6.alttutorial.tutorial.network.ModTutorialStatusManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class CompletedWPickaxePacket{
    public CompletedWPickaxePacket() {
        
    }

    public void encoder(FriendlyByteBuf buffer) {
    }

    public static CompletedWPickaxePacket decoder(FriendlyByteBuf buffer) {
        return new CompletedWPickaxePacket();
    }

    // Handle receiving this packet on the server side
    public static void handle(CompletedWPickaxePacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ModTutorialStatusManager.get(player.server).completedWoodenPickaxe();
        });
        ctx.setPacketHandled(true);
    }
}
