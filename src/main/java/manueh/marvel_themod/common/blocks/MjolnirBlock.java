package manueh.marvel_themod.common.blocks;

import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MjolnirBlock extends FallingBlock {

    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 1.0D, 12.0D, 16.0D, 15.0D);

    public MjolnirBlock(Properties p_i48401_1_) {

        super(p_i48401_1_);
    }

    public static VoxelShape box(double p_49797_, double p_49798_, double p_49799_, double p_49800_, double p_49801_, double p_49802_) {
        return SHAPE;
    }

    @Override
    public Item asItem() {
        return ItemInit.MJOLNIR.get();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {
        level.destroyBlock(blockPos, true);
        player.addItem(new ItemStack(ItemInit.MJOLNIR.get()));
        return InteractionResult.SUCCESS;
    }




}
