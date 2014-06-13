package gearworks.core.account.mysql;

import gearworks.core.mysql.consumer.Query;
import org.bukkit.entity.Player;

public final class QueryCreateAccount implements Query {

    private final Player player;
    private long timeStamp;

    public QueryCreateAccount (Player player) {
        this.player = player;
        this.timeStamp = (System.currentTimeMillis () / 1000L);
    }

    public String[] getQuery () {
        return new String[]{String.format ("INSERT INTO `sts_players` (player_name, registered, rank, is_online, online_time, last_online, joins, ip) VALUES ('%s', %d, 'guest', 0, 0, 0, 0, '%s')", new Object[]{this.player.getName (), Long.valueOf (this.timeStamp), player.getAddress ().getAddress ().getHostAddress ()}),
                            String.format ("INSERT INTO `sts_donations` (player_name, coins, gems, display_name) VALUES ('%s', 0, 0, '%s')", player.getName (), player.getName ())};
    }
}
