package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.blocks.TimeGemBlock;
import manueh.marvel_themod.common.entity.TimeGemBlockEntity;
import manueh.marvel_themod.core.enums.TimeGemBlockEntityType;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeInit {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Main.MODID);

    public static final RegistryObject<TileEntityType<TimeGemBlockEntity>> TIME_GEM_TILE_ENTITY = TILE_ENTITY_TYPE.register("time_gem_entity", () -> TileEntityType.Builder.of(TimeGemBlockEntity::new, BlockInit.TIME_GEM_BLOCK.get()).build(null));

}
