package manueh.marvel.common.blocks;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import manueh.marvel.core.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class MjolnirBlock extends FallingBlock {

    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 1.0D, 12.0D, 16.0D, 15.0D);

    public MjolnirBlock(Properties p_i48401_1_) {

        super(p_i48401_1_);
    }

    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public Item asItem() {
        return ItemInit.MJOLNIR.get();
    }




}
