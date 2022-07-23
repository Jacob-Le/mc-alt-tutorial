package net.jacob6.alttutorial.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModGlowParticles extends TextureSheetParticle{

    protected ModGlowParticles(ClientLevel level, double x, double y, double z,
            SpriteSet sprites, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        
        this.friction = 0.8f;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.quadSize *= 0.85f;
        this.lifetime = 20;
        this.setSpriteFromAge(sprites);
        
        this.rCol = 1f; 
        this.gCol = 1f;
        this.bCol = 1f;
    }

    @Override
    public void tick(){
        super.tick();
        fadeOut();
    }

    private void fadeOut(){
        this.alpha = (-(1/(float) lifetime) * age + 1);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType>{
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites){
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x,
                double y, double z, double dx, double dy, double dz) {

            return new ModGlowParticles(level, x, y, z, sprites, dx, dy, dz);
        }

    }
}
