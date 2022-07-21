package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.entity.TimeGemBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileEntityTypeInit {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Main.MODID);

    public static final RegistryObject<BlockEntityType<TimeGemBlockEntity>> TIME_GEM_TILE_ENTITY = TILE_ENTITY_TYPE.register("time_gem_entity", () -> BlockEntityType.Builder.of(TimeGemBlockEntity::new, BlockInit.TIME_GEM_BLOCK.get()).build(null));

}
