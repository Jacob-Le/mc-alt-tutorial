package net.jacob6.alttutorial.tutorial;

import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.network.chat.Component;
import net.jacob6.alttutorial.tutorial.ModTutorial.TutorialStepID;;

public class ModTutorialToast extends TutorialToast {
    private final TutorialStepID id;

    public ModTutorialToast(Icons icon, Component title, Component message, boolean progressable, TutorialStepID id) {
        super(icon, title, message, progressable);
        this.id = id;
    }
    
    public TutorialStepID getID(){
        return this.id;
    }
}
