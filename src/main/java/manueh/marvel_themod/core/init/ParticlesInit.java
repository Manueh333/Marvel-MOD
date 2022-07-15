package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticlesInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Main.MODID);

    public static final RegistryObject<BasicParticleType> SPACE_GEM_PARTICLE =
            PARTICLE_TYPES.register("space_gem_particle", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> IRONMAN_BEAM_PARTICLE =
            PARTICLE_TYPES.register("ironman_beam_particle", () -> new BasicParticleType(true));

}
