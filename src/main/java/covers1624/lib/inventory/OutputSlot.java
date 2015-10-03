package covers1624.lib.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Created by covers1624 on 10/3/2015}.
 * Extend this class for all output slots and InventoryUtils will take that slot in to account as non shift click into able.
 */
public abstract class OutputSlot extends Slot {
    public OutputSlot(IInventory inventory, int slotIndex, int xPos, int yPos) {
        super(inventory, slotIndex, xPos, yPos);
    }
}
