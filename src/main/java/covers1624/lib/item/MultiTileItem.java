package covers1624.lib.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by covers1624 on 10/10/2015}.
 * This class belongs with MultiTileBlock.
 */

public class MultiTileItem extends ItemBlock {
	private HashMap<Integer, String> names = new HashMap<Integer, String>();

	public MultiTileItem(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	public void registerSubItem(int meta, String unlocName) {
		names.put(meta, unlocName);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return names.get(stack.getItemDamage());
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List items) {
		for (Map.Entry<Integer, String> entry : names.entrySet()) {
			items.add(new ItemStack(this, 1, entry.getKey()));
		}
	}
}
