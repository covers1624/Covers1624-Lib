package covers1624.lib.block;

import covers1624.lib.item.MultiTileItem;
import covers1624.lib.util.LogHelper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by covers1624 on 10/10/2015}.
 * This class allows blocks to easily add new Tile entity's with very little new implementation.
 * It also allows for the tile to open its gui when clicked.
 */
@Deprecated
public class MultiTileBlock extends BlockContainer {

	private Class<? extends TileEntity>[] tileEntityMap = new Class[16];

	protected MultiTileBlock(Material material) {
		super(material);
	}

	/**
	 * Adds a sub Block and adds a TileEntity mapping for a given metadata and registers the TileEntity.
	 * See Below for more information.
	 *
	 * @param meta,      metadata for the sub Block.
	 * @param unlocName, Unlocalized name for the sub block.
	 * @param clazz,     TileEntity Class.
	 */
	public void addSubItemAndTileAndRegister(int meta, String unlocName, Class<? extends TileEntity> clazz) {
		GameRegistry.registerTileEntity(clazz, Loader.instance().activeModContainer().getModId() + ":" + unlocName);
		addSubItemAndTile(meta, unlocName, clazz);
	}

	/**
	 * Adds a sub Block and adds a TileEntity mapping for a given metadata.
	 * See Below for more information.
	 *
	 * @param meta,      metadata for the sub Block.
	 * @param unlocName, Unlocalized name for the sub block.
	 * @param clazz,     TileEntity Class.
	 */
	public void addSubItemAndTile(int meta, String unlocName, Class<? extends TileEntity> clazz) {
		addTileEntityMapping(meta, clazz);
		setItemName(meta, unlocName);
	}

	public void setItemName(int meta, String name) {
		Item item = Item.getItemFromBlock(this);
		((MultiTileItem) item).registerSubItem(meta, "tile." + name);
	}

	/**
	 * Adds a TileEntity mapping for a given metadata.
	 *
	 * @param meta,  metadata for the TileEntity.
	 * @param clazz, TileEntity Class.
	 */
	public void addTileEntityMapping(int meta, Class<? extends TileEntity> clazz) {
		this.tileEntityMap[meta] = clazz;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		try {
			return tileEntityMap[meta].getDeclaredConstructor(new Class[0]).newInstance();
		} catch (Exception e) {
			LogHelper.fatal("Unable to create tile with the MetaData of %s.", meta);
			return null;
		}
	}
}
