package gearworks.core.account.mysql;

// UTILITY CLASS FOR PLAYER BASED RESULT SETS

import gearworks.core.mysql.RepositoryBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AccountRepository {

    private static AccountRepository instance;

    private RepositoryBase repository;

    public AccountRepository (RepositoryBase repository){
        instance = this;

        this.repository = repository;
    }

    public static AccountRepository initialize (RepositoryBase repository){
        if (instance == null){
            instance = new AccountRepository (repository);
        }

        return instance;
    }

    public static AccountRepository getInstance (){
        return instance;
    }

    public RepositoryBase getRepository (){
        return repository;
    }

    public Map<String, Object> getPlayerMap (final String playerName){
        final Connection conn = getRepository ().getConnection ();
        final HashMap<String, Object> toReturn = new HashMap<String, Object> ();

        try{
            // Load player stats
            ResultSet resultSet = conn.prepareStatement (String.format ("SELECT * FROM `sts_players` WHERE player_name = '%s'", playerName)).executeQuery ();
            ResultSetMetaData rsm = resultSet.getMetaData ();
            while (resultSet.next ()){
                for (int i = 1; i < rsm.getColumnCount (); i++){
                    toReturn.put (rsm.getColumnName (i), resultSet.getObject (i));
                }
            }

            // Load donation stats
            resultSet = conn.prepareStatement (String.format ("SELECT * FROM `sts_donations` WHERE player_name = '%s'", playerName)).executeQuery ();
            rsm = resultSet.getMetaData ();
            while (resultSet.next ()){
                for (int i = 1; i < rsm.getColumnCount (); i++){
                    toReturn.put (rsm.getColumnName (i), resultSet.getObject (i));
                }
            }
        } catch (SQLException e){
            e.printStackTrace ();
        }

        return toReturn;
    }
}
