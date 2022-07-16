package net.jacob6.alttutorial.tutorial.network;

import com.google.common.base.Supplier;

import net.jacob6.alttutorial.tutorial.client.ModClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class CraftedItemPacket {
    public CraftedItemPacket() {
        
    }

    public void encoder(FriendlyByteBuf buffer) {

    }

    public CraftedItemPacket decoder(FriendlyByteBuf buffer) {
        return new CraftedItemPacket();
    }

    // Handle receiving this packet on the server side
    public static void handle(CraftedItemPacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ModTutorialStatusManager.get(player.server).setHasCrafted(true);
        });
    }
}
