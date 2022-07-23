package net.jacob6.alttutorial.tutorial.data;

public class ModTutorialStatus {
    // Client side status
    private static boolean hasSwapped;
    private static boolean hasPlaced;
    private static int timeAccessed;
    private static int timeCrafted;
    private static boolean hasAccessed;
    private static boolean hasCrafted;
    private static int numWastedBlocks;

    // Setters
    public static void set(int tAccessed, int tCrafted, int numWasted, boolean hSwapped, boolean hPlaced, boolean hAccessed, boolean hCrafted){
        timeAccessed = tAccessed;
        timeCrafted = tCrafted;
        numWastedBlocks = numWasted;
        hasSwapped = hSwapped;
        hasPlaced = hPlaced;
        hasAccessed = hAccessed;
        hasCrafted = hCrafted;
    }
    public static void setTimeAccessed(int tAccessed) {
        timeAccessed = tAccessed;
    }
    public static void setTimeCrafted(int tCrafted) {
        timeCrafted = tCrafted;
    }
    public static void setHasSwapped(boolean hSwapped) {
        hasSwapped = hSwapped;
    }
    public static void setHasPlaced(boolean hPlaced) {
        hasPlaced = hPlaced;
    }
    public static void setHasAccessed(boolean hAccessed) {
        hasAccessed = hAccessed;
    }
    public static void setHasCrafted(boolean hCrafted) {
        hasCrafted = hCrafted;
    }
    
    // Getters
    public static int getTimeAccessed() {
        return timeAccessed;
    }
    public static int getTimeCrafted() {
        return timeCrafted;
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
    

    public ModTutorialStatus(int tAccessed, int tCrafted, int numWasted, boolean hSwapped, boolean hPlaced, boolean hAccessed, boolean hCrafted){
        set(tAccessed, tCrafted, numWasted, hSwapped, hPlaced, hAccessed, hCrafted);
    }
}
