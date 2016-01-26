package covers1624.lib;

import covers1624.lib.api.texture.provider.ITextureProvider;
import covers1624.lib.client.init.ClientInit;
import covers1624.lib.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by covers1624 on 1/21/2016.
 */
@Mod(modid = "TextureProviderScanner", name = "TextureProviderScanner", dependencies = "after:*")
public class TextureProviderScanner {

	@Mod.EventHandler
	@SideOnly(Side.CLIENT)
	public void init(FMLPreInitializationEvent event) {
		LogHelper.info("Analysing Block Registry for instances of ITextureProvider...");
		int found = 0;
		for (Block block : GameData.getBlockRegistry()) {
			if (block instanceof ITextureProvider) {
				ClientInit.getProxy().register(block);
				found++;
			}
		}
		LogHelper.info("Finished Analysing the Block Registry. Found %s instances.", found);
		LogHelper.info("Analysing Item Registry for instances of ITextureProvider...");
		found = 0;
		for (Item item : GameData.getItemRegistry()) {
			if (item instanceof ITextureProvider) {
				ClientInit.getProxy().register(item);
				found++;
			}
		}
		LogHelper.info("Finished Analysing the Item Registry. Found %s instances.", found);
	}
}
