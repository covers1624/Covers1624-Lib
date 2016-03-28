package covers1624.lib.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Created by covers1624 on 12/16/2015.
 */
public class RotationUtils {

    //TODO getPlacedRotation(World world, BlockPos pos, EntityLivingBase entity, boolean includeVirt);

    /**
     * Gets the rotation for placing a block, Does not factor in Up and Down.
     */
    public static EnumFacing getPlacedRotationHorizontal(World world, BlockPos blockPos, EntityLivingBase entity) {
        int facing = MathHelper.floor_double((entity.rotationYaw * 4F) / 360F + 0.5D) & 3;
        return entityRotationToSide(facing);
    }

    /**
     * Gets rotation for placing a block, Will use Up and Down.
     */
    public static EnumFacing getPlacedRotationAdvanced(World world, BlockPos blockPos, EntityLivingBase entity) {
        int entityRotation = (int) Math.floor(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        EnumFacing direction = EnumFacing.NORTH;
        boolean ignoreRotation = false;
        if (Math.abs(entity.posX - blockPos.getX()) < 2.0D && Math.abs(entity.posZ - blockPos.getZ()) < 2.0D) {
            //TODO, Config for EntityRotation use or Block face placed.
            //TODO Entity Eye height.
            double entityYRotation = entity.posY + 1.82D - blockPos.getY();

            if (entityYRotation > 2.0D) {
                ignoreRotation = true;
                direction = EnumFacing.DOWN;
            }

            if (entityYRotation < 0.0D) {
                ignoreRotation = true;
                direction = EnumFacing.UP;
            }
        }
        if (!ignoreRotation) {
            switch (entityRotation) {
            case 0:
                direction = EnumFacing.SOUTH;
                break;
            case 1:
                direction = EnumFacing.WEST;
                break;
            case 2:
                direction = EnumFacing.NORTH;
                break;
            default:
                direction = EnumFacing.EAST;
                break;
            }
        }
        return direction;
    }

    /**
     * Rotate this Facing around the Y axis counter-clockwise (NORTH => WEST => SOUTH => EAST => NORTH)
     */
    public static EnumFacing rotateCounterClockwise(EnumFacing facing) {
        switch (facing) {
        case NORTH:
            return EnumFacing.WEST;
        case EAST:
            return EnumFacing.NORTH;
        case SOUTH:
            return EnumFacing.EAST;
        case WEST:
            return EnumFacing.SOUTH;
        default:
            throw new IllegalStateException("Unable to get CCW facing of " + facing);
        }
    }

    /**
     * Rotate this Facing around the Y axis counter-clockwise (NORTH => EAST => SOUTH => WEST => NORTH)
     */
    public static EnumFacing rotateClockwise(EnumFacing facing) {
        switch (facing) {
        case NORTH:
            return EnumFacing.EAST;
        case EAST:
            return EnumFacing.SOUTH;
        case SOUTH:
            return EnumFacing.WEST;
        case WEST:
            return EnumFacing.NORTH;
        default:
            throw new IllegalStateException("Unable to get CW facing of " + facing);
        }
    }

    /**
     * Rotate this Facing around all axises counter-clockwise (NORTH => SOUTH => EAST => WEST => UP => DOWN => NORTH)
     */
    public static EnumFacing rotateAdvancedForward(EnumFacing facing) {
        switch (facing) {
        case NORTH:
            return EnumFacing.DOWN;
        case DOWN:
            return EnumFacing.UP;
        case UP:
            return EnumFacing.WEST;
        case WEST:
            return EnumFacing.EAST;
        case EAST:
            return EnumFacing.SOUTH;
        case SOUTH:
            return EnumFacing.NORTH;
        }
        return EnumFacing.NORTH;
    }

    /**
     * Rotate this Facing around all axises counter-clockwise (NORTH => DOWN => UP => WEST => EAST => SOUTH => NORTH)
     * NORTH DOWN UP WEST EAST SOUTH NORTH
     */
    public static EnumFacing rotateAdvancedBackwards(EnumFacing facing) {
        switch (facing) {
        case NORTH:
            return EnumFacing.SOUTH;
        case SOUTH:
            return EnumFacing.EAST;
        case EAST:
            return EnumFacing.WEST;
        case WEST:
            return EnumFacing.UP;
        case UP:
            return EnumFacing.DOWN;
        case DOWN:
            return EnumFacing.NORTH;
        }
        return EnumFacing.NORTH;
    }

    /**
     * Turns Entity rotation in to EnumFacing.
     *
     * @param rotation The entity rotation, Generally MathHelper.floor_double((entity.rotationYaw * 4F) / 360F + 0.5D) & 3;
     * @return The rotation in EnumFacing.
     */
    public static EnumFacing entityRotationToSide(int rotation) {
        switch (rotation) {
        case 0:
            return EnumFacing.EAST;

        case 1:
            return EnumFacing.SOUTH;

        case 2:
            return EnumFacing.WEST;

        default:
            return EnumFacing.NORTH;
        }
    }

}
