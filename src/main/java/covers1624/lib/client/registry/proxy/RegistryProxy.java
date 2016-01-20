package covers1624.lib.client.registry.proxy;

import covers1624.lib.api.texture.provider.ITextureProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.BlockFluidClassic;

/**
 * Created by covers1624 on 1/19/2016.
 */
public class RegistryProxy {

	protected boolean hasInit = false;

	public void register(Object provider){

	}

	public void registerBlock(Block block){

	}

	public void registerItem(Item item){

	}

	public void registerFluid(BlockFluidClassic fluid){

	}

	public void init(){

		hasInit = true;
	}

}
