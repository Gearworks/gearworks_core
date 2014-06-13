package gearworks.core.account.mysql;


import gearworks.core.account.CoreClient;
import gearworks.core.mysql.consumer.Query;

public class QueryQuit implements Query {

    private CoreClient coreClient;
    private long timeStamp;

    public QueryQuit (CoreClient coreClient){
        this.coreClient = coreClient;
        timeStamp = (System.currentTimeMillis () / 1000L);
    }

    public String[] getQuery () {
        return new String[]{ String.format ("UPDATE `sts_players` SET is_online = 0, online_time = online_time + (%d - last_online), last_online = %d, rank = '%s' WHERE player_name = '%s'", new Object[]{Long.valueOf (timeStamp), Long.valueOf (timeStamp), coreClient.getRank ().getRankName (), coreClient.getName ()})};
    }
}
