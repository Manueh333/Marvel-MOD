package manueh.marvel_themod.common.blocks;

import manueh.marvel_themod.common.entity.TimeGemBlockEntity;
import manueh.marvel_themod.core.enums.TierSupplier;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Random;

public final class TimeGemBlock extends Block implements EntityBlock, TierSupplier {
  private final ResourceLocation tierID;
  
  public TimeGemBlock(BlockBehaviour.Properties properties, ResourceLocation tier) {
    super(properties);
    this.tierID = tier;

  }

  @Override
  public Item asItem() {
    return ItemInit.INFINITY_GAUNTLET.get();
  }
  protected static final VoxelShape SHAPE = Block.box(5.0D, 6.0D, 5.0D, 11.0D, 10.0D, 11.0D);

  public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
    return SHAPE;
  }
  @Override
  public InteractionResult use(BlockState blockState, Level world, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockRayTraceResult) {
    if(player.getItemInHand(hand).sameItemStackIgnoreDurability(ItemInit.INFINITY_GAUNTLET.get().getDefaultInstance())) {
      ItemInit.INFINITY_GAUNTLET.get().setSlot(player.getItemInHand(hand), 0, ItemInit.TIME_GEM.get().getDefaultInstance());
      world.destroyBlock(blockPos, true);
    }
    return InteractionResult.SUCCESS;
  }


  public ResourceLocation getTier() {
    return this.tierID;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockGetter p_196283_1_) {
    return null;
  }


  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TimeGemBlockEntity(pos, state);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
    return TimeGemLogic.getTicker(level, state, type);
  }

  @Override
  public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean boolean_1) {
    TimeGemLogic.neighborUpdate(state, level, pos, neighborBlock, neighborPos, boolean_1, (be) ->
            be.getDisplayName());
  }

  @Override
  public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    TimeGemLogic.onPlaced(level, pos, state, placer, stack, this);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> GameEventListener getListener(Level p_153210_, T p_153211_) {
    return EntityBlock.super.getListener(p_153210_, p_153211_);
  }
}