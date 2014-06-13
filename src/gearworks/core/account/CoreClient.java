package gearworks.core.account;

import gearworks.core.account.mysql.AccountRepository;
import gearworks.core.account.mysql.QueryCreateAccount;
import gearworks.core.account.mysql.QueryJoin;
import gearworks.core.account.mysql.QueryQuit;
import gearworks.core.common.Rank;
import gearworks.core.mysql.RepositoryBase;
import org.bukkit.entity.Player;

import java.util.Map;

public class CoreClient {

    private final Player player;

    private String name;
    private String displayName;

    private Rank rank;

    private int coins;
    private int gems;

    public CoreClient (final Player player){
        this.player = player;
        this.name = player.getName ();

        load ();
    }

    private void load (){
        if (!RepositoryBase.getInstance ().entryExists ("sts_players", "player_name", name)){
            RepositoryBase.getInstance ().getConsumer ().queueQuery (new QueryCreateAccount (player));
            rank = Rank.GUEST;

            coins = 0;
            gems = 0;

            displayName = name;
        }else{
            final Map<String, Object> playerMap = AccountRepository.getInstance ().getPlayerMap (name);
            rank = Rank.getRankByName ((String) playerMap.get ("rank"));

            coins = (Integer) playerMap.get ("coins");
            gems = (Integer) playerMap.get ("gems");

            displayName = (String) playerMap.get ("displayName");
        }

        // Last online, is online, joins, etc.
        RepositoryBase.getInstance ().getConsumer ().queueQuery (new QueryJoin (player));
    }

    public void save (){
        RepositoryBase.getInstance ().getConsumer ().queueQuery (new QueryQuit (this));
    }

    public Player getPlayer (){
        return player;
    }

    public String getName (){
        return name;
    }

    public String getDisplayName (){
        return displayName;
    }

    public Rank getRank (){
        return rank;
    }
}
