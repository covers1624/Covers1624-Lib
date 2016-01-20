package covers1624.lib;

import covers1624.lib.client.init.ClientInit;
import covers1624.lib.handler.ConfigurationHandler;
import covers1624.lib.proxy.CommonProxy;
import covers1624.lib.reference.Reference;
import covers1624.lib.util.LogHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions = "[1.8.9]")
public class Covers1624Lib {

	public static ModMetadata modMetadata = new ModMetadata();
	@SidedProxy(clientSide = "covers1624.lib.proxy.ClientProxy", serverSide = "covers1624.lib.proxy.CommonProxy")
	public static CommonProxy proxy;

	static {
		modMetadata.modId = Reference.MOD_ID;
		modMetadata.name = Reference.MOD_NAME;
		modMetadata.description = "Contains Basic and some advanced Utility classes used by all my mods.";
		modMetadata.version = Reference.MOD_VERSION;
		modMetadata.authorList.add("covers1624");
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LogHelper.info("Init");
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		ClientInit.getProxy().init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
}
