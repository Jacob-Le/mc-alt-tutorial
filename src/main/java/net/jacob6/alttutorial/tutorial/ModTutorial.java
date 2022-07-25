package net.jacob6.alttutorial.tutorial;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.fml.DistExecutor;
import net.jacob6.alttutorial.Messages;
import net.jacob6.alttutorial.gui.ModVignetteOverlay;
import net.jacob6.alttutorial.particle.ModParticles;
import net.jacob6.alttutorial.tutorial.data.ModTutorialContent;
import net.jacob6.alttutorial.tutorial.data.ModTutorialStatus;
import net.jacob6.alttutorial.tutorial.network.packets.*;

import java.util.List;

import org.slf4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ModTutorial{
   private static ModTutorialToast currentToast;
   private static List<ModTimedToast> timedToasts = Lists.newArrayList();
   private static List<GlowInstance> glowInstances = Lists.newArrayList();

   private static final int INSUFFICIENT_HINT_DURATION = 400;
   private static final int SWAP_HINT_DURATION = 400;
   private static final int GLOW_PARTICLE_INTERVAL = 15;
   private static final int MAX_TOOL_PROMPTS = 7;

   private static final Logger LOGGER = LogUtils.getLogger();

   public enum TutorialStepID{
      NONE(-1),
      SWAP_ITEMS(0),
      CRAFT_DRAG_ITEM(1),
      CRAFT_INVENTORY_ITEM(2),
      CRAFT_DONE(3),
      PLACE_BLOCK(4),
      ACCESS_CRAFTING_TABLE(5),
      ACCESS_DONE(6),
      GET_BETTER_TOOLS(7);

      private final int id;

      TutorialStepID(int id){
         this.id = id;
      }
   }

   // Step 0.5 - player has more than 2 items, tell them how to swap items
   public static void promptSwapItems() {
      // Check if the player has already recieved the tutorial
      if(!ModTutorialStatus.playerHasSwapped() && !hasActiveTimedToast()){
         LOGGER.info("------------ Player has gotten more than 1 item, tell them to swap -------------");

         // Add timed toast
         addTimedToast(new ModTutorialToast(TutorialToast.Icons.MOUSE, 
            ModTutorialContent.SWAP_TITLE, 
            ModTutorialContent.SWAP_DESCRIPTION,
            true,
            TutorialStepID.SWAP_ITEMS), SWAP_HINT_DURATION);

         // Send message to server that player has swapped
         Messages.sendToServer(new SwappedItemPacket());
      }
   }

   // Step 1 - player opens inventory, tell them to drag item to slot
   public static void promptCraftDragItem(boolean isInventory){
      // Check if the player has already recieved the tutorial
      // NOTE: This will be called every draw if inventory screen is open, need to put in checks not to add too many toasts
      if(!hasCurrentToast() &&
            !ModTutorialStatus.playerHasCrafted()){
            
         LOGGER.info("------------ Player opened inventory, telling to drag item to slot -------------");
         setCurrentToast(new ModTutorialToast(TutorialToast.Icons.MOUSE,
            ModTutorialContent.CRAFT_DRAG_TITLE,
            ModTutorialContent.CRAFT_DRAG_DESCRIPTION,
            false,
            TutorialStepID.CRAFT_DRAG_ITEM));
      
         // Display Vignette for dragging item -> matrix
         if(isInventory){
            ModVignetteOverlay.setVignetteMode(ModVignetteOverlay.VignetteMode.INVENTORY_TO_MATRIX);
         }else{
            ModVignetteOverlay.setVignetteMode(ModVignetteOverlay.VignetteMode.CRAFTING_TO_MATRIX);
         }
      }
   }

   // Step 2 - crafting matrix has the correct items, show tell player to click and drag from result
   public static void promptCraftToInventory(boolean isInventory){
      // NOTE: This will be called every tick if inventory screen is open, need to put in checks not to add too many toasts
      if (getCurrentStep() == TutorialStepID.CRAFT_DRAG_ITEM && 
            getCurrentStep() != TutorialStepID.NONE && 
            !ModTutorialStatus.playerHasCrafted()){
        
         LOGGER.info("------------ Player has filled matrix, tell them to drag to inventory -------------");
         setCurrentToast(new ModTutorialToast(TutorialToast.Icons.MOUSE, 
            ModTutorialContent.CRAFT_INVENTORY_TITLE, 
            ModTutorialContent.CRAFT_INVENTORY_DESCRIPTION, 
            false,
            TutorialStepID.CRAFT_INVENTORY_ITEM));
         // Display Vignette for dragging item -> inventory
         if(isInventory){
            ModVignetteOverlay.setVignetteMode(ModVignetteOverlay.VignetteMode.INVENTORY_TO_INVENTORY);
         }else{
            ModVignetteOverlay.setVignetteMode(ModVignetteOverlay.VignetteMode.CRAFTING_TO_INVENTORY);
         }
      }
   }

   // Handle if the container is closed
   public static void closeVignettes(){
      ModVignetteOverlay.setVignetteMode(ModVignetteOverlay.VignetteMode.NONE);
      clearCurrentToast();
   }

   // Step 2.5 - player has crafted an item, hide the toast
   public static void promptCraftDone(){
      if(hasCurrentToast() && !ModTutorialStatus.playerHasCrafted()){
         // Notify server that the player has crafted an item
         Messages.sendToServer(new CraftedItemPacket());
         closeVignettes();

         LOGGER.info("------------ Player has crafted an item, cleaning up -------------");
      }
   }

   // Step 3 - player has crafted a crafting table, inform them how to place a block
   public static void promptPlaceBlockHint() {
      if(!ModTutorialStatus.playerHasPlaced()){
         // Notify server that the player placed a block

         // Display toast
         setCurrentToast(new ModTutorialToast(TutorialToast.Icons.RIGHT_CLICK, 
            ModTutorialContent.PLACE_TITLE, 
            ModTutorialContent.PLACE_DESCRIPTION, 
            false,
            TutorialStepID.PLACE_BLOCK));

         LOGGER.info("------------ Player has gotten a block to place -------------");
      }
   }

   // Step 4 - player has placed a crafting table, inform them how to access the table
   public static void promptAccessEvent(BlockSnapshot snapshot){
     // Check if the player has already recieved the tutorial
     if(!ModTutorialStatus.playerHasAccessed()){
         // Notify server that the player has accessed the crafting table

         setCurrentToast(new ModTutorialToast(TutorialToast.Icons.RIGHT_CLICK,
            ModTutorialContent.ACCESS_TITLE, 
            ModTutorialContent.ACCESS_DESCRIPTION,
            false,
            TutorialStepID.ACCESS_CRAFTING_TABLE));

         BlockPos pos = snapshot.getPos();

         // Make the crafting table glow!
         DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ModTutorial.addGlowInstance(new GlowInstance(Minecraft.getInstance().level, pos, TutorialStepID.ACCESS_CRAFTING_TABLE)));

         // Inform the server
         Messages.sendToServer(new PlacedBlockPacket());

         LOGGER.info(String.format("------------ Placed a crafting table at (%d, %d, %d) -------------", pos.getX(), pos.getY(), pos.getZ()));
      }
   }

   // Step 4.5 - player has accessed the crafting table, clear toasts
   public static void promptAccessDone(){
      if(hasCurrentToast() && !ModTutorialStatus.playerHasAccessed()){
         clearCurrentToast();

         removeGlowInstance(TutorialStepID.ACCESS_CRAFTING_TABLE);

         Messages.sendToServer(new AccessedCraftingTablePacket());
         LOGGER.info("------------ Cleaning up access tutorial step -------------");
      }
   }

   // Player doesn't know that they need to get better tools
   public static void promptGetBetterToolsHint(){

      // TODO: only prompt a set number of times
      if(!hasActiveTimedToast()){
         // Display toast
         addTimedToast(new ModTutorialToast(TutorialToast.Icons.RECIPE_BOOK, 
            ModTutorialContent.TOOLS_TITLE,
            ModTutorialContent.TOOLS_DESCRIPTION, 
            true,
            TutorialStepID.GET_BETTER_TOOLS), INSUFFICIENT_HINT_DURATION);
      }
      // send message that increment number of times the player has broken a block they don't have tools for
      Messages.sendToServer(new AddWastedBlockPacket());
   }

   public static void handleVignetteDraw(int x, int y, int width, int height) {
      if(getCurrentStep() == TutorialStepID.CRAFT_DRAG_ITEM || getCurrentStep() == TutorialStepID.CRAFT_INVENTORY_ITEM){
         ModVignetteOverlay.doRender(x, y, width, height);
      }
   }

   public static void setCurrentToast(ModTutorialToast toast){
      clearCurrentToast();
      // Set the reference
      currentToast = toast;
      // Display the toast
      Minecraft.getInstance().getToasts().addToast(currentToast);
   }

   public static void addTimedToast(ModTutorialToast toast, int duration){
      timedToasts.add(new ModTimedToast(toast, duration));
      Minecraft.getInstance().getToasts().addToast(toast);
   }

   public static void addGlowInstance(GlowInstance instance){
      glowInstances.add(instance);
   }

   public static void removeGlowInstance(TutorialStepID id){
      glowInstances.removeIf((instance) -> instance.id == id);
   }

   public static void clearCurrentToast(){
      if (currentToast != null) {
         currentToast.hide();
         currentToast = null;
      }
   }

   public static boolean hasCurrentToast(){
      return currentToast != null;
   }

   public static TutorialStepID getCurrentStep(){
      if(currentToast != null){
         return currentToast.getID();
      }else{
         return TutorialStepID.NONE;
      }
   }

   public static boolean hasActiveTimedToast(){
      return !timedToasts.isEmpty();
   }

   public static void tick() {
      timedToasts.removeIf(ModTimedToast::updateProgress);
      glowInstances.forEach((glowInstance) -> glowInstance.glowPulse());
   }

   @OnlyIn(Dist.CLIENT)
   static final class GlowInstance {
      public final TutorialStepID id;
      private final Level level;
      private final BlockPos pos;
      private int progress;

      GlowInstance(Level level, BlockPos pos, TutorialStepID id){
         this.level = level;
         this.pos = pos;
         this.id = id;
         this.progress = 0;
      }

      private void glowPulse(){
         if(++this.progress % ModTutorial.GLOW_PARTICLE_INTERVAL == 0){
            for(int i = 0; i < 360; i++){
               if(i % 20 == 0){
                  this.level.addParticle(ModParticles.MOD_GLOW_PARTICLES.get(),
                     this.pos.getX() + 0.5d, this.pos.getY(), this.pos.getZ() + 0.5d,
                     Math.cos(i * Math.sin(this.progress)) * Mth.lerp(0.1d, 0.25d, (double) i/360), // X Velociity
                     0.15d * (double) i/360,   // Y velocity
                     Math.sin(i * Math.sin(this.progress)) * Mth.lerp(0.1d, 0.25d, (double) i/360)); // Z Velocity
               }
            }
         }
         if(this.progress == 100000){ this.progress = 0; }
      }
   }

   @OnlyIn(Dist.CLIENT)
   static final class ModTimedToast {
      final TutorialToast toast;
      private final int durationTicks;
      private int progress;

      ModTimedToast(TutorialToast toast, int duration) {
         this.toast = toast;
         this.durationTicks = duration;
      }

      private boolean updateProgress() {
         this.toast.updateProgress(Math.min((float)(++this.progress) / (float)this.durationTicks, 1.0F));
         if (this.progress > this.durationTicks) {
            this.toast.hide();
            return true;
         } else {
            return false;
         }
      }
   }

   public static void recordCraftedItem(Item item) {
      // Check when crafting table is crafted
      if(item == Items.CRAFTING_TABLE){
         Messages.sendToServer(new CompletedCraftingTablePacket());
      }

      // Check when wooden pickaxe is crafted
      if(item == Items.WOODEN_PICKAXE){
         Messages.sendToServer(new CompletedWPickaxePacket());
      }

      // Check when stone pickaxe is crafted
      if(item == Items.STONE_PICKAXE){
         Messages.sendToServer(new CompletedSPickaxePacket());
      }

      // Check when furnace is crafted
      if(item == Items.FURNACE){
         Messages.sendToServer(new CompletedFurnacePacket());
      }
   }

   public static void recordSmeltedItem(Item item) {
      // Check when copper ingot is smelted
      if(item == Items.COPPER_INGOT){
         Messages.sendToServer(new CompletedCopperPacket());
      }
   }

   public static void recordLogGotten() {
      // Check if log has been broken and if it has been recorded before
      Messages.sendToServer(new CompletedLogPacket());
   }

   public static void recordPickaxeBlockGotten() {
      Messages.sendToServer(new AddPickaxeBlockPacket());
   }
}

   