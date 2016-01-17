package covers1624.lib;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import covers1624.lib.handler.ConfigurationHandler;
import covers1624.lib.util.ItemUtils;
import covers1624.lib.util.LogHelper;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.versioning.VersionParser;
import cpw.mods.fml.common.versioning.VersionRange;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.7.10")
public class Covers1624Lib extends DummyModContainer implements IFMLLoadingPlugin {

	public static ModMetadata modMetadata = new ModMetadata();

	static {
		modMetadata.modId = "Covers1624Core";
		modMetadata.name = "Covers1624 Core";
		modMetadata.description = "Contains Basic and some advanced Utility classes used by all my mods.";
		modMetadata.version = "1.7.10-1.0";
		modMetadata.authorList.add("covers1624");
	}

	public Covers1624Lib() {
		super(modMetadata);
	}


	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}

	@Override
	public String getModContainerClass() {
		return this.getClass().getName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {

	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

	@Subscribe
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
	}

	@Subscribe
	public void postInit(FMLPostInitializationEvent event) {
	}

	@Override
	public VersionRange acceptableMinecraftVersionRange() {
		return VersionParser.parseRange("[1.7.10]");
	}
}
