package manueh.marvel_themod;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import manueh.marvel_themod.core.enums.Tier;
import manueh.marvel_themod.core.enums.TimeGemAPI;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class TimeGemImpl implements TimeGemAPI {
  private final Logger logger = LogManager.getLogger("timegem-api");
  

  private final Map<ResourceLocation, Tier> localTiers = new HashMap<>();
  
  private final Set<Block> blacklistedBlocks = new HashSet<>();
  
  private final Set<BlockEntityType<?>> blacklistedTiles = new HashSet<>();
  
  private Map<ResourceLocation, Tier> remoteTiers = new HashMap<>();
  
  public void registerTier(ResourceLocation name, int maxSpeed, int xzRange, int yRange) {
    if (this.localTiers.containsKey(name)) {
      this.logger.warn("Tier with id {} has already been registered.", name);
      return;
    } 
    Tier tier = new Tier(maxSpeed, xzRange, yRange);
    this.localTiers.put(name, tier);
  }
  
  public boolean blacklistBlock(ResourceLocation blockId) {
    Optional<Block> block = Registry.BLOCK.getOptional(blockId);
    if (block.isPresent()) {
      if (this.blacklistedBlocks.contains(block.get())) {
        this.logger.warn("Block with id {} is already blacklisted.", block);
        return false;
      } 
      this.blacklistedBlocks.add(block.get());
      return true;
    } 
    this.logger.warn("Block with id {} does not exist.", block);
    return false;
  }
  
  public boolean blacklistBlock(Block block) {
    if (this.blacklistedBlocks.contains(block)) {
      this.logger.warn("Block with id {} is already blacklisted.", Registry.BLOCK.getKey(block));
      return false;
    } 
    this.blacklistedBlocks.add(block);
    return true;
  }
  
  public boolean blacklistTileEntity(ResourceLocation blockEntityTypeId) {
    return blacklistBlockEntity(blockEntityTypeId);
  }
  
  public boolean blacklistTileEntity(BlockEntityType<? extends BlockEntity> blockEntityType) {
    return blacklistBlockEntity(blockEntityType);
  }
  
  public boolean isBlockBlacklisted(Block block) {
    return this.blacklistedBlocks.contains(block);
  }
  
  public boolean isTileEntityBlacklisted(BlockEntityType<? extends BlockEntity> blockEntityType) {
    return isBlockEntityBlacklisted(blockEntityType);
  }
  
  public boolean blacklistBlockEntity(ResourceLocation blockEntityTypeId) {
    Optional<BlockEntityType<?>> blockEntityType = Registry.BLOCK_ENTITY_TYPE.getOptional(blockEntityTypeId);
    if (blockEntityType.isPresent()) {
      if (this.blacklistedTiles.contains(blockEntityType.get())) {
        this.logger.warn("BlockEntityType with id {} is already blacklisted.", blockEntityTypeId);
        return false;
      } 
      this.blacklistedTiles.add(blockEntityType.get());
      return true;
    } 
    this.logger.warn("BlockEntityType with id {} does not exist.", blockEntityTypeId);
    return false;
  }
  
  public boolean blacklistBlockEntity(BlockEntityType<?> blockEntityType) {
    if (this.blacklistedTiles.contains(blockEntityType)) {
      this.logger.warn("BlockEntityType with id {} is already blacklisted.", Registry.BLOCK_ENTITY_TYPE.getKey(blockEntityType));
      return false;
    } 
    this.blacklistedTiles.add(blockEntityType);
    return true;
  }
  
  public boolean isBlockEntityBlacklisted(BlockEntityType<?> blockEntityType) {
    return this.blacklistedTiles.contains(blockEntityType);
  }
  
  public void setRemoteTiers(Map<ResourceLocation, Tier> tiers) {
    this.remoteTiers = tiers;
  }
  
  public ImmutableMap<ResourceLocation, Tier> getTiers() {
    return ImmutableMap.copyOf(this.localTiers);
  }
  
  public Tier getTier(ResourceLocation name) {
    return this.remoteTiers.getOrDefault(name, null);
  }
}
