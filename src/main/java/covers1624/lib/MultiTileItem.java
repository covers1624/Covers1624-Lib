package covers1624.lib;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by covers1624 on 10/10/2015}.
 * This class belongs with MultiTileBlock.
 */
public class MultiTileItem extends ItemBlock{
	private ArrayList<Integer> valid = new ArrayList<Integer>();
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

	public void registerSubItem(int meta, String unlocName){
		names.put(meta, unlocName);
		valid.add(meta);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return names.get(stack.getItemDamage());
	}

	@SuppressWarnings({"unchecked"})
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List items) {
		for (Integer integer : valid){
			items.add(new ItemStack(this, 1, integer));
		}
	}
}
