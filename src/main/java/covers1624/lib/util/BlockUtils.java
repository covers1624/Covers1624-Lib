package covers1624.lib.util;

import covers1624.lib.math.Vector3;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtils {

    @Deprecated
    public static void markBlockDirty(World world, int x, int y, int z) {
        //if (world.blockExists(x, y, z)) {
        //	world.getChunkFromBlockCoords(x, z).setChunkModified();
        //}
    }
    @Deprecated
    public static void updateIndirectNeighbors(World world, BlockPosition position, IBlockState state, Block block) {
        if (!world.isRemote) {
            for (int i = -3; i <= 3; i++) {
                for (int j = -3; j <= 3; j++) {
                    for (int k = -3; k <= 3; k++) {
                        int l = i < 0 ? -i : i;
                        l += j < 0 ? -j : j;
                        l += k < 0 ? -k : k;

                        if (l <= 3) {
                            notifyBlock(world, new BlockPosition(position.x + i, position.y + j, position.z + k), state, block);
                        }
                    }
                }
            }
        }
    }
    @Deprecated
    public static void notifyBlock(World world, BlockPosition position, IBlockState state, Block blockToUpdate) {
        Block block = position.getBlock(world);

        if (block != null) {
            block.onNeighborBlockChange(world, position.toBlockPos(), state, blockToUpdate);
        }
    }

    /**
     * Generates a bounding box.
     *
     * @param pos     MUST HAVE A ORIENTATION SET!
     * @param vector3 Size of the box.
     */
    public static AxisAlignedBB getBox(BlockPosition pos, Vector3 vector3) {
        double xMiddle = pos.x + 0.5D;
        double yMiddle = pos.y + 0.5D;
        double zMiddle = pos.z + 0.5D;
        if (pos.hasOrientation()) {
            switch (pos.getOrientation()) {
            case DOWN:
                return new AxisAlignedBB(xMiddle - vector3.x, pos.y - vector3.z, zMiddle - vector3.x, xMiddle + vector3.x, pos.y + vector3.y, zMiddle + vector3.x);

            case UP:
                return new AxisAlignedBB(xMiddle - vector3.x, pos.y + 1 - vector3.y, zMiddle - vector3.x, xMiddle + vector3.x, pos.y + 1 + vector3.z, zMiddle + vector3.x);

            case NORTH:
                return new AxisAlignedBB(xMiddle - vector3.x, yMiddle - vector3.x, pos.z - vector3.z, xMiddle + vector3.x, yMiddle + vector3.x, pos.z + vector3.y);

            case SOUTH:
                return new AxisAlignedBB(xMiddle - vector3.x, yMiddle - vector3.x, pos.z + 1 - vector3.y, xMiddle + vector3.x, yMiddle + vector3.x, pos.z + 1 + vector3.z);

            case WEST:
                return new AxisAlignedBB(pos.x - vector3.z, yMiddle - vector3.x, zMiddle - vector3.x, pos.x + vector3.y, yMiddle + vector3.x, zMiddle + vector3.x);

            case EAST:
                return new AxisAlignedBB(pos.x + 1 - vector3.y, yMiddle - vector3.x, zMiddle - vector3.x, pos.x + 1 + vector3.z, yMiddle + vector3.x, zMiddle + vector3.x);
            default:
                return null;
            }
        } else {
            throw new RuntimeException("Orientation was not set!");
        }
    }

    @Deprecated
    public static boolean isEntityInRage(BlockPosition position, Entity entity, int range) {
        return isEntityInRage(position.toBlockPos(), entity, range);
    }

    public static boolean isEntityInRage(BlockPos position, Entity entity, int range) {
        return entity.getDistanceSq(position.getX() + 0.5D, position.getY() + 0.5D, position.getZ() + 0.5D) <= range;
    }

    @Deprecated
    public static void fireLightUpdate(World world, BlockPosition position) {
        fireLightUpdate(world, position.toBlockPos());
    }

    public static void fireLightUpdate(World world, BlockPos position) {
        world.notifyLightSet(position);
    }
}
