package manueh.marvel_themod.core.enums;

import com.google.common.collect.ImmutableMap;
import manueh.marvel_themod.TimeGemImpl;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;


public interface TimeGemAPI {
  public static final TimeGemAPI INSTANCE = (TimeGemAPI)new TimeGemImpl();
  
  ImmutableMap<ResourceLocation, Tier> getTiers();
  
  Tier getTier(ResourceLocation paramResourceLocation);
  
  boolean blacklistBlock(ResourceLocation paramResourceLocation);
  
  boolean blacklistBlock(Block paramBlock);
  
  @Deprecated
  boolean blacklistTileEntity(ResourceLocation paramResourceLocation);
  
  @Deprecated
  boolean blacklistTileEntity(TileEntityType<? extends TileEntity> paramTileEntityType);
  
  boolean isBlockBlacklisted(Block paramBlock);
  
  @Deprecated
  boolean isTileEntityBlacklisted(TileEntityType<? extends TileEntity> paramTileEntityType);
  
  boolean blacklistBlockEntity(ResourceLocation paramResourceLocation);
  
  boolean blacklistBlockEntity(TileEntityType<?> paramTileEntityType);
  
  boolean isBlockEntityBlacklisted(TileEntityType<?> paramTileEntityType);
}
