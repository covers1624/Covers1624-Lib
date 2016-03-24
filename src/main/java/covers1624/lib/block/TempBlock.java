package covers1624.lib.block;

import covers1624.lib.api.block.EnumPlacingType;
import covers1624.lib.api.block.property.PropertyBoolUnlisted;
import covers1624.lib.api.block.property.PropertyEnumPlacing;
import covers1624.lib.api.block.property.PropertyString;
import covers1624.lib.api.texture.ITextureRegistry;
import covers1624.lib.api.texture.Icon;
import covers1624.lib.util.BlockPosition;
import covers1624.lib.util.LogHelper;
import covers1624.lib.util.RotationUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by covers1624 on 1/22/2016.
 */
public class TempBlock extends BaseBlock {

	public static final String[] names = new String[] { "tempBlock" };
	public static final List<String> namesList = new ArrayList<String>();

	public static final PropertyString VARIANTS = new PropertyString("variants", names);
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final IUnlistedProperty<EnumFacing> FACING_UNLISTED = Properties.toUnlisted(FACING);
	public static final PropertyEnumPlacing PLACING_TYPE = PropertyEnumPlacing.create("placingType");
	public static final IUnlistedProperty<EnumPlacingType> PLACING_TYPE_UNLISTED = Properties.toUnlisted(PLACING_TYPE);
	public static final PropertyBoolUnlisted ACTIVE = new PropertyBoolUnlisted("active");
	//public static final IUnlistedProperty<Boolean> ACTIVE_UNLISTED = Properties.toUnlisted(ACTIVE);

	private Icon[][] icons = new Icon[16][16];

	private int[] sideMappingInv = { 3, 0, 2, 1, 2, 2, 2 };
	// North, East, South, West.
	private int[][] sideMappingOff = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 3, 0, 1, 2, 2, 2 }, { 3, 0, 2, 1, 2, 2 }, { 3, 0, 2, 2, 1, 2 }, { 3, 0, 2, 2, 2, 1 } };
	// North, East, South, West.
	private int[][] sideMappingOn = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 3, 0, 4, 2, 2, 2 }, { 3, 0, 2, 4, 2, 2 }, { 3, 0, 2, 2, 4, 2 }, { 3, 0, 2, 2, 2, 4 } };

	public TempBlock() {
		super(Material.rock);
		Collections.addAll(namesList, names);
		IBlockState defaultState = getDefaultState();
		defaultState.withProperty(VARIANTS, "tempBlock");
		setDefaultState(((IExtendedBlockState) defaultState).withProperty(FACING_UNLISTED, EnumFacing.NORTH).withProperty(PLACING_TYPE_UNLISTED, EnumPlacingType.HORIZONTAL).withProperty(ACTIVE, false));
		setUnlocalizedName("tempBlock");
	}

	@Override
	public void registerIcons(ITextureRegistry textureRegistry) {
		for (int i = 0; i < namesList.size(); i++) {
			icons[i][0] = textureRegistry.registerIcon("covers1624lib", namesList.get(i) + "/top");
			icons[i][1] = textureRegistry.registerIcon("covers1624lib", namesList.get(i) + "/front");
			icons[i][2] = textureRegistry.registerIcon("covers1624lib", namesList.get(i) + "/side");
			icons[i][3] = textureRegistry.registerIcon("covers1624lib", namesList.get(i) + "/bottom");
			icons[i][4] = textureRegistry.registerIcon("covers1624lib", namesList.get(i) + "/active");
		}
	}

	@Override
	public Icon getIcon(int meta, EnumFacing face) {
		//LogHelper.info(meta + " " + face);
		Icon icon = icons[meta][sideMappingInv[face.ordinal()]];
		LogHelper.info(((TextureAtlasSprite) icon.getSprite()).getIconName());
		return icon;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		LogHelper.info("Block Placed!");
		IExtendedBlockState extendedState = (IExtendedBlockState) worldIn.getBlockState(pos);
		EnumPlacingType placingType = extendedState.getValue(PLACING_TYPE_UNLISTED);
		if (placingType == EnumPlacingType.HORIZONTAL) {
			facing = RotationUtils.getPlacedRotationHorizontal(worldIn, new BlockPosition(pos), placer);
		} else if (placingType == EnumPlacingType.ALL) {
			facing = RotationUtils.getPlacedRotationAdvanced(worldIn, new BlockPosition(pos), placer);
		}
		extendedState.withProperty(FACING_UNLISTED, facing);
		worldIn.setBlockState(pos, extendedState);
		LogHelper.info("After Facing: %s", extendedState.getValue(FACING_UNLISTED));
		return extendedState;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

	}

	@Override
	public Icon getIcon(IBlockState state, EnumFacing face) {
		//LogHelper.info("ICON: " + face.ordinal());
		IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
		Icon[] stateArray = icons[getMetaFromState(extendedBlockState)];
		//LogHelper.info(extendedBlockState.getValue(ACTIVE) == null);
		boolean active = false;
		if (extendedBlockState.getValue(ACTIVE) != null) {
			active = extendedBlockState.getValue(ACTIVE);
		}
		EnumFacing facing = EnumFacing.WEST;
		if (extendedBlockState.getValue(FACING_UNLISTED) != null) {
			facing = extendedBlockState.getValue(FACING_UNLISTED);
		}
		//LogHelper.info(facing + " " + active);
		if (active) {
			return stateArray[sideMappingOn[facing.getIndex()][face.ordinal()]];
		} else {
			return stateArray[sideMappingOff[facing.getIndex()][face.ordinal()]];
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
		//return new BlockState(this, FACING, PLACING_TYPE, ACTIVE, VARIANTS);
		return new ExtendedBlockState(this, new IProperty[] { VARIANTS }, new IUnlistedProperty[] { FACING_UNLISTED, PLACING_TYPE_UNLISTED, ACTIVE });
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
		return extendedBlockState.withProperty(FACING_UNLISTED, EnumFacing.NORTH).withProperty(PLACING_TYPE_UNLISTED, EnumPlacingType.HORIZONTAL).withProperty(ACTIVE, false).withProperty(VARIANTS, "tempBlock");
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getBlockState().getBaseState().withProperty(VARIANTS, namesList.get(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return namesList.indexOf(state.getValue(VARIANTS));
	}

	//@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

}
