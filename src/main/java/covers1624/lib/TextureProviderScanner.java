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

import static covers1624.lib.reference.Reference.MOD_ID_SCANNER;
import static covers1624.lib.reference.Reference.MOD_NAME_SCANNER;

/**
 * Created by covers1624 on 1/21/2016.
 */
@Mod(modid = MOD_ID_SCANNER, name = MOD_NAME_SCANNER, dependencies = "after:*", clientSideOnly = true)
public class TextureProviderScanner {

    @Mod.EventHandler
    @SideOnly(Side.CLIENT)
    public void init(FMLPreInitializationEvent event) {
        LogHelper.info("Analysing Block Registry for instances of IBlockTextureProvider...");
        int found = 0;
        for (Block block : GameData.getBlockRegistry()) {
            if (block instanceof ITextureProvider) {
                ClientInit.getProxy().register(block);
                found++;
            }
        }
        LogHelper.info("Finished Analysing the Block Registry. Found %s instances.", found);
        LogHelper.info("Analysing Item Registry for instances of IItemTextureProvider...");
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
