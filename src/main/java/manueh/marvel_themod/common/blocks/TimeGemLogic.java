package manueh.marvel_themod.common.blocks;

import manueh.marvel_themod.Config;
import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.entity.TimeGemBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public final class TimeGemLogic {
  public static void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    if (world.isClientSide())
      return; 
    TileEntity tileEntity = world.getBlockEntity(pos);
    if (tileEntity instanceof TimeGemBlockEntity) {
      TimeGemBlockEntity blockEntity = (TimeGemBlockEntity)tileEntity;
      blockEntity.tick();
    } 
  }
  

  
  public static void neighborUpdate(BlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean isMoving) {
    if (world.isClientSide())
      return; 
    TileEntity tileEntity = world.getBlockEntity(pos);
    if (tileEntity instanceof TimeGemBlockEntity) {
      TimeGemBlockEntity blockEntity = (TimeGemBlockEntity)tileEntity;

    } 
  }
  
  public static void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack, Block block) {
    if (world.isClientSide())
      return; 
    TileEntity tileEntity = world.getBlockEntity(pos);
    if (tileEntity instanceof TimeGemBlockEntity) {
      TimeGemBlockEntity blockEntity = (TimeGemBlockEntity)tileEntity;
      if (stack.hasCustomHoverName())
        blockEntity.setCustomName(stack.getHoverName());

  }

  }
}