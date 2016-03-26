package covers1624.lib.block;

import covers1624.lib.api.block.property.PropertyString;
import covers1624.lib.api.texture.ITextureRegistry;
import covers1624.lib.client.RenderDispatcher;
import covers1624.lib.client.registry.RenderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by covers1624 on 1/22/2016.
 */
public class TempBlock extends Block {

    public static final String[] names = new String[] { "tempblock" };
    public static final List<String> namesList = new ArrayList<String>();

    public static final PropertyString VARIANTS = new PropertyString("variants", names);

    public static EnumBlockRenderType type = RenderDispatcher.createNewRenderType("temp");

    //private Icon[][] icons = new Icon[16][16];

    //private int[] sideMappingInv = { 3, 0, 2, 1, 2, 2, 2 };
    // North, East, South, West.
    //private int[][] sideMappingOff = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 3, 0, 1, 2, 2, 2 }, { 3, 0, 2, 1, 2, 2 }, { 3, 0, 2, 2, 1, 2 }, { 3, 0, 2, 2, 2, 1 } };
    // North, East, South, West.
    //private int[][] sideMappingOn = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 3, 0, 4, 2, 2, 2 }, { 3, 0, 2, 4, 2, 2 }, { 3, 0, 2, 2, 4, 2 }, { 3, 0, 2, 2, 2, 4 } };

    public TempBlock() {
        super(Material.rock);
        Collections.addAll(namesList, names);
        getDefaultState().withProperty(VARIANTS, "tempblock");
        setUnlocalizedName("tempBlock");
    }

    //@Override
    public void registerIcons(ITextureRegistry textureRegistry) {
        //for (int i = 0; i < namesList.size(); i++) {
        //    icons[i][0] = textureRegistry.registerIcon("covers1624lib", namesList.get(i) + "/top");
        //    icons[i][1] = textureRegistry.registerIcon("covers1624lib", namesList.get(i) + "/front");
        //    icons[i][2] = textureRegistry.registerIcon("covers1624lib", namesList.get(i) + "/side");
        //    icons[i][3] = textureRegistry.registerIcon("covers1624lib", namesList.get(i) + "/bottom");
        //    icons[i][4] = textureRegistry.registerIcon("covers1624lib", namesList.get(i) + "/active");
        //}
    }

    /*@Override
    public Icon getIcon(int meta, EnumFacing face) {
        //LogHelper.info(meta + " " + face);
        Icon icon = icons[meta][sideMappingInv[face.ordinal()]];
        LogHelper.info(((TextureAtlasSprite) icon.getSprite()).getIconName());
        return icon;
    }*/

    // @Override
    //public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    // LogHelper.info("Block Placed!");
    //IExtendedBlockState extendedState = (IExtendedBlockState) worldIn.getBlockState(pos);
    //EnumPlacingType placingType = extendedState.getValue(PLACING_TYPE_UNLISTED);
    //if (placingType == EnumPlacingType.HORIZONTAL) {
    //    facing = RotationUtils.getPlacedRotationHorizontal(worldIn, new BlockPosition(pos), placer);
    //} else if (placingType == EnumPlacingType.ALL) {
    //    facing = RotationUtils.getPlacedRotationAdvanced(worldIn, new BlockPosition(pos), placer);
    //}
    //extendedState.withProperty(FACING_UNLISTED, facing);
    //worldIn.setBlockState(pos, extendedState);
    //LogHelper.info("After Facing: %s", extendedState.getValue(FACING_UNLISTED));
    //return extendedState;
    //}

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

    }

    //@Override
    /*public Icon getIcon(IBlockState state, EnumFacing face) {
        LogHelper.info("ICON: " + face.ordinal());
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
    }*/

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < namesList.size(); i++) {
            list.add(new ItemStack(itemIn, 1, i));
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANTS);
        //return new ExtendedBlockState(this, new IProperty[] { VARIANTS }, new IUnlistedProperty[] { FACING_UNLISTED, PLACING_TYPE_UNLISTED, ACTIVE });
    }

    /*@Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
        return extendedBlockState.withProperty(FACING_UNLISTED, EnumFacing.NORTH).withProperty(PLACING_TYPE_UNLISTED, EnumPlacingType.HORIZONTAL).withProperty(ACTIVE, false).withProperty(VARIANTS, "tempBlock");
    }*/

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getBlockState().getBaseState().withProperty(VARIANTS, namesList.get(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return namesList.indexOf(state.getValue(VARIANTS));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return type;
    }
}
