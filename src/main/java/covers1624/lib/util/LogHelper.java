package covers1624.lib.util;

import covers1624.lib.handler.ConfigurationHandler;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

/**
 * Created by covers1624 on 10/3/2015}.
 * LogManager for all Covers1624's Mods.
 */
public class LogHelper {

    private String name;

    public LogHelper(String name) {
        this.name = name;
    }

    public void log(Level logLevel, Object object) {
        FMLLog.log(name, logLevel, String.valueOf(object));
    }

    public void all(Object object) {
        log(Level.ALL, object);
    }

    public void debug(Object object) {
        log(Level.DEBUG, object);
    }

    public void error(Object object) {
        log(Level.ERROR, object);
    }

    public void fatal(Object object) {
        log(Level.FATAL, object);
    }

    public void info(Object object) {
        log(Level.INFO, object);
    }

    public void off(Object object) {
        log(Level.OFF, object);
    }

    public void trace(Object object) {
        if (ConfigurationHandler.logDebug) {
            info(object);
        } else {
            log(Level.TRACE, object);
        }

    }

    public void warn(Object object) {
        log(Level.WARN, object);
    }

    /**
     * Formatable
     */
    public void all(String object, Object... format) {
        log(Level.ALL, String.format(object, format));
    }

    public void debug(String object, Object... format) {
        log(Level.DEBUG, String.format(object, format));
    }

    public void error(String object, Object... format) {
        log(Level.ERROR, String.format(object, format));
    }

    public void fatal(String object, Object... format) {
        log(Level.FATAL, String.format(object, format));
    }

    public void info(String object, Object... format) {
        log(Level.INFO, String.format(object, format));
    }

    public void off(String object, Object... format) {
        log(Level.OFF, String.format(object, format));
    }

    public void trace(String object, Object... format) {
        if (ConfigurationHandler.logDebug) {
            log(Level.INFO, String.format(object, format));
        } else {
            log(Level.TRACE, String.format(object, format));
        }

    }

    public void warn(String object, Object... format) {
        log(Level.WARN, String.format(object, format));
    }

    public void bigFatal(String format, Object... data) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        fatal("****************************************");
        fatal("* " + format, data);
        for (int i = 2; i < 8 && i < trace.length; i++) {
            fatal("*  at %s%s", trace[i].toString(), i == 7 ? "..." : "");
        }
        fatal("****************************************");
    }

}
