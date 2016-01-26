package covers1624.lib.client.registry;

import covers1624.lib.api.texture.ITextureRegistry;
import covers1624.lib.api.texture.Icon;
import covers1624.lib.client.model.TextureAtlasSpriteAccessor;
import covers1624.lib.util.LogHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;

/**
 * Created by covers1624 on 1/19/2016.
 */
public class TextureRegistry implements ITextureRegistry{

	private TextureMap textureMap;

	public TextureRegistry(TextureMap textureMap) {
		this.textureMap = textureMap;
	}

	public Icon registerIcon(String location) {
		TextureAtlasSprite sprite = textureMap.getTextureExtry(location);
		if (sprite == null){
			sprite = TextureAtlasSpriteAccessor.createTexture(location);
			textureMap.setTextureEntry(location, sprite);
		}
		Icon icon = new Icon(sprite);
		LogHelper.info(((TextureAtlasSprite)icon.getSprite()).getIconName());
		return icon;
	}
}
