package gearworks.core;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Module implements Listener {

    private String moduleName = "default";
    private JavaPlugin plugin;

    public Module (String moduleName, JavaPlugin plugin){
        this.moduleName = moduleName;
        this.plugin = plugin;

        registerEvents (this);
    }



    public void registerEvents (Listener listener){
        this.plugin.getServer ().getPluginManager ().registerEvents (this, this.plugin);
    }
}
