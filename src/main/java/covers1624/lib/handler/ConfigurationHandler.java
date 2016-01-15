package covers1624.lib.handler;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {

	public static Configuration configuration;

	public static boolean logDebug;
	public static boolean logAllNetworkTraffic;
	public static boolean dissableProfiling;

	public static void init(File config) {
		if (configuration == null) {
			configuration = new Configuration(config);
		}
		loadConfiguration();
	}

	public static void loadConfiguration() {
		logDebug = configuration.getBoolean("logDebug", "Logging", false, "Forces all TRACE logs from my mods to log at level INFO. (Makes the log statements visible in the console, still logs to file regardless.)");
		logAllNetworkTraffic = configuration.getBoolean("logAllNetworkTraffic", "Logging", false, "Forces all network traffic from my mods to be logged to the console window.(logged to file regardless.)");
		dissableProfiling = configuration.getBoolean("disableProfiling", "Statistics", false, "When set to true profiling of most of this mods dependants is turned off.");
	}
}
