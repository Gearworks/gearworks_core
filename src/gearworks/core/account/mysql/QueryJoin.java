package gearworks.core.account.mysql;

import gearworks.core.mysql.consumer.Query;
import org.bukkit.entity.Player;

public class QueryJoin implements Query {

    private Player player;
    private long timeStamp;

    public QueryJoin (final Player player){
        this.player = player;
    }

    public String [] getQuery (){
        return new String[]{ String.format ("UPDATE `sts_players` SET is_online = 1, joins = joins + 1, last_online = %d, ip = '%s' WHERE player_name = '%s'", new Object[]{Long.valueOf (this.timeStamp), this.player.getAddress ().getAddress ().getHostAddress (), this.player.getName ()}) };
    }
}
