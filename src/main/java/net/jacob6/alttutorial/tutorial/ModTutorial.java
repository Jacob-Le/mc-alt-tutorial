package net.jacob6.alttutorial.tutorial;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jacob6.alttutorial.Messages;
import net.jacob6.alttutorial.tutorial.client.ModTutorialContent;
import net.jacob6.alttutorial.tutorial.data.ModTutorialStatus;
import net.jacob6.alttutorial.tutorial.network.AccessedCraftingTablePacket;
import net.jacob6.alttutorial.tutorial.network.CraftedItemPacket;

import org.slf4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ModTutorial{
   private static TutorialToast accessToast;
   private static TutorialToast craftToast;

   private static final Logger LOGGER = LogUtils.getLogger();

   public static void promptAccessEvent(){
      
     // Check if the player has already recieved the tutorial
     if(!ModTutorialStatus.playerHasAccessed() && !ModTutorialStatus.playerHasCrafted()){
         // if true, create a toast

         accessToast = new TutorialToast(TutorialToast.Icons.RIGHT_CLICK,
            ModTutorialContent.getAccessTitle(), 
            ModTutorialContent.getAccessDescription(),
            true);

         // Send toast to Minecraft to display
         Minecraft.getInstance().getToasts().addToast(accessToast);
         LOGGER.info("------------ WOWOWEE WA WE DID A PLACE THING -------------");
      }
   }

   public static void handleAccessEvent(){
      if (!ModTutorialStatus.playerHasAccessed() && !ModTutorialStatus.playerHasCrafted()){
         // Notify server that the player has accessed the crafting table
         Messages.sendToServer(new AccessedCraftingTablePacket());
         clearAccessToast();
         
         craftToast = new TutorialToast(TutorialToast.Icons.MOUSE, 
            ModTutorialContent.getCraftTitle(), 
            ModTutorialContent.getCraftDescription(), 
            true);

         LOGGER.info("------------ DRAG TIME -------------");

         // Display crafting toast
         Minecraft.getInstance().getToasts().addToast(craftToast);
      }
   }

   public static void handleCraftedEvent(){
      if(!ModTutorialStatus.playerHasCrafted()){
         // Notify server that the player has crafted an item
         Messages.sendToServer(new CraftedItemPacket());
         clearCraftToast();

         LOGGER.info("------------ WOWOWEE ALL DONE -------------");
      }
   }

   public static void clearAccessToast() {
      if (accessToast != null) {
         accessToast.hide();
         accessToast = null;
      }
   }

   public static void clearCraftToast(){
      if (craftToast != null) {
         craftToast.hide();
         craftToast = null;
      }
   }

}

   