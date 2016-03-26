package covers1624.lib.api.texture;

import covers1624.lib.util.LogHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by covers1624 on 1/20/2016.
 * Bridge class for TextureAtlasSprite to exist somewhat on the server.
 */
public class Icon {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite sprite;

    public Icon(Object object) {
        try {
            if (object instanceof TextureAtlasSprite) {
                sprite = (TextureAtlasSprite) object;
            }
        } catch (Throwable ignored) {
            LogHelper.bigTrace("Ignoring Icon instance creation, this is the server.");
        }
    }

    /**
     * Gets the internal TextureAtlasSprite.
     * This returns an object to make it server safe, However,
     * It is always safe to cast to TextureAtlasSprite as that is the only object allowed to be stored.
     */
    public Object getSprite() {
        try {
            return sprite;
        } catch (Throwable ignored) {
            LogHelper.bigTrace("Ignoring sprite get, this is the server..");
            return null;
        }
    }

    /**
     * Sets the internal TextureAtlasSprite.
     * Again, It requires an object to make it server safe,
     * Make sure the object is an instance of TextureAtlasSprite before passing it in so there is no log spam.
     */
    public void setSprite(Object object) {
        try {
            if (object instanceof TextureAtlasSprite) {
                sprite = (TextureAtlasSprite) object;
            } else {
                LogHelper.bigFatal("Object is not an instance of TextureAtlasSprite!");
            }
        } catch (Throwable ignored) {
            LogHelper.bigTrace("Ignoring sprite set, this is the server..");
            sprite = null;
        }
    }

    public String getIconName() {
        try {
            return sprite.getIconName();
        } catch (Throwable ignored) {
            LogHelper.bigTrace("Ignoring getIconName call, this is the server");
        }
        return "UNKNOWN";
    }
}
