package covers1624.lib.block;

import covers1624.lib.api.texture.Icon;
import covers1624.lib.api.texture.provider.IBlockTextureProvider;
import covers1624.lib.client.registry.TextureRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

/**
 * Created by covers1624 on 1/19/2016.
 */
public abstract class BaseBlock extends Block implements IBlockTextureProvider{

	private Icon icon;

	public BaseBlock(Material material) {
		super(material);
	}

	@Override
	public Icon getIcon(IBlockState blockState, EnumFacing face) {
		return icon;
	}

	@Override
	public Icon getIcon(int meta, EnumFacing face) {
		return icon;
	}

	@Override
	public void registerIcons(TextureRegistry textureRegistry) {
		icon = textureRegistry.registerIcon("missingno");
	}
}
