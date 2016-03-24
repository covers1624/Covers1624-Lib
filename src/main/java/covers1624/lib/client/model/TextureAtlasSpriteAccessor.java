package covers1624.lib.client.model;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

/**
 * Created by covers1624 on 1/20/2016.
 */
public class TextureAtlasSpriteAccessor extends TextureAtlasSprite {

	protected TextureAtlasSpriteAccessor(String spriteName) {
		super(spriteName);
	}

	public static TextureAtlasSprite createTexture(String spriteName) {
		return makeAtlasSprite(new ResourceLocation(spriteName));
	}
}
