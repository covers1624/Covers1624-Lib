package covers1624.lib.client.init;

import covers1624.lib.client.model.ModelGenerator;
import covers1624.lib.client.registry.proxy.RegistryProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by covers1624 on 1/20/2016.
 */
public class ClientInit {

	@SidedProxy(clientSide = "covers1624.lib.client.registry.proxy.ClientRegistryProxy", serverSide = "covers1624.lib.client.registry.proxy.RegistryProxy")
	public static RegistryProxy proxy;

	public static RegistryProxy getProxy() {
		return proxy;
	}

	//TODO move to internal Proxy
	public static void register() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new ModelGenerator());
		}
	}

}
