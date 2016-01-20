package covers1624.lib.client.registry.proxy;

import covers1624.lib.api.texture.provider.IBlockTextureProvider;
import covers1624.lib.api.texture.provider.IItemTextureProvider;
import covers1624.lib.api.texture.provider.ITextureProvider;
import covers1624.lib.client.model.ModelGenerator;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidClassic;

/**
 * Created by covers1624 on 1/19/2016.
 */
public class ClientRegistryProxy extends RegistryProxy {

	@Override
	public void register(Object provider) {
		if (provider instanceof ITextureProvider){
			ModelGenerator.textureProviders.add((ITextureProvider) provider);
		}
	}

	@Override
	public void registerBlock(Block block) {
		if (block instanceof IBlockTextureProvider && !ModelGenerator.blocks.contains(block)){
			ModelGenerator.blocks.add(block);
			register(block);
		}
	}

	public void registerItem(Item item){
		if (item instanceof IItemTextureProvider && !ModelGenerator.items.contains(item)){
			ModelGenerator.items.add(item);
		}
	}

	@Override
	public void registerFluid(BlockFluidClassic fluid) {
		if (!ModelGenerator.fluids.contains(fluid)){
			ModelGenerator.fluids.add(fluid);
		}
	}

	@Override
	public void init() {
		if (!hasInit){
			MinecraftForge.EVENT_BUS.register(new ModelGenerator());
		}
		super.init();
	}
}
