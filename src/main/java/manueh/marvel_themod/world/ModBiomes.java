package manueh.marvel_themod.world;

import manueh.marvel_themod.Main;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


import java.util.function.Supplier;

public class ModBiomes {
    public static final DeferredRegister<Biome> BIOMES
            = DeferredRegister.create(ForgeRegistries.BIOMES, Main.MODID);

    public static final RegistryObject<Biome> REALITY_BIOME = BIOMES.register("reality_biome",
            () -> makeRealityBiome(() -> ModConfiguredSurfaceBuilders.REALITY_SURFACE, 0.125f, 0.05f));

    private static Biome makeRealityBiome(final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {
        BiomeGenerationSettings.Builder biomegenerationsettings$builder =
                (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);
        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.farmAnimals(mobspawninfo$builder);
        DefaultBiomeFeatures.addDefaultCarvers(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultLakes(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addMossyStoneBlock(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultOres(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultOres(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addAncientDebris(biomegenerationsettings$builder);

        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);
        DefaultBiomeFeatures.addSurfaceFreezing(biomegenerationsettings$builder);

        return (new Biome.Builder()).mobSpawnSettings(MobSpawnInfo.EMPTY).precipitation(Biome.RainType.NONE).biomeCategory(Biome.Category.DESERT).depth(depth).scale(scale)
                .temperature(1.5F).downfall(0.9F).specialEffects((new BiomeAmbience.Builder()).waterColor(-3407872).waterFogColor(-16777216)
                        .fogColor(-65536).skyColor(getSkyColorWithTemperatureModifier(0.8F)).foliageColorOverride(-3407872).grassColorOverride(12267568)
                        .ambientParticle(new ParticleEffectAmbience(ParticleTypes.EFFECT, 0.003f)).skyColor(16743034)
                        .ambientLoopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                        .ambientMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0D))
                        .ambientAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, 0.0111D))

                        .build())
                .generationSettings(biomegenerationsettings$builder.build()).build();
    }

    private static int getSkyColorWithTemperatureModifier(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.2460909F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }

    public static void register(IEventBus eventBus) {
        BIOMES.register(eventBus);
    }
}