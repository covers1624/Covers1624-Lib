package covers1624.lib.api.texture.provider;

import net.minecraft.util.ResourceLocation;
//TODO
public interface IItemTextureProvider {
	ResourceLocation getTextureName(int damage);

	int getMaxMeta();

	String getModID();
}
