package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticlesInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Main.MODID);

    public static final RegistryObject<SimpleParticleType> SPACE_GEM_PARTICLE =
            PARTICLE_TYPES.register("space_gem_particle", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> IRONMAN_BEAM_PARTICLE =
            PARTICLE_TYPES.register("ironman_beam_particle", () -> new SimpleParticleType(true));

}
