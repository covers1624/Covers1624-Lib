package covers1624.lib.util;

import covers1624.lib.Covers1624Lib;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class ItemUtils {

	/**
	 * Drops an item in the world at the given BlockPosition
	 * Will only drop the item on the server.
	 *
	 * @param world    World to drop the item.
	 * @param position Location to drop item.
	 * @param stack    ItemStack to drop.
	 */
	public static void dropItem(World world, BlockPosition position, ItemStack stack) {
		if (!world.isRemote) {
			double xVelocity = world.rand.nextFloat() * 0.7D + (1.0D - 0.7D) * 0.5D;
			double yVelocity = world.rand.nextFloat() * 0.7D + (1.0D - 0.7D) * 0.5D;
			double zVelocity = world.rand.nextFloat() * 0.7D + (1.0D - 0.7D) * 0.5D;
			EntityItem entityItem = new EntityItem(world, position.x + xVelocity, position.y + yVelocity, position.z + zVelocity, stack);
			entityItem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityItem);
		}
	}

	/**
	 * Drops all the items in an IInventory on the ground.
	 * Client safe.
	 *
	 * @param world     World to drop the item.
	 * @param position  Location to drop item.
	 * @param inventory IInventory to drop.
	 */
	public static void dropInventory(World world, BlockPosition position, IInventory inventory) {
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack != null && stack.stackSize > 0) {
				dropItem(world, position, stack);
			}
		}
	}

	/**
	 * Copy's an ItemStack.
	 *
	 * @param stack     Stack to copy.
	 * @param stackSize Size of the new stack.
	 * @return The new stack.
	 */
	public static ItemStack copyStack(ItemStack stack, int stackSize) {
		if (stack.getItem() != null) {
			return new ItemStack(stack.getItem(), stackSize, stack.getItem().getDamage(stack));
		}
		return null;
	}

	public static void ejectItems(World world, BlockPosition pos, ArrayList<ItemStack> stacks, ForgeDirection dir) {
		for (ItemStack stack : stacks) {
			ejectItem(world, pos, stack, dir);
		}
	}

	public static void ejectItem(World world, BlockPosition pos, ItemStack stack, ForgeDirection dir) {
		pos.step(dir);
		EntityItem entity = new EntityItem(world, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, stack);
		entity.motionX = 0.0;
		entity.motionY = 0.0;
		entity.motionZ = 0.0;

		switch (dir) {
		case DOWN:
			entity.motionY = -0.3;
			break;
		case UP:
			entity.motionY = 0.3;
			break;
		case NORTH:
			entity.motionZ = -0.3;
			break;
		case SOUTH:
			entity.motionZ = 0.3;
			break;
		case WEST:
			entity.motionX = -0.3;
			break;
		case EAST:
			entity.motionX = 0.3;
			break;
		default:
		}

		entity.delayBeforeCanPickup = 10;
		world.spawnEntityInWorld(entity);
	}

	/**
	 * Gets the burn time for a given ItemStack.
	 * Will return 0 if there is no burn time on the item.
	 *
	 * @param itemStack Stack to get Burn time on.
	 * @return Burn time for the Stack.
	 */
	public static int getBurnTime(ItemStack itemStack) {
		return TileEntityFurnace.getItemBurnTime(itemStack);
	}

	/**
	 * Compares an ItemStack, only real use is to list items by id and metadata.
	 *
	 * @param stack1 First Stack.
	 * @param stack2 Second Stack.
	 * @return Returns the difference.
	 */
	public static int compareItemStack(ItemStack stack1, ItemStack stack2) {
		int itemStack1ID = Item.getIdFromItem(stack1.getItem());
		int itemStack2ID = Item.getIdFromItem(stack1.getItem());
		return itemStack1ID != itemStack2ID ? itemStack1ID - itemStack2ID : (stack1.getItemDamage() == stack2.getItemDamage() ? 0 : (stack1.getItem().getHasSubtypes() ? stack1.getItemDamage() - stack2.getItemDamage() : 0));
	}
}
