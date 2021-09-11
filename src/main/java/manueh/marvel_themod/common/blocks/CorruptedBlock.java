package manueh.marvel_themod.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class CorruptedBlock extends Block {

    public CorruptedBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
        BubbleColumnBlock.growColumn(p_225534_2_, p_225534_3_.above(), true);
    }

    @Override
    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        if (p_196271_2_ == Direction.UP && p_196271_3_.is(Blocks.WATER)) {
            p_196271_4_.getBlockTicks().scheduleTick(p_196271_5_, this, 20);
        }

        return super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }

    @Override
    public void randomTick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
        BlockPos blockpos = p_225542_3_.above();
        p_225542_2_.playSound((PlayerEntity)null, p_225542_3_, SoundEvents.CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F, 2.6F + (p_225542_2_.random.nextFloat() - p_225542_2_.random.nextFloat()) * 0.8F);
        p_225542_2_.sendParticles(new RedstoneParticleData(255, 60, 60, 1), (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D, (double)blockpos.getZ() + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D);

    }
}
