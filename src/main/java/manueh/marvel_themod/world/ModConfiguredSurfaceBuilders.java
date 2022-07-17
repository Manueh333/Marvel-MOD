package manueh.marvel_themod.world;

import manueh.marvel_themod.Main;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;


public class ModConfiguredSurfaceBuilders {
    public static ConfiguredSurfaceBuilder<?> REALITY_SURFACE = register("reality_surface",
            SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderBaseConfiguration(
                    Blocks.GRASS_BLOCK.defaultBlockState(),
                    Blocks.MAGMA_BLOCK.defaultBlockState(),
                    Blocks.RED_GLAZED_TERRACOTTA.defaultBlockState()
            )));

    private static <SC extends SurfaceBuilderConfiguration>ConfiguredSurfaceBuilder<SC> register(String name,
                                                                                           ConfiguredSurfaceBuilder<SC> csb) {
        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER,
                new ResourceLocation(Main.MODID, name), csb);
    }
}