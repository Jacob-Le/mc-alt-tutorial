package net.jacob6.alttutorial.tutorial.data;

public class ModTutorialStatus {
    // Client side status
    private static int timeAccessed;
    private static int timeCrafted;
    private static boolean hasAccessed;
    private static boolean hasCrafted;

    // Setters
    public static void set(int tAccessed, int tCrafted, boolean hAccessed, boolean hCrafted){
        timeAccessed = tAccessed;
        timeCrafted = tCrafted;
        hasAccessed = hAccessed;
        hasCrafted = hCrafted;
    }
    public static void setTimeAccessed(int tAccessed) {
        timeAccessed = tAccessed;
    }
    public static void setTimeCrafted(int tCrafted) {
        timeCrafted = tCrafted;
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
    public static boolean playerHasAccessed() {
        return hasAccessed;
    }
    public static boolean playerHasCrafted() {
        return hasCrafted;
    }

    public ModTutorialStatus(int tAccessed, int tCrafted, boolean hAccessed, boolean hCrafted){
        set(tAccessed, tCrafted, hAccessed, hCrafted);
    }
}
