package manueh.marvel_themod.common.blocks;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class CorruptedBlock extends Block {

    public CorruptedBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, LevelAccessor p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        if (p_196271_2_ == Direction.UP && p_196271_3_.is(Blocks.WATER)) {
            p_196271_4_.getBlockTicks().scheduleTick(p_196271_5_, this, 20);
        }

        return super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }

    @Override
    public void randomTick(BlockState p_225542_1_, ServerLevel p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
        BlockPos blockpos = p_225542_3_.above();
        p_225542_2_.playSound((Player)null, p_225542_3_, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F, 2.6F + (p_225542_2_.random.nextFloat() - p_225542_2_.random.nextFloat()) * 0.8F);
        p_225542_2_.sendParticles(new DustParticleOptions(new Vector3f(255, 60, 60), 1), (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D, (double)blockpos.getZ() + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D);

    }
}
