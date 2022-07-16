package net.jacob6.alttutorial.tutorial.client;

import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class ModTutorialContent {
    private static final Component ACCESS_TITLE = Component.translatable("alttutorial.access.title");
    private static final Component ACCESS_DESCRIPTION = Component.translatable("alttutorial.access.description",  Component.literal("Right Mouse Button").withStyle(ChatFormatting.BOLD));
    private static final Component CRAFT_TITLE = Component.translatable("alttutorial.crafting.title");
    private static final Component CRAFT_DESCRIPTION = Component.translatable("alttutorial.crafting.description", 
        Component.literal("Left Mouse Button ").withStyle(ChatFormatting.BOLD), 
        Component.literal("to "), 
        Component.literal("Drag ").withStyle(ChatFormatting.BOLD),
        Component.literal("to your inventory"));

    public static Component getAccessTitle(){
        return ACCESS_TITLE;
    }
    public static Component getAccessDescription() {
        return ACCESS_DESCRIPTION;
    }

    public static Component getCraftTitle() {
        return CRAFT_TITLE;
    }
    public static Component getCraftDescription() {
        return CRAFT_DESCRIPTION;
    }
}
