package covers1624.lib.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;

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

	public static boolean setBlockMetadata(World world, int x, int y, int z, int meta) {
		return world.setBlockMetadataWithNotify(x, y, z, meta, 2);
	}

	public static boolean setBlock(World world, int x, int y, int z, Block block, int meta) {
		return world.setBlock(x, y, z, block, meta, 3);
	}

	public static boolean setBlock(World world, BlockPosition blockPos, Block block, int meta){
		return setBlock(world, blockPos.x, blockPos.y, blockPos.y, block, meta);
	}

	public static int rotToSide(int var0) {
		switch (var0) {
		case 0:
			return 5;

		case 1:
			return 3;

		case 2:
			return 4;

		default:
			return 2;
		}
	}

	public static void playNoise(World world, int x, int y, int z, Block block) {
		String effectName = block.stepSound.getStepResourcePath();
		float volume = (block.stepSound.getVolume() + 1.0F) / 2.0F;
		float pitch = block.stepSound.getPitch() * 0.8F;
		world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, effectName, volume, pitch);
	}
}
