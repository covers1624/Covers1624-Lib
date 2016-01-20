package covers1624.lib.item;

import covers1624.lib.api.texture.provider.IItemTextureProvider;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * Created by covers1624 on 1/20/2016.
 */
public abstract class BaseItem extends Item implements IItemTextureProvider {

	public BaseItem() {
	}

	@Override
	public ResourceLocation getTextureName(int damage) {
		return new ResourceLocation("missingno");
	}

	@Override
	public int getMaxMeta() {
		return 1;
	}

	@Override
	public String getModID() {
		return "covers1624lib";
	}
}
