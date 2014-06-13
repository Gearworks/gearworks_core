package gearworks.core;

import gearworks.core.common.util.RoflHashMap;
import org.bukkit.plugin.java.JavaPlugin;

public class ClientModule<DataType> extends Module {

    private RoflHashMap<String, DataType> clientData = new RoflHashMap<String, DataType> ();

    public ClientModule(String moduleName, JavaPlugin plugin){
        super(moduleName, plugin);
    }


}
