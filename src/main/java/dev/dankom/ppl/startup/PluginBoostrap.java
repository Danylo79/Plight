package dev.dankom.ppl.startup;

import dev.dankom.interfaces.Temporary;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.ppl.PluginManager;
import dev.dankom.ppl.loader.PluginClassLoader;
import dev.dankom.ppl.loader.PluginLoader;
import dev.dankom.ppl.plugin.IPluginInstance;
import dev.dankom.ppl.type.PluginService;
import dev.dankom.triggered.trigger.Trigger;
import dev.dankom.triggered.type.TriggerType;

import java.io.File;

public class PluginBoostrap implements Temporary {
    private String[] jarPaths;
    private PluginManager manager;

    public static PluginService service = new PluginService(new DefaultLogger());

    public PluginBoostrap(String... jarPaths) {
        this.jarPaths = jarPaths;
        this.manager = new PluginManager(this);

        new Trigger("PluginBoostrapInit", TriggerType.PRE);

        for (String jarPath : jarPaths) {
            try {
                PluginClassLoader loader = new PluginClassLoader(this, service, new PluginLoader(new File(jarPath)));
                loader.getPlugin().onInit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        open();
    }

    public String[] getJarPaths() {
        return jarPaths;
    }

    /**
     * Called to properly start the plugins!
     */
    @Override
    public void open() {
        for (IPluginInstance plugin : getManager().plugins()) {
            plugin.getParent().onEnable();
        }
    }

    /**
     * Called to properly stop the plugins!
     */
    @Override
    public void close() {
        for (IPluginInstance plugin : getManager().plugins()) {
            plugin.getParent().onDisable();
        }
    }

    public PluginManager getManager() {
        return manager;
    }
}
