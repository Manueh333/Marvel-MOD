package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;

public class DimensionInit {
	public static final ResourceKey<Level> REALITY_DIMENSION = ResourceKey.create(Registry.DIMENSION_REGISTRY,
			new ResourceLocation(Main.MODID, "reality_dimension"));
}