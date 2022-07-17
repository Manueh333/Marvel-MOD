package manueh.marvel_themod.core.enums;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

public interface TierSupplier {
  ResourceLocation getTier();

  @Nullable
  BlockEntity newBlockEntity(BlockGetter p_196283_1_);
}