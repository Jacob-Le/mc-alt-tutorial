package net.jacob6.alttutorial.tutorial.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class AddWastedBlockPacket{
    public AddWastedBlockPacket() {
        
    }

    public void encoder(FriendlyByteBuf buffer) {
    }

    public static AddWastedBlockPacket decoder(FriendlyByteBuf buffer) {
        return new AddWastedBlockPacket();
    }

    // Handle receiving this packet on the server side
    public static void handle(AddWastedBlockPacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ModTutorialStatusManager.get(player.server).addWastedBlock();
        });
        ctx.setPacketHandled(true);
    }
}
