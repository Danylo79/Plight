package dev.dankom.pl;

import dev.dankom.pl.plugin.IPluginInstance;
import dev.dankom.pl.startup.PluginBoostrap;

import java.util.ArrayList;
import java.util.List;

public class PluginManager {
    private List<IPluginInstance> plugins;
    private PluginBoostrap parent;

    public PluginManager(PluginBoostrap parent) {
        this.parent = parent;
        this.plugins = new ArrayList<>();
    }

    public void add(IPluginInstance plugin) {
        if (!plugins().contains(plugin)) {
            plugins().add(plugin);
            plugin.getService().getLog().info("PluginManager$" + plugin.getConfiguration().getName(), "Found plugin " + plugin.getConfiguration().getName() + "!");
        } else {
            plugin.getService().getLog().error("PluginManager$" + plugin.getConfiguration().getName(), "Attempted to add " + plugin.getConfiguration().getName() + " multiple times! (Skipped)");
        }
    }

    public void remove(IPluginInstance plugin) {
        plugins().remove(plugin);
    }

    public List<IPluginInstance> plugins() {
        return plugins;
    }

    public PluginBoostrap getParent() {
        return parent;
    }
}
