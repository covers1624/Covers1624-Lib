package covers1624.lib.api.texture.provider;

import covers1624.lib.api.texture.Icon;

public interface IItemTextureProvider extends ITextureProvider {

	/**
	 * Gets the item's icon from metadata.
	 *
	 * @param meta The items metadata.
	 * @return The icon to use.
	 */
	Icon getIcon(int meta);
}
