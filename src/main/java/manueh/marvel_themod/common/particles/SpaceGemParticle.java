package manueh.marvel_themod.common.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;



public class SpaceGemParticle extends TextureSheetParticle {
   protected SpaceGemParticle(ClientLevel level, double xCoord, double yCoord, double zCoord,
                               SpriteSet spriteSet, double xd, double yd, double zd) {
      super(level, xCoord, yCoord, zCoord, xd, yd, zd);

      this.roll = 0.8F;
      this.xd = xd;
      this.yd = yd;
      this.zd = zd;
      this.quadSize *= 0.85F;
      this.lifetime = 20;
      this.setSpriteFromAge(spriteSet);

      this.rCol = 60F / 255F;
      this.gCol = 169F / 255F;
      this.bCol = 255F / 255F;
   }

   @Override
   public void tick() {
      super.tick();
      fadeOut();
   }

   private void fadeOut() {
      this.alpha = (-(1/(float)lifetime) * age + 1);
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Factory(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                     double x, double y, double z,
                                     double dx, double dy, double dz) {
         Particle particle = new SpaceGemParticle(level, x, y, z, this.sprites, dx, dy, dz);
         return particle;
      }
   }
}