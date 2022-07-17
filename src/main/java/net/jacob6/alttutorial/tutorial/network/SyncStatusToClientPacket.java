package net.jacob6.alttutorial.tutorial.network;

import java.util.function.Supplier;

import net.jacob6.alttutorial.tutorial.client.ModClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class SyncStatusToClientPacket {// extends AbstractPacket{
    // Server -> Client message packet to sync the players' status of the tutorial

    public int tAccessed;
    public int tCrafted;
    public boolean hAccessed;
    public boolean hCrafted;

    public SyncStatusToClientPacket(int tAccessed, int tCrafted, boolean hAccessed, boolean hCrafted){
        this.tAccessed = tAccessed;
        this.tCrafted = tCrafted;
        this.hAccessed = hAccessed;
        this.hCrafted = hCrafted;
    }

    public SyncStatusToClientPacket(FriendlyByteBuf buffer){
        decoder(buffer);
    }

    public void encoder(FriendlyByteBuf buffer){
        buffer.writeInt(this.tAccessed);
        buffer.writeInt(this.tCrafted);
        buffer.writeBoolean(this.hAccessed);
        buffer.writeBoolean(this.hCrafted);
    }

    public static SyncStatusToClientPacket decoder(FriendlyByteBuf buffer){
        return new SyncStatusToClientPacket(buffer);
    }

    // Sending status to client
    public static void handle(SyncStatusToClientPacket msg, Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ModClientPacketHandler.handleSyncStatusPacket(msg, supplier));
        });
        ctx.setPacketHandled(true);
    }
}

