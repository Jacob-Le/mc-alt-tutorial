package net.jacob6.alttutorial.tutorial.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Nonnull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.jacob6.alttutorial.Messages;
import net.jacob6.alttutorial.tutorial.network.packets.SyncStatusToClientPacket;

public class ModTutorialStatusManager extends SavedData{
    // Server side reference for tutorial status
    private int timeAccessed;
    private int timeCrafted;
    private int timeLog;
    private int timeCraftingTable;
    private int timeWoodenPickaxe;
    private int timeStonePickaxe;
    private int timeFurnace;
    private int timeCopperOre;
    private int numWastedBlocks;
    private int numPickaxeBlocks;
    private boolean hasAccessed;
    private boolean hasCrafted;
    private boolean hasSwapped;
    private boolean hasPlaced;
    private long worldSeed;

    private int tickCount = 0;

    // Interval counter between when we send updates
    private final int INTERVAL = 10;
    private int counter = 0;

    private final String RESULTS_NAME = "results.csv";
    private final String ZIP_NAME = "results.zip";

    public void addWastedBlock(){
        this.numWastedBlocks++;
    }
    public void addPickaxeBlock(){
        this.numPickaxeBlocks++;
    }

    public ModTutorialStatusManager(){
        this.resetStats();
    }

    public void setHasAccessed(boolean accessed){
        this.hasAccessed = accessed;
    }
    public void setHasCrafted(boolean crafted){
        this.hasCrafted = crafted;
    }
    public void setHasSwapped(boolean swapped){
        this.hasSwapped = swapped;
    }
    public void setHasPlaced(boolean placed){
        this.hasPlaced = placed;
    }
    public void completedAccess(){
        if(this.timeAccessed == -1) this.timeAccessed = this.ticksToSeconds(this.tickCount);
        this.hasAccessed = true;
    }
    public void completedCraft(){
        if(this.timeCrafted == -1) this.timeCrafted = this.ticksToSeconds(this.tickCount);
        this.hasCrafted = true;
    }
    public void completedLog(){
        if(this.timeLog == -1) this.timeLog = this.ticksToSeconds(this.tickCount);
    }
    public void completedCraftingTable(){
        if(this.timeCraftingTable == -1) this.timeCraftingTable = this.ticksToSeconds(this.tickCount);
    }
    public void completedWoodenPickaxe(){
        if(this.timeWoodenPickaxe == -1) this.timeWoodenPickaxe = this.ticksToSeconds(this.tickCount);
    }
    public void completedStonePickaxe(){
        if(this.timeStonePickaxe == -1) this.timeStonePickaxe = this.ticksToSeconds(this.tickCount);
    }
    public void completedFurnace(){
        if(this.timeFurnace == -1) this.timeFurnace = this.ticksToSeconds(this.tickCount);
    }
    public void completedCopper(){
        if(this.timeCopperOre == -1) this.timeCopperOre = this.ticksToSeconds(this.tickCount);
    }

    public void resetStats(){
        this.tickCount = 0;
        this.timeAccessed = -1;
        this.timeCrafted = -1;
        this.timeLog = -1;
        this.timeCraftingTable = -1;
        this.timeWoodenPickaxe = -1;
        this.timeStonePickaxe = -1;
        this.timeFurnace = -1;
        this.timeCopperOre = -1;
        this.numWastedBlocks = 0;
        this.numPickaxeBlocks = 0;
        this.hasAccessed = false;
        this.hasCrafted = false;
        this.hasSwapped = false;
        this.hasPlaced = false;
        this.worldSeed = -1;
    }

    private int ticksToSeconds(int ticks){
        return Math.round(ticks/20);
    }

    private boolean tutorialIsDone(){
        return this.timeAccessed != -1 && this.timeCrafted != -1 &&
            this.timeLog != -1 && this.timeCraftingTable != -1 && this.timeWoodenPickaxe != -1 && this.timeStonePickaxe != -1 && this.timeCopperOre != -1;
    }

    public String toCSVString(){
        String stats = String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d",
            this.timeAccessed, this.timeCrafted,
            this.timeLog, this.timeCraftingTable, this.timeWoodenPickaxe, this.timeStonePickaxe, this.timeFurnace, this.timeCopperOre,
            this.numWastedBlocks, this.numPickaxeBlocks, this.worldSeed);
        return stats;
    }

    public void exportStats() throws IOException{
        // Write to file
        File csvFile = new File(RESULTS_NAME);
        try(PrintWriter writer = new PrintWriter(csvFile)){
            writer.println(toCSVString());
        }

        // Zip file
        byte[] buffer = new byte[2048];
        FileInputStream fileInputStream = new FileInputStream(csvFile);
        FileOutputStream fileOutputStream = new FileOutputStream(ZIP_NAME);
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);

        int length;
        while((length = fileInputStream.read(buffer)) > 0){
            gzipOutputStream.write(buffer, 0, length);
        }
        fileInputStream.close();
        gzipOutputStream.close();
    }

    public static ModTutorialStatusManager create(){
        return new ModTutorialStatusManager();
    }

    public void tick(MinecraftServer server){
        counter -= 1;
        tickCount++;

        this.worldSeed = server.getWorldData().worldGenSettings().seed();

        // Send every INTERVAL ticks an update if the player has not accessed the crafting table and not crafted anything
        if(counter <= 0 && (!tutorialIsDone())){
            counter = INTERVAL;

            if(tickCount % 400 == 0) System.out.println(toCSVString());

            // There is a bug here - will complete tutorial for all players, but that is outside of the scope of this for now
            server.getPlayerList().getPlayers().forEach(player -> {
                SyncStatusToClientPacket packet = new SyncStatusToClientPacket(this.timeAccessed, this.timeCrafted, 
                    this.timeLog, this.timeCraftingTable, this.timeWoodenPickaxe, this.timeStonePickaxe, this.timeFurnace, this.timeCopperOre,
                    this.numWastedBlocks, this.numPickaxeBlocks,
                    this.hasSwapped, this.hasPlaced, this.hasAccessed, this.hasCrafted);

                Messages.sendToPlayer((ServerPlayer) player, packet);
            });
        }
    }

    public static ModTutorialStatusManager load(CompoundTag tag){
        ModTutorialStatusManager manager = create();
        manager.tickCount = tag.getInt("tickCount");
        manager.timeAccessed = tag.getInt("timeAccessed");
        manager.timeCrafted = tag.getInt("timeCrafted");
        manager.timeLog = tag.getInt("timeLog");
        manager.timeCraftingTable = tag.getInt("timeCraftingTable");
        manager.timeWoodenPickaxe = tag.getInt("timeWoodenPickaxe");
        manager.timeStonePickaxe = tag.getInt("timeStonePickaxe");
        manager.timeFurnace = tag.getInt("timeFurnace");
        manager.timeCopperOre = tag.getInt("timeCopperOre");
        manager.numWastedBlocks = tag.getInt("numWastedBlocks");
        manager.numPickaxeBlocks = tag.getInt("numPickaxeBlocks");
        manager.hasSwapped = tag.getBoolean("hasSwapped");
        manager.hasPlaced = tag.getBoolean("hasPlaced");
        manager.hasAccessed = tag.getBoolean("hasAccessed");
        manager.hasCrafted = tag.getBoolean("hasCrafted");

        return manager;
    }

    @Nonnull
    public static ModTutorialStatusManager get(MinecraftServer server){
        DimensionDataStorage dataStorage = server.overworld().getDataStorage();

        return dataStorage.computeIfAbsent(ModTutorialStatusManager::load, ModTutorialStatusManager::create, "ModTutorialStatusManager");
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("tickCount", this.tickCount);
        tag.putInt("timeAccessed", this.timeAccessed);
        tag.putInt("timeCrafted", this.timeCrafted);
        tag.putInt("timeLog", this.timeLog);
        tag.putInt("timeCraftingTable", this.timeCraftingTable);
        tag.putInt("timeWoodenPickaxe", this.timeWoodenPickaxe);
        tag.putInt("timeStonePickaxe", this.timeStonePickaxe);
        tag.putInt("timeFurnace", this.timeFurnace);
        tag.putInt("timeCopperOre", this.timeCopperOre);
        tag.putInt("numWastedBlocks", this.numWastedBlocks);
        tag.putInt("numPickaxeBlocks", this.numPickaxeBlocks);
        tag.putBoolean("hasSwapped", this.hasSwapped);
        tag.putBoolean("hasPlaced", this.hasPlaced);
        tag.putBoolean("hasAccessed", this.hasAccessed);
        tag.putBoolean("hasCrafted", this.hasCrafted);

        return tag;
    }
}
