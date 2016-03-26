package covers1624.lib.client.registry;

import covers1624.lib.api.texture.ITextureRegistry;
import covers1624.lib.api.texture.Icon;
import covers1624.lib.client.model.TextureAtlasSpriteAccessor;
import covers1624.lib.util.LogHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

/**
 * Created by covers1624 on 1/19/2016.
 */
public class TextureRegistry implements ITextureRegistry {

    private TextureMap textureMap;
    /**
     * Defines the current pass for textures. 0 Blocks 1 Items.
     */
    private int pass = 0;

    public TextureRegistry(TextureMap textureMap) {
        this.textureMap = textureMap;
    }

    public void setTexturePass(int pass) {
        this.pass = pass;
    }

    public Icon registerIcon(String location) {
        ResourceLocation resourceLocation = new ResourceLocation(location);
        return registerIcon(resourceLocation.getResourceDomain(), resourceLocation.getResourcePath());
    }

    public Icon registerIcon(String prefix, String location) {
        if (prefix.endsWith(":")) {
            prefix = prefix.substring(0, prefix.lastIndexOf(":"));
        }
        String parentDomain = (pass == 0 ? "blocks/" : (pass == 1 ? "items/" : ""));
        ResourceLocation patchedLocation = new ResourceLocation(prefix, parentDomain + location);
        TextureAtlasSprite sprite = textureMap.getTextureExtry(patchedLocation.toString());
        if (sprite == null) {
            sprite = TextureAtlasSpriteAccessor.createTexture(patchedLocation.toString());
            textureMap.setTextureEntry(patchedLocation.toString(), sprite);
        }
        Icon icon = new Icon(sprite);
        LogHelper.info(((TextureAtlasSprite) icon.getSprite()).getIconName());
        return icon;
    }
}
