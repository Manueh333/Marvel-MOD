package manueh.marvel_themod.common.blocks;

import manueh.marvel_themod.Config;
import manueh.marvel_themod.common.entity.TimeGemBlockEntity;
import manueh.marvel_themod.core.enums.TimeGemBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;
import java.util.function.Consumer;

public final class TimeGemLogic {
  public static void neighborUpdate(BlockState state, Level world, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean isMoving,
                                    Consumer<TimeGemBlockEntity> func) {
    if (world.isClientSide) {
      return;
    }
    if (world.getBlockEntity(pos) instanceof TimeGemBlockEntity blockEntity) {
      func.accept(blockEntity);
    }
  }

  public static void onPlaced(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack, Block block) {
    if (world.isClientSide) {
      return;
    }
    if (world.getBlockEntity(pos) instanceof TimeGemBlockEntity blockEntity) {
      if (stack.hasCustomHoverName()) {
        blockEntity.setCustomName(stack.getHoverName());
      }

    }

  }

  public static <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
    if (type instanceof TimeGemBlockEntityType) {
      return (level1, pos, state1, entity) -> TimeGemBlockEntity.tick(level1, pos, state1, (TimeGemBlockEntity) entity);
    }
    return null;
  }
}