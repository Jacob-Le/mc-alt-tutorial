package net.jacob6.alttutorial.tutorial.network.packets;

import java.util.function.Supplier;

import net.jacob6.alttutorial.tutorial.client.ModClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class SyncStatusToClientPacket {
    // Server -> Client message packet to sync the players' status of the tutorial

    public int tAccessed;
    public int tCrafted;
    public int tLog;
    public int tCraftingTable;
    public int tWPick;
    public int tSPick;
    public int tFurnace;
    public int tCopper;
    public int numWasted;
    public int numPickaxeBlock;
    public boolean hSwapped;
    public boolean hPlaced;
    public boolean hAccessed;
    public boolean hCrafted;

    public SyncStatusToClientPacket(int tAccessed, int tCrafted, 
        int tLog, int tCraftingTable, int tWPick, int tSPick, int tFurnance, int tCopper,
        int numWasted, int numPickaxeBlock,
        boolean hSwapped, boolean hPlaced, boolean hAccessed, boolean hCrafted){
        this.tAccessed = tAccessed;
        this.tCrafted = tCrafted;
        this.tLog = tLog;
        this.tCraftingTable = tCraftingTable;
        this.tWPick = tWPick;
        this.tSPick = tSPick;
        this.tFurnace = tFurnance;
        this.tCopper = tCopper;
        this.numWasted = numWasted;
        this.numPickaxeBlock = numPickaxeBlock;
        this.hSwapped = hSwapped;
        this.hPlaced = hPlaced;
        this.hAccessed = hAccessed;
        this.hCrafted = hCrafted;
    }

    public SyncStatusToClientPacket(FriendlyByteBuf buffer){
        decoder(buffer);
    }

    public void encoder(FriendlyByteBuf buffer){
        buffer.writeInt(this.tAccessed);
        buffer.writeInt(this.tCrafted);
        buffer.writeInt(this.numWasted);
        buffer.writeInt(this.tLog);
        buffer.writeInt(this.tCraftingTable);
        buffer.writeInt(this.tWPick);
        buffer.writeInt(this.tSPick);
        buffer.writeInt(this.tFurnace);
        buffer.writeInt(this.tCopper);
        buffer.writeInt(this.numPickaxeBlock);
        buffer.writeBoolean(this.hSwapped);
        buffer.writeBoolean(this.hPlaced);
        buffer.writeBoolean(this.hAccessed);
        buffer.writeBoolean(this.hCrafted);
    }

    public static SyncStatusToClientPacket decoder(FriendlyByteBuf buffer){
        return new SyncStatusToClientPacket(buffer.readInt(),
            buffer.readInt(),
            buffer.readInt(),
            buffer.readInt(),
            buffer.readInt(),
            buffer.readInt(),
            buffer.readInt(),
            buffer.readInt(),
            buffer.readInt(),
            buffer.readInt(),
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.readBoolean());
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

