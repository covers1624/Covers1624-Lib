package covers1624.lib;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import covers1624.lib.handler.ConfigurationHandler;
import covers1624.lib.reference.Reference;
import covers1624.lib.util.ItemUtils;
import covers1624.lib.util.LogHelper;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.versioning.VersionParser;
import cpw.mods.fml.common.versioning.VersionRange;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, useMetadata = false, acceptedMinecraftVersions = "[1.7.10]")
public class Covers1624Lib{

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		event.getModMetadata();
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}

	//TODO push this to 1.8.9 branch.
	private static void parseModMetadata(ModMetadata metadata){
		metadata.modId = Reference.MOD_ID;
		metadata.name = Reference.MOD_NAME;
		metadata.description = "Contains Basic and some advanced Utility classes used by all my mods.";
		metadata.version = Reference.MOD_VERSION;
		metadata.authorList.add("covers1624");
	}
}
