package gearworks.core.account;

import gearworks.core.common.Rank;
import gearworks.core.common.util.RoflHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public class CoreClientManager implements Listener {

    private static CoreClientManager instance;
    private RoflHashMap<String, CoreClient> clientList;
    private HashSet<String> dontRemoveList;

    public CoreClientManager (JavaPlugin plugin){
        instance = this;

        clientList = new RoflHashMap<String, CoreClient> ();
        dontRemoveList = new HashSet<String> ();

        plugin.getServer ().getPluginManager ().registerEvents (this, plugin);


    }

    public static CoreClientManager initialize (JavaPlugin plugin){
        if (instance == null){
            instance = new CoreClientManager (plugin);
        }

        return instance;
    }

    public CoreClient addClient (Player player){
        CoreClient coreClient = new CoreClient (player);
        if (coreClient != null && !clientList.containsKey (player.getName ())){
            clientList.put (player.getName (), coreClient);
            return coreClient;
        }

        return null;
    }

    public CoreClient getClient (Player player){
        if (clientList.containsKey (player.getName ())){
            return clientList.get (player.getName ());
        }

        return null;
    }

    public CoreClient removeClient (Player player){
        if (clientList.containsKey (player.getName ())){
            CoreClient coreClient = clientList.get (player.getName ());
            coreClient.save ();
            clientList.remove (player.getName ());
            return coreClient;
        }

        return null;
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void login (PlayerLoginEvent event){

        final CoreClient client = addClient (event.getPlayer ());

        if (Bukkit.getOnlinePlayers ().length >= Bukkit.getServer ().getMaxPlayers ()){
            if (Rank.has (client.getRank (), Rank.DEITY)){
                event.allow ();
                event.setResult (PlayerLoginEvent.Result.ALLOWED);
                return;
            }

            event.disallow (PlayerLoginEvent.Result.KICK_OTHER, "Server is full - purchase the Deity rank at http://www.oursite.com/");
        }
    }

    @EventHandler
    public void kick (PlayerKickEvent event){
        if (event.getReason ().equalsIgnoreCase ("You logged in from another location")){
            dontRemoveList.add (event.getPlayer ().getName ());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void quit (PlayerQuitEvent event){
        if (!this.dontRemoveList.contains (event.getPlayer ().getName ())){
            removeClient (event.getPlayer ());
        }

        dontRemoveList.remove (event.getPlayer ().getName ());
    }
}

