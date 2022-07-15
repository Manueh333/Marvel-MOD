package manueh.marvel_themod.world;

import manueh.marvel_themod.Main;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;


public class ModConfiguredSurfaceBuilders {
    public static ConfiguredSurfaceBuilder<?> REALITY_SURFACE = register("reality_surface",
            SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(
                    Blocks.GRASS_BLOCK.defaultBlockState(),
                    Blocks.MAGMA_BLOCK.defaultBlockState(),
                    Blocks.RED_GLAZED_TERRACOTTA.defaultBlockState()
            )));

    private static <SC extends ISurfaceBuilderConfig>ConfiguredSurfaceBuilder<SC> register(String name,
                                                                                           ConfiguredSurfaceBuilder<SC> csb) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER,
                new ResourceLocation(Main.MODID, name), csb);
    }
}