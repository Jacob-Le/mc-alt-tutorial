package net.jacob6.alttutorial.tutorial.data;

import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.client.tutorial.Tutorial;

public class ModTutorialContent {

    // Swap items
    public static final Component SWAP_TITLE = Component.translatable("alttutorial.swap.title").withStyle(ChatFormatting.DARK_PURPLE);;
    public static final Component SWAP_DESCRIPTION = Component.translatable("alttutorial.swap.description");

    // Crafting - drag items to matrix
    public static final Component CRAFT_DRAG_TITLE = Component.translatable("alttutorial.craft_drag.title").withStyle(ChatFormatting.DARK_PURPLE);;
    public static final Component CRAFT_DRAG_DESCRIPTION = Component.translatable("alttutorial.craft_drag.description");

    // Crafting - drag from result slot
    public static final Component CRAFT_INVENTORY_TITLE = Component.translatable("alttutorial.craft_inventory.title").withStyle(ChatFormatting.DARK_PURPLE);
    public static final Component CRAFT_INVENTORY_DESCRIPTION = Component.translatable("alttutorial.craft_inventory.description", Tutorial.key("right"));

    // Place block
    public static final Component PLACE_TITLE = Component.translatable("alttutorial.place.title").withStyle(ChatFormatting.DARK_PURPLE);;
    public static final Component PLACE_DESCRIPTION = Component.translatable("alttutorial.place.description");

    // Access block by right clicking
    public static final Component ACCESS_TITLE = Component.translatable("alttutorial.access.title").withStyle(ChatFormatting.DARK_PURPLE);
    public static final Component ACCESS_DESCRIPTION = Component.translatable("alttutorial.access.description");

    // Get better tools
    public static final Component TOOLS_TITLE = Component.translatable("alttutorial.tools.title").withStyle(ChatFormatting.DARK_PURPLE);
    public static final Component TOOLS_DESCRIPTION = Component.translatable("alttutorial.tools.description");
}
