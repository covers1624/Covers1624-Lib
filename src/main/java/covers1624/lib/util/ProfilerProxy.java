package covers1624.lib.util;

import net.minecraft.profiler.Profiler;

import static covers1624.lib.handler.ConfigurationHandler.dissableProfiling;

/**
 * Created by covers1624 on 1/14/2016.
 * Used as a proxy to disable profiling of Covers lib.
 */
public class ProfilerProxy {

    private Profiler profiler;

    public void pushProfiler(Profiler profiler) {
        if (!dissableProfiling) {
            this.profiler = profiler;
        }
    }

    public void popProfiler() {
        if (!dissableProfiling) {
            profiler = null;
        }
    }

    public void startSection(String name) {
        if (profiler != null) {
            profiler.startSection(name);
        }
    }

    public void endSection() {
        if (profiler != null) {
            profiler.endSection();
        }
    }

    public void startEndSection(String name) {
        if (profiler != null) {
            endSection();
            startSection(name);
        }
    }
}
