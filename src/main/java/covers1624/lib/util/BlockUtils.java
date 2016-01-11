package covers1624.lib.util;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockUtils {

	@Deprecated
	public static void markBlockDirty(World world, int x, int y, int z) {
		//if (world.blockExists(x, y, z)) {
		//	world.getChunkFromBlockCoords(x, z).setChunkModified();
		//}
	}

	@Deprecated
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

	@Deprecated
	public static void notifyBlock(World world, int x, int y, int z, Block blockToUpdate) {
		//Block block = getBlock(world, x, y, z);

		//if (block != null) {
			//block.onNeighborBlockChange(world, x, y, z, blockToUpdate);
		//}
	}

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

	public static void playNoise(World world, int x, int y, int z, Block block) {
		String effectName = block.stepSound.getStepSound();
		float volume = (block.stepSound.volume + 1.0F) / 2.0F;
		float pitch = block.stepSound.frequency * 0.8F;
		world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, effectName, volume, pitch);
	}
}
