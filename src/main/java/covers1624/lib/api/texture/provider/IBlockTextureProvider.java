package covers1624.lib.api.texture.provider;

import covers1624.lib.api.texture.Icon;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

/**
 * Created by covers1624 on 1/19/2016.
 */
public interface IBlockTextureProvider extends ITextureProvider {

	/**
	 * Returns an icon for a given face.
	 *
	 * @param blockState The extended metadata for the block.
	 * @param face       The face to get the Icon for.
	 * @return The icon for the given face.
	 */
	Icon getIcon(IBlockState blockState, EnumFacing face);

	/**
	 * Returns the icon for a given face for inventory rendering.
	 *
	 * @param face The face to get the Icon for.
	 * @return The icon for the given face.
	 */
	Icon getIcon(int meta, EnumFacing face);

}
