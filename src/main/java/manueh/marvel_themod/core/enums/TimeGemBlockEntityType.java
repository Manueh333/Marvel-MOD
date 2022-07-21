package manueh.marvel_themod.core.enums;

import com.mojang.datafixers.types.Type;
import manueh.marvel_themod.common.entity.TimeGemBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;

@Deprecated
public class TimeGemBlockEntityType extends BlockEntityType<TimeGemBlockEntity> {
  public TimeGemBlockEntityType(BlockEntitySupplier<TimeGemBlockEntity> supplier, Type type) {
    super(supplier, null, type);
  }
  
  public boolean func_223045_a(Block block) {
    return block instanceof TierSupplier;
  }
}
