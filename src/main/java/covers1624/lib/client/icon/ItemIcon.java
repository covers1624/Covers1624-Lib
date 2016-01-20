package covers1624.lib.client.icon;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * Created by covers1624 on 1/19/2016.
 */
public class ItemIcon {

	private Item item;
	private int meta;
	private TextureAtlasSprite sprite;
	private ResourceLocation location;

	public ItemIcon(Item item, int meta, TextureAtlasSprite sprite, ResourceLocation location){
		this.item = item;
		this.meta = meta;
		this.sprite = sprite;
		this.location = location;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getMeta() {
		return meta;
	}

	public void setMeta(int meta) {
		this.meta = meta;
	}

	public TextureAtlasSprite getSprite() {
		return sprite;
	}

	public void setSprite(TextureAtlasSprite sprite) {
		this.sprite = sprite;
	}

	public ResourceLocation getLocation() {
		return location;
	}

	public void setLocation(ResourceLocation location) {
		this.location = location;
	}
}
