package dev.dankom.ppl.plugin;

public abstract class IPlugin {

    public void onInit() {}

    public abstract void onEnable();
    public abstract void onDisable();
}
