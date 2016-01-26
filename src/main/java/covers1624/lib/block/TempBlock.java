package covers1624.lib.block;

import covers1624.lib.api.block.EnumPlacingType;
import covers1624.lib.api.block.PropertyEnumPlacing;
import covers1624.lib.api.block.PropertyString;
import covers1624.lib.api.texture.ITextureRegistry;
import covers1624.lib.api.texture.Icon;
import covers1624.lib.util.LogHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by covers1624 on 1/22/2016.
 */
public class TempBlock extends BaseBlock {

	public static final String[] names = new String[] {"tempBlock"};
	public static final List<String> namesList = new ArrayList<String>();

	public static final PropertyString VARIANTS = new PropertyString("variants", names);
	public static final PropertyDirection FACING = PropertyDirection.create("facingAll");
	public static final PropertyEnumPlacing PLACING_TYPE = PropertyEnumPlacing.create("placingType");
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	private Icon[][] icons = new Icon[16][16];

	private int[] sideMappingInv = { 3, 0, 2, 1, 2, 2, 2 };
	// North, East, South, West.
	private int[][] sideMappingOff = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 3, 0, 1, 2, 2, 2 }, { 3, 0, 2, 1, 2, 2 }, { 3, 0, 2, 2, 1, 2 }, { 3, 0, 2, 2, 2, 1 } };
	// North, East, South, West.
	private int[][] sideMappingOn = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 3, 0, 4, 2, 2, 2 }, { 3, 0, 2, 4, 2, 2 }, { 3, 0, 2, 2, 4, 2 }, { 3, 0, 2, 2, 2, 4 } };


	public TempBlock() {
		super(Material.rock);
		Collections.addAll(namesList, names);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PLACING_TYPE, EnumPlacingType.HORIZONTAL).withProperty(ACTIVE, false));
		setUnlocalizedName("tempBlock");
	}

	@Override
	public void registerIcons(ITextureRegistry textureRegistry) {
		for (int i = 0; i < namesList.size(); i++) {
			icons[i][0] = textureRegistry.registerIcon("covers1624lib:" + "blocks/" + namesList.get(i) + "/top");
			icons[i][1] = textureRegistry.registerIcon("covers1624lib:" + "blocks/" + namesList.get(i) + "/front");
			icons[i][2] = textureRegistry.registerIcon("covers1624lib:" + "blocks/" + namesList.get(i) + "/side");
			icons[i][3] = textureRegistry.registerIcon("covers1624lib:" + "blocks/" + namesList.get(i) + "/bottom");
			icons[i][4] = textureRegistry.registerIcon("covers1624lib:" + "blocks/" + namesList.get(i) + "/active");
		}
	}

	@Override
	public Icon getIcon(int meta, EnumFacing face) {
		//LogHelper.info(meta + " " + face);
		Icon icon = icons[meta][sideMappingInv[face.ordinal()]];
		LogHelper.info(((TextureAtlasSprite)icon.getSprite()).getIconName());
		return icon;
	}

	@Override
	public Icon getIcon(IBlockState state, EnumFacing face) {
		Icon[] stateArray = icons[getMetaFromState(state)];
		boolean active = state.getValue(ACTIVE);
		int facing = state.getValue(FACING).ordinal();
		//LogHelper.info(facing + " " + active);
		if (active) {
			return stateArray[sideMappingOn[facing][face.ordinal()]];
		} else {
			return stateArray[sideMappingOff[facing][face.ordinal()]];
		}
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (int i = 0; i < namesList.size(); i++) {
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, FACING, PLACING_TYPE, ACTIVE, VARIANTS);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getBlockState().getBaseState().withProperty(VARIANTS, namesList.get(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return namesList.indexOf(String.valueOf(state.getValue(VARIANTS)));
	}

	//@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

}
