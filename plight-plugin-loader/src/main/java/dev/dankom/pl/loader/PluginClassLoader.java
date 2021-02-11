package dev.dankom.pl.loader;

import dev.dankom.logger.abztract.AbstractLogger;
import dev.dankom.pl.exception.InvalidPluginException;
import dev.dankom.pl.plugin.IPlugin;
import dev.dankom.pl.plugin.IPluginInstance;
import dev.dankom.pl.plugin.PluginConfiguration;
import dev.dankom.pl.startup.PluginBoostrap;
import dev.dankom.pl.type.PluginService;
import org.apache.commons.lang3.Validate;

import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader {

    private PluginService service;
    private final PluginLoader loader;
    private final IPlugin plugin;
    private PluginBoostrap parent;
    private final PluginConfiguration configuration;

    public PluginClassLoader(PluginBoostrap parent, PluginService service, PluginLoader loader, PluginConfiguration configuration) throws Exception {
        super(new URL[] {loader.getJar().toURI().toURL()}, loader);
        this.parent = parent;
        this.configuration = configuration;
        Validate.notNull(loader, "Loader can not me null!");

        getLog().info("PluginClassLoader", "Loading " + configuration.getName());

        this.service = service;
        this.loader = loader;

        try {
            Class<?> jarClass;
            getLog().info("PluginClassLoader", "Looking for main class . . .");
            try {
                jarClass = Class.forName(configuration.getMainClass(), true, this);
            } catch (ClassNotFoundException ex) {
                throw new InvalidPluginException("Cannot find main class `" + configuration.getMainClass() + "'", ex);
            }
            getLog().info("PluginClassLoader", "Found main class!");

            Class<? extends IPlugin> pluginClass;
            try {
                pluginClass = jarClass.asSubclass(IPlugin.class);
            } catch (ClassCastException ex) {
                throw new InvalidPluginException("Main class `" + configuration.getMainClass() + "' does not extend IPlugin", ex);
            }
            plugin = pluginClass.newInstance();

            getPluginBoostrap().getManager().add(new IPluginInstance(plugin, loader, service, configuration));
        } catch (IllegalAccessException ex) {
            throw new InvalidPluginException("No public constructor", ex);
        } catch (InstantiationException ex) {
            throw new InvalidPluginException("Abnormal plugin type", ex);
        }
    }

    public PluginClassLoader(PluginBoostrap parent, PluginService options, PluginLoader loader) throws Exception {
        this(parent, options, loader, new PluginConfiguration(loader.getJar().getName(), loader));
    }

    public PluginLoader getLoader() {
        return loader;
    }

    public PluginService getService() {
        return service;
    }

    public void setService(PluginService service) {
        this.service = service;
    }

    public AbstractLogger getLog() {
        return service.getLog();
    }

    public IPlugin getPlugin() {
        return plugin;
    }

    public PluginConfiguration getConfiguration() {
        return configuration;
    }

    public PluginBoostrap getPluginBoostrap() {
        return parent;
    }
}
