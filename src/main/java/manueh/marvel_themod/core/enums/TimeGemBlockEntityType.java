package manueh.marvel_themod.core.enums;

import com.mojang.datafixers.types.Type;
import manueh.marvel_themod.common.entity.TimeGemBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;

import java.util.function.Supplier;

@Deprecated
public class TimeGemBlockEntityType extends TileEntityType<TimeGemBlockEntity> {
  public TimeGemBlockEntityType(Supplier<TimeGemBlockEntity> supplier, Type type) {
    super(supplier, null, type);
  }
  
  public boolean func_223045_a(Block block) {
    return block instanceof TierSupplier;
  }
}
