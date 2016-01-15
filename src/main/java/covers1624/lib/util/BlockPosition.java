package covers1624.lib.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

public class BlockPosition {
	public int x;
	public int y;
	public int z;
	private boolean hasOrientation;
	private EnumFacing orientation;

	public static final int[][] SIDE_COORD_MOD = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };
	/*
		Constructors
	 */

	public BlockPosition(int x, int y, int z, EnumFacing orientation) {
		this.x = x;
		this.y = y;
		this.z = z;
		if (orientation != null) {
			this.orientation = orientation;
		}
		this.hasOrientation = orientation != null;
	}

	public BlockPosition(int x, int y, int z) {
		this(x, y, z, null);
	}

	public BlockPosition(Vec3i vec3i) {
		this(vec3i.getX(), vec3i.getY(), vec3i.getZ());
	}

	public BlockPosition(BlockPosition p) {
		x = p.x;
		y = p.y;
		z = p.z;
		orientation = p.orientation;
		this.hasOrientation = p.hasOrientation;
	}

	public BlockPosition(NBTTagCompound nbttagcompound) {
		x = nbttagcompound.getInteger("BlockPosition:X");
		y = nbttagcompound.getInteger("BlockPosition:Y");
		z = nbttagcompound.getInteger("BlockPosition:Z");
		if (nbttagcompound.getBoolean("BlockPosition:HasOrientation")) {
			orientation = EnumFacing.VALUES[nbttagcompound.getInteger("BlockPosition:Orientation")];
			hasOrientation = true;
		} else {
			hasOrientation = false;
		}
	}

	public BlockPosition(TileEntity tile) {
		this(tile.getPos());
	}
	/*
		Getters.
	 */

	public BlockPosition copy() {
		return new BlockPosition(x, y, z, orientation);
	}

	public Vec3i convertToVec3i() {
		return new Vec3i(x, y, z);
	}

	public BlockPos toBlockPos() {
		return new BlockPos(x, y, z);
	}

	public boolean hasOrientation() {
		return hasOrientation;
	}

	public EnumFacing getOrientation() {
		return orientation;
	}

	public void setOrientation(EnumFacing orientation) {
		this.orientation = orientation;
		this.hasOrientation = true;
	}

	/*
		Orientation Manipulation.
	 */

	public BlockPosition step(EnumFacing dir) {
		Vec3i directionVec = dir.getDirectionVec();
		x += directionVec.getX();
		y += directionVec.getY();
		z += directionVec.getZ();
		return this;
	}

	public BlockPosition step(EnumFacing dir, int dist) {
		Vec3i directionVec = dir.getDirectionVec();
		x += directionVec.getX() * dist;
		y += directionVec.getY() * dist;
		z += directionVec.getZ() * dist;
		return this;
	}

	@Deprecated
	public BlockPosition step(int dir, int dist) {
		return step(EnumFacing.VALUES[dir], dist);
	}

	public void moveRight(int step) {
		if (!hasOrientation) {
			LogHelper.bigError("BlockPosition does not have an orientation! Unable to step..");
			return;
		}
		switch (orientation) {
		case SOUTH:
			x = x - step;
			break;
		case NORTH:
			x = x + step;
			break;
		case EAST:
			z = z + step;
			break;
		case WEST:
			z = z - step;
			break;
		default:
			break;
		}
	}

	public void moveLeft(int step) {
		moveRight(-step);
	}

	public void moveForwards(int step) {
		if (!hasOrientation) {
			LogHelper.bigError("BlockPosition does not have an orientation! Unable to step..");
			return;
		}
		switch (orientation) {
		case UP:
			y = y + step;
			break;
		case DOWN:
			y = y - step;
			break;
		case SOUTH:
			z = z + step;
			break;
		case NORTH:
			z = z - step;
			break;
		case EAST:
			x = x + step;
			break;
		case WEST:
			x = x - step;
			break;
		default:
		}
	}

	public void moveBackwards(int step) {
		moveForwards(-step);
	}

	public void moveUp(int step) {
		if (!hasOrientation) {
			LogHelper.bigError("BlockPosition does not have an orientation! Unable to step..");
			return;
		}
		switch (orientation) {
		case EAST:
		case WEST:
		case NORTH:
		case SOUTH:
			y = y + step;
			break;
		default:
			break;
		}

	}

	public void moveDown(int step) {
		moveUp(-step);
	}

	/*
		Adding.
	 */

	public BlockPosition add(BlockPosition other) {
		BlockPosition result = new BlockPosition(x, y, z);
		result.x += other.x;
		result.y += other.y;
		result.z += other.z;
		result.hasOrientation = other.hasOrientation;
		result.orientation = other.orientation;
		return result;
	}

	public BlockPosition add(Vec3i other) {
		return add(new BlockPosition(other));
	}

	/*
		Saving and loading.
	 */

	public void writeToNBT(NBTTagCompound tagCompound) {
		tagCompound.setDouble("BlockPosition:X", x);
		tagCompound.setDouble("BlockPosition:Y", y);
		tagCompound.setDouble("BlockPosition:Z", z);
		tagCompound.setBoolean("BlockPosition:HasOrientation", hasOrientation);
		if (hasOrientation) {
			tagCompound.setInteger("BlockPosition:Orientation", orientation.ordinal());
		}
	}

	@Override
	public String toString() {
		if (hasOrientation) {
			return "{" + x + ", " + y + ", " + z + ";" + orientation.toString() + "}";
		}
		return "{" + x + ", " + y + ", " + z + "}";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof BlockPosition && equals((BlockPosition) obj);
	}

	private boolean equals(BlockPosition other) {
		return other.x == x && other.y == y && other.z == z && other.orientation == orientation && other.hasOrientation == hasOrientation;
	}

	@Override
	public int hashCode() {
		return (x & 0xfff) | (y & 0xff << 8) | (z & 0xfff << 12);
	}

	public BlockPosition min(BlockPosition p) {
		return new BlockPosition(p.x > x ? x : p.x, p.y > y ? y : p.y, p.z > z ? z : p.z);
	}

	public BlockPosition max(BlockPosition p) {
		return new BlockPosition(p.x < x ? x : p.x, p.y < y ? y : p.y, p.z < z ? z : p.z);
	}

	public List<BlockPosition> getAdjacent() {
		return getAdjacent(false);
	}

	public List<BlockPosition> getAdjacent(boolean includeVertical) {
		List<BlockPosition> a = new ArrayList<BlockPosition>();
		a.add(new BlockPosition(x + 1, y, z, EnumFacing.EAST));
		a.add(new BlockPosition(x - 1, y, z, EnumFacing.WEST));
		a.add(new BlockPosition(x, y, z + 1, EnumFacing.SOUTH));
		a.add(new BlockPosition(x, y, z - 1, EnumFacing.NORTH));
		if (includeVertical) {
			a.add(new BlockPosition(x, y + 1, z, EnumFacing.UP));
			a.add(new BlockPosition(x, y - 1, z, EnumFacing.DOWN));
		}
		return a;
	}

	public TileEntity getTileEntity(IBlockAccess world) {
		return world.getTileEntity(toBlockPos());
	}

	public IBlockState getBlockState(IBlockAccess world) {
		return world.getBlockState(toBlockPos());
	}

	public Block getBlock(IBlockAccess world) {

		return getBlockState(world).getBlock();
	}

	public ItemStack getWorldItemStack(IBlockAccess world) {
		Block block = getBlock(world);
		return new ItemStack(block, 1, block.getMetaFromState(getBlockState(world)));
	}

	public boolean blockExists(IBlockAccess world) {
		return world.isAirBlock(toBlockPos());
	}

	public boolean setBlock(World world, Block block) {
		return setBlockState(world, block.getActualState(getBlockState(world), world, toBlockPos()));
	}

	public boolean setBlockState(World world, IBlockState blockState) {
		return world.setBlockState(toBlockPos(), blockState);
	}

	public void markDirty(World world) {
		Chunk chunk = world.getChunkFromBlockCoords(toBlockPos());
		chunk.setChunkModified();
	}
}