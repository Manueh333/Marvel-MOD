package manueh.marvel_themod.core.enums;

import com.google.common.collect.ImmutableMap;
import manueh.marvel_themod.TimeGemImpl;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.resources.ResourceLocation;


public interface TimeGemAPI {
  public static final TimeGemAPI INSTANCE = (TimeGemAPI)new TimeGemImpl();
  
  ImmutableMap<ResourceLocation, Tier> getTiers();
  
  Tier getTier(ResourceLocation paramResourceLocation);
  
  boolean blacklistBlock(ResourceLocation paramResourceLocation);
  
  boolean blacklistBlock(Block paramBlock);
  
  @Deprecated
  boolean blacklistTileEntity(ResourceLocation paramResourceLocation);
  
  @Deprecated
  boolean blacklistTileEntity(BlockEntityType<? extends BlockEntity> paramTileEntityType);
  
  boolean isBlockBlacklisted(Block paramBlock);
  
  @Deprecated
  boolean isTileEntityBlacklisted(BlockEntityType<? extends BlockEntity> paramTileEntityType);
  
  boolean blacklistBlockEntity(ResourceLocation paramResourceLocation);
  
  boolean blacklistBlockEntity(BlockEntityType<?> paramTileEntityType);
  
  boolean isBlockEntityBlacklisted(BlockEntityType<?> paramTileEntityType);
}
