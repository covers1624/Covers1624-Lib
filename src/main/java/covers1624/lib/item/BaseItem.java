package covers1624.lib.item;

import covers1624.lib.api.texture.ITextureRegistry;
import covers1624.lib.api.texture.Icon;
import covers1624.lib.api.texture.provider.IItemTextureProvider;
import net.minecraft.item.Item;

/**
 * Created by covers1624 on 1/20/2016.
 */
public abstract class BaseItem extends Item implements IItemTextureProvider {

	protected Icon itemIcon;

	public BaseItem() {
	}

	@Override
	public Icon getIcon(int meta) {
		return itemIcon;
	}

	@Override
	public void registerIcons(ITextureRegistry textureRegistry) {
		itemIcon = textureRegistry.registerIcon("missingno");
	}
}
