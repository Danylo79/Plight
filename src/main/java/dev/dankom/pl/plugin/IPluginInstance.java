package dev.dankom.pl.plugin;

import dev.dankom.pl.loader.PluginLoader;
import dev.dankom.pl.type.PluginService;

public class IPluginInstance {
    private final IPlugin parent;
    private final PluginLoader loader;
    private final PluginService service;
    private final PluginConfiguration configuration;

    public IPluginInstance(IPlugin parent, PluginLoader loader, PluginService service, PluginConfiguration configuration) {
        this.parent = parent;
        this.loader = loader;
        this.service = service;
        this.configuration = configuration;
    }

    public IPlugin getParent() {
        return parent;
    }

    public PluginLoader getLoader() {
        return loader;
    }

    public PluginService getService() {
        return service;
    }

    public PluginConfiguration getConfiguration() {
        return configuration;
    }
}
