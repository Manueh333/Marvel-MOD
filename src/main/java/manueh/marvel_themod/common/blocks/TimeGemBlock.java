package manueh.marvel_themod.common.blocks;

import manueh.marvel_themod.common.entity.TimeGemBlockEntity;
import manueh.marvel_themod.core.enums.TierSupplier;
import manueh.marvel_themod.core.init.TileEntityTypeInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public final class TimeGemBlock extends Block implements ITileEntityProvider, TierSupplier {
  private final ResourceLocation tierID;
  
  public TimeGemBlock(AbstractBlock.Properties properties, ResourceLocation tier) {
    super(properties);
    this.tierID = tier;

  }
  
  public ResourceLocation getTier() {
    return this.tierID;
  }
  
  public TileEntity createNewTileEntity(IBlockReader level) {
    return (TileEntity)new TimeGemBlockEntity();
  }
  
  public PushReaction getPushReaction(BlockState state) {
    return PushReaction.IGNORE;
  }
  
  public void onBlockAdded(BlockState newState, World level, BlockPos pos, BlockState state, boolean boolean_1) {
    neighborChanged(null, level, pos, null, null, false);
  }
  
  public void tick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
    TimeGemLogic.scheduledTick(state, level, pos, random);
  }
  
  public void neighborChanged(BlockState state, World level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean boolean_1) {
    TimeGemLogic.neighborUpdate(state, level, pos, neighborBlock, neighborPos, boolean_1);
  }
  
  public void setPlacedBy(World level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    TimeGemLogic.onPlaced(level, pos, state, placer, stack, (Block)this);
  }
  
  public boolean hasTileEntity(BlockState state) {
    return true;
  }
  

  public TileEntity createTileEntity(BlockState state, IBlockReader level) {
    return TileEntityTypeInit.TIME_GEM_TILE_ENTITY.get().create();
  }

  @Nullable
  @Override
  public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
    return (TileEntity)new TimeGemBlockEntity();
  }
}