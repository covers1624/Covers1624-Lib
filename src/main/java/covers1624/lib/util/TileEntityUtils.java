package covers1624.lib.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

/**
 * Created by covers1624 on 1/15/2016.
 */
public class TileEntityUtils {

	/**
	 * Retrieves an IInventory at the given location, Useful for receiving the entire inventory of a double chest.
	 */
	public static IInventory getInventory(World world, BlockPosition pos) {
		TileEntity tileEntity = pos.getTileEntity(world);
		if (tileEntity instanceof IInventory) {
			IInventory inventory = (IInventory) tileEntity;
			if (inventory instanceof TileEntityChest) {
				IInventory second = null;
				for (BlockPosition position : pos.getAdjacent(false)) {
					TileEntity suspect = position.getTileEntity(world);
					if (suspect instanceof TileEntityChest) {
						second = (IInventory) suspect;
						break;
					}
				}
				if (second != null) {
					return new InventoryLargeChest("large Chest", inventory, second);
				}
			}
			return inventory;
		}
		return null;
	}

}
