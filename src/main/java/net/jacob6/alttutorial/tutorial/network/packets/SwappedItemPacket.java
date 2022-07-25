package net.jacob6.alttutorial.tutorial.network.packets;

import java.util.function.Supplier;

import net.jacob6.alttutorial.tutorial.network.ModTutorialStatusManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class SwappedItemPacket{
    public SwappedItemPacket() {
        
    }

    public void encoder(FriendlyByteBuf buffer) {
    }

    public static SwappedItemPacket decoder(FriendlyByteBuf buffer) {
        return new SwappedItemPacket();
    }

    // Handle receiving this packet on the server side
    public static void handle(SwappedItemPacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ModTutorialStatusManager.get(player.server).setHasSwapped(true);
        });
        ctx.setPacketHandled(true);
    }
}
