package net.jacob6.alttutorial.event;

import net.jacob6.alttutorial.MCAltTutorial;
import net.jacob6.alttutorial.particle.ModGlowParticles;
import net.jacob6.alttutorial.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = MCAltTutorial.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientBusEvents {
    
    @SubscribeEvent
    public static void registerParticleFactories(final ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particleEngine.register(ModParticles.MOD_GLOW_PARTICLES.get(), ModGlowParticles.Provider::new);
    }
}
