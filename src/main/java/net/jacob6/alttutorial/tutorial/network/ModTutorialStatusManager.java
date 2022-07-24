package net.jacob6.alttutorial.tutorial.network;

import javax.annotation.Nonnull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.jacob6.alttutorial.Messages;

public class ModTutorialStatusManager extends SavedData{
    // Server side reference for tutorial status
    private int timeAccessed;
    private int timeCrafted;
    private int numWastedBlocks;
    private boolean hasAccessed;
    private boolean hasCrafted;
    private boolean hasSwapped;
    private boolean hasPlaced;

    // Interval counter between when we send updates
    private final int INTERVAL = 10;
    private int counter = 0;
    
    // Setters
    public void setTimeAccessed(int timeAccessed) {
        this.timeAccessed = timeAccessed;
    }
    public void setTimeCrafted(int timeCrafted) {
        this.timeCrafted = timeCrafted;
    }
    public void setNumWastedBlocks(int num){
        this.numWastedBlocks = num;
    }
    public void setHasSwapped(boolean hasSwapped) {
        this.hasSwapped = hasSwapped;
    }
    public void setHasPlaced(boolean hasPlaced) {
        this.hasPlaced = hasPlaced;
    }
    public void setHasAccessed(boolean hasAccessed) {
        this.hasAccessed = hasAccessed;
    }
    public void setHasCrafted(boolean hasCrafted) {
        this.hasCrafted = hasCrafted;
    }
    
    // Getters
    public int getTimeAccessed() {
        return timeAccessed;
    }
    public int getTimeCrafted() {
        return timeCrafted;
    }
    public int getNumWastedBlocks(){
        return numWastedBlocks;
    }
    public boolean playerHasSwapped() {
        return hasSwapped;
    }
    public boolean playerHasPlaced() {
        return hasPlaced;
    }
    public boolean playerHasAccessed() {
        return hasAccessed;
    }
    public boolean playerHasCrafted() {
        return hasCrafted;
    }

    public void addWastedBlock(){
        this.numWastedBlocks++;
    }

    public ModTutorialStatusManager(){
        this.timeAccessed = -1;
        this.timeCrafted = -1;
        this.hasAccessed = false;
        this.hasCrafted = false;
    }

    public static ModTutorialStatusManager create(){
        return new ModTutorialStatusManager();
    }

    public void tick(MinecraftServer server){
        counter -= 1;

        // Check if has been accessed, if not increment timeAccessed
        if (!hasAccessed) {
            ++this.timeAccessed;
        }

        // If accessed, check if crafted, if not increment timeCrafted
        if (hasAccessed && !hasCrafted) {
            ++this.timeCrafted;
        }

        // Send every INTERVAL ticks an update if the player has not accessed the crafting table and not crafted anything
        if(counter <= 0 && (!hasAccessed || !hasCrafted || !hasPlaced || !hasSwapped)){
            counter = INTERVAL;

            // There is a bug here - will complete tutorial for all players, but that is outside of the scope of this for now
            server.getPlayerList().getPlayers().forEach(player -> {
                // if (player instanceof ServerPlayer serverPlayer){
                Messages.sendToPlayer((ServerPlayer) player, new SyncStatusToClientPacket(this.timeAccessed, this.timeCrafted, this.numWastedBlocks, this.hasSwapped, this.hasPlaced, this.hasAccessed, this.hasCrafted));
                // }
            });
        }
    }

    public static ModTutorialStatusManager load(CompoundTag tag){
        ModTutorialStatusManager manager = create();
        manager.setTimeAccessed(tag.getInt("timeAccessed"));
        manager.setTimeCrafted(tag.getInt("timeCrafted"));
        manager.setNumWastedBlocks(tag.getInt("numWastedBlocks"));
        manager.setHasSwapped(tag.getBoolean("hasSwapped"));
        manager.setHasPlaced(tag.getBoolean("hasPlaced"));
        manager.setHasAccessed(tag.getBoolean("hasAccessed"));
        manager.setHasCrafted(tag.getBoolean("hasCrafted"));

        return manager;
    }

    @Nonnull
    public static ModTutorialStatusManager get(MinecraftServer server){
        DimensionDataStorage dataStorage = server.overworld().getDataStorage();

        return dataStorage.computeIfAbsent(ModTutorialStatusManager::load, ModTutorialStatusManager::create, "ModTutorialStatusManager");
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("timeAccessed", this.timeAccessed);
        tag.putInt("timeCrafted", this.timeCrafted);
        tag.putInt("numWastedBlocks", this.numWastedBlocks);
        tag.putBoolean("hasSwapped", this.hasSwapped);
        tag.putBoolean("hasPlaced", this.hasPlaced);
        tag.putBoolean("hasAccessed", this.hasAccessed);
        tag.putBoolean("hasCrafted", this.hasCrafted);

        return tag;
    }
    
}
