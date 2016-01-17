package covers1624.lib.util;

import covers1624.lib.math.Vector3;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockUtils {

	//TODO add a BlockPosition Variant to all methods.

	public static void markBlockDirty(World world, int x, int y, int z) {
		if (world.blockExists(x, y, z)) {
			world.getChunkFromBlockCoords(x, z).setChunkModified();
		}
	}

	public static Block getBlock(World world, int x, int y, int z) {
		return world.getBlock(x, y, z);
	}

	public static void updateIndirectNeighbors(World world, int x, int y, int z, Block block) {
		if (!world.isRemote) {
			for (int i = -3; i <= 3; i++) {
				for (int j = -3; j <= 3; j++) {
					for (int k = -3; k <= 3; k++) {
						int l = i < 0 ? -i : i;
						l += j < 0 ? -j : j;
						l += k < 0 ? -k : k;

						if (l <= 3) {
							notifyBlock(world, x + i, y + j, z + k, block);
						}
					}
				}
			}
		}
	}

	public static void notifyBlock(World world, int x, int y, int z, Block blockToUpdate) {
		Block block = getBlock(world, x, y, z);

		if (block != null) {
			block.onNeighborBlockChange(world, x, y, z, blockToUpdate);
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
		if (pos.orientation != ForgeDirection.UNKNOWN) {
			switch (pos.orientation) {
			case DOWN:
				return  AxisAlignedBB.getBoundingBox(xMiddle - vector3.x, pos.y - vector3.z, zMiddle - vector3.x, xMiddle + vector3.x, pos.y + vector3.y, zMiddle + vector3.x);

			case UP:
				return AxisAlignedBB.getBoundingBox(xMiddle - vector3.x, pos.y + 1 - vector3.y, zMiddle - vector3.x, xMiddle + vector3.x, pos.y + 1 + vector3.z, zMiddle + vector3.x);

			case NORTH:
				return AxisAlignedBB.getBoundingBox(xMiddle - vector3.x, yMiddle - vector3.x, pos.z - vector3.z, xMiddle + vector3.x, yMiddle + vector3.x, pos.z + vector3.y);

			case SOUTH:
				return AxisAlignedBB.getBoundingBox(xMiddle - vector3.x, yMiddle - vector3.x, pos.z + 1 - vector3.y, xMiddle + vector3.x, yMiddle + vector3.x, pos.z + 1 + vector3.z);

			case WEST:
				return AxisAlignedBB.getBoundingBox(pos.x - vector3.z, yMiddle - vector3.x, zMiddle - vector3.x, pos.x + vector3.y, yMiddle + vector3.x, zMiddle + vector3.x);

			case EAST:
				return AxisAlignedBB.getBoundingBox(pos.x + 1 - vector3.y, yMiddle - vector3.x, zMiddle - vector3.x, pos.x + 1 + vector3.z, yMiddle + vector3.x, zMiddle + vector3.x);
			default:
				return null;
			}
		} else {
			throw new RuntimeException("Orientation was not set!");
		}
	}

	public static boolean isEntityInRage(BlockPosition position, Entity entity, int range) {
		return entity.getDistanceSq(position.x + 0.5D, position.y + 0.5D, position.z + 0.5D) <= range;
	}

	public static void fireLightUpdate(World world, BlockPosition position) {
		world.func_147479_m(position.x, position.y, position.z);
	}

	public static ForgeDirection entityRotationToSide(int rotation) {
		switch (rotation) {
		case 0:
			return ForgeDirection.EAST;

		case 1:
			return ForgeDirection.SOUTH;

		case 2:
			return ForgeDirection.WEST;

		default:
			return ForgeDirection.NORTH;
		}
	}

	public static void playNoise(World world, int x, int y, int z, Block block) {
		String effectName = block.stepSound.getStepResourcePath();
		float volume = (block.stepSound.getVolume() + 1.0F) / 2.0F;
		float pitch = block.stepSound.getPitch() * 0.8F;
		world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, effectName, volume, pitch);
	}
}
