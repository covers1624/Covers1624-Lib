package covers1624.lib.util;

import covers1624.lib.Covers1624Lib;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Comparator;
import java.util.TreeMap;

public class ItemUtils {
	public static ItemStackComparator itemStackComparator = new ItemStackComparator();
	private static TreeMap<ItemStack, String> oreMap = new TreeMap<ItemStack, String>(itemStackComparator);

	/**
	 * Drops an item in the world at the given X, Y, Z.
	 * Will only drop the item on the server.
	 *
	 * @param world World to drop the item.
	 * @param x     X Location
	 * @param y     Y Location
	 * @param z     Z Location
	 * @param stack ItemStack to drop.
	 */
	public static void dropItem(World world, int x, int y, int z, ItemStack stack) {
		if (!world.isRemote) {
			double xVelocity = world.rand.nextFloat() * 0.7D + (1.0D - 0.7D) * 0.5D;
			double yVelocity = world.rand.nextFloat() * 0.7D + (1.0D - 0.7D) * 0.5D;
			double zVelocity = world.rand.nextFloat() * 0.7D + (1.0D - 0.7D) * 0.5D;
			EntityItem entityItem = new EntityItem(world, x + xVelocity, y + yVelocity, z + zVelocity, stack);
			entityItem.setPickupDelay(10);
			world.spawnEntityInWorld(entityItem);
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

	private static void registerOre(String name, ItemStack stack) {
		oreMap.put(stack, name);
	}

	public static void readOres() {
		for (String name : OreDictionary.getOreNames()) {
			for (ItemStack stack : OreDictionary.getOres(name)) {
				registerOre(name, stack);
			}
		}
		if (oreMap.isEmpty()) {
			LogHelper.fatal("Something is messing with the OreDictionary and is causing me to be unable to get all registered items..");
		}
	}

	public static String getOreClass(ItemStack stack) {
		String name = oreMap.get(stack);
		if (name != null) {
			return name;
		} else {
			stack = new ItemStack(stack.getItem(), 1, OreDictionary.WILDCARD_VALUE);
			return oreMap.get(stack);
		}
	}

	public static String getOreClassSafe(ItemStack stack) {
		String clazz = getOreClass(stack);
		if (clazz != null) {
			return clazz;
		}
		return "null";
	}

	public static boolean matchItemStackOre(ItemStack stack1, ItemStack stack2) {
		String ore1 = getOreClass(stack1);
		String ore2 = getOreClass(stack2);
		return ore1 != null && ore2 != null && ore1.equals(ore2) || compareItemStack(stack1, stack2) == 0;
	}

	/**
	 * Comparator used for the OreMap.
	 */
	private static class ItemStackComparator implements Comparator<ItemStack> {

		@Override
		public int compare(ItemStack stack1, ItemStack stack2) {
			return compareItemStack(stack1, stack2);
		}
	}

}
