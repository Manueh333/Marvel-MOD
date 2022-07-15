package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DimensionInit {
	public static final RegistryKey<World> REALITY_DIMENSION = RegistryKey.create(Registry.DIMENSION_REGISTRY,
			new ResourceLocation(Main.MODID, "reality_dimension"));
}