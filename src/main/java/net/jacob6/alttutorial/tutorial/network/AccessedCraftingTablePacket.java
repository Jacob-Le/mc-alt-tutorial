package net.jacob6.alttutorial.tutorial.network;

import com.google.common.base.Supplier;

import net.jacob6.alttutorial.tutorial.client.ModClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class AccessedCraftingTablePacket {
    public AccessedCraftingTablePacket() {
        
    }

    public void encoder(FriendlyByteBuf buffer) {

    }

    public static AccessedCraftingTablePacket decoder(FriendlyByteBuf buffer) {
        return new AccessedCraftingTablePacket();
    }

    // Handle receiving this packet on the server side
    public static void handle(AccessedCraftingTablePacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ModTutorialStatusManager.get(player.server).setHasAccessed(true);
        });
    }
}
