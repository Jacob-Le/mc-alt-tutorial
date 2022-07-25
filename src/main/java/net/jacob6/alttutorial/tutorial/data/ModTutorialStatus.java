package net.jacob6.alttutorial.tutorial.data;

public class ModTutorialStatus {
    // Client side status
    private static int timeAccessed = -1;
    private static int timeCrafted = -1;
    private static int timeLog = -1;
    private static int timeCraftingTable = -1;
    private static int timeWPick = -1;
    private static int timeSPick = -1;
    private static int timeFurnace = -1;
    private static int timeCopper = -1;
    private static int numWasted = -1;
    private static int numWastedBlocks = 0;
    private static int numPickaxeBlock = 0;
    private static boolean hasSwapped = false;
    private static boolean hasPlaced = false;
    private static boolean hasAccessed = false;
    private static boolean hasCrafted = false;

    // Setters
    public static void set(int tAccessed, int tCrafted, 
        int tLog, int tCraftingTable, int tWPick, int tSPick, int tFurnance, int tCopper,
        int numWasted,  int numPickaxe,
        boolean hSwapped, boolean hPlaced, boolean hAccessed, boolean hCrafted){
        
        timeAccessed = tAccessed;
        timeCrafted = tCrafted;
        timeLog = tLog;
        timeCraftingTable = tCraftingTable;
        timeWPick = tWPick;
        timeSPick = tSPick;
        timeFurnace = tFurnance;
        timeCopper = tCopper;
        numWastedBlocks = numWasted;
        numPickaxeBlock = numPickaxe;
        hasSwapped = hSwapped;
        hasPlaced = hPlaced;
        hasAccessed = hAccessed;
        hasCrafted = hCrafted;
    }
    
    // Getters
    public static int getTimeAccessed() {
        return timeAccessed;
    }
    public static int getTimeCrafted() {
        return timeCrafted;
    }
    public static int getTimeLog() {
        return timeLog;
    }
    public static int getTimeCraftingTable() {
        return timeCraftingTable;
    }
    public static int getTimeWPick() {
        return timeWPick;
    }
    public static int getTimeSPick() {
        return timeSPick;
    }
    public static int getTimeFurnace() {
        return timeFurnace;
    }
    public static int getTimeCopper() {
        return timeCopper;
    }
    public static int getNumWasted() {
        return numWasted;
    }
    public static int getNumPickaxeBlock() {
        return numPickaxeBlock;
    }
    public static boolean playerHasSwapped() {
        return hasSwapped;
    }
    public static boolean playerHasPlaced() {
        return hasPlaced;
    }
    public static boolean playerHasAccessed() {
        return hasAccessed;
    }
    public static boolean playerHasCrafted() {
        return hasCrafted;
    }
    public static int getNumWastedBlocks(){
        return numWastedBlocks;
    }
    

    public ModTutorialStatus(int tAccessed, int tCrafted, 
        int tLog, int tCraftingTable, int tWPick, int tSPick, int tFurnance, int tCopper,
        int numWasted,  int numPickaxe,
        boolean hSwapped, boolean hPlaced, boolean hAccessed, boolean hCrafted){

        set(tAccessed, tCrafted, 
            tLog, tCraftingTable, tWPick, tSPick, tFurnance, tCopper,
            numWasted, numPickaxe, 
            hSwapped, hPlaced, hAccessed, hCrafted);
    }
}
