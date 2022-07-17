package manueh.marvel_themod.world;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class RealityTeleporter implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;

    public RealityTeleporter(BlockPos pos, boolean insideDim) {
        thisPos = pos;
        insideDimension = insideDim;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        double y = 61;

        if (!insideDimension) {
            y = thisPos.getY();
        }

        BlockPos destinationPos = new BlockPos(thisPos.getX(), y, thisPos.getZ());

        int tries = 0;
        while ((destinationWorld.getBlockState(destinationPos).getMaterial() != Material.AIR) &&
                !destinationWorld.getBlockState(destinationPos).canBeReplaced(Fluids.WATER) &&
                destinationWorld.getBlockState(destinationPos.above()).getMaterial() != Material.AIR &&
                !destinationWorld.getBlockState(destinationPos.above()).canBeReplaced(Fluids.WATER) && tries < 128) {
            destinationPos = destinationPos.below(1);
            tries++;
        }
        tries = 0;
        while ((destinationWorld.getBlockState(destinationPos).getMaterial() != Material.AIR) &&
                !destinationWorld.getBlockState(destinationPos).canBeReplaced(Fluids.WATER) &&
                destinationWorld.getBlockState(destinationPos.above()).getMaterial() != Material.AIR &&
                !destinationWorld.getBlockState(destinationPos.above()).canBeReplaced(Fluids.WATER) && tries < 256) {
            destinationPos = destinationPos.above(1);
            tries++;
        }



        entity.setPos(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());

        return entity;
    }
}