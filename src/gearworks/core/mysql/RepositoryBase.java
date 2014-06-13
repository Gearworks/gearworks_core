package gearworks.core.mysql;

import gearworks.core.common.util.Messaging;
import gearworks.core.mysql.consumer.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepositoryBase {

    private static String HOST = "108.61.139.226";
    private static int PORT = 3306;
    private static String DATABASE = "gearworks_arcade";

    private static String USER = "root";
    private static String PASS = "x8J7754x5Q257485v2256C13i";

    private ConnectionPool connectionPool;
    private Consumer consumer;

    private Connection conn = null;

    private static RepositoryBase instance;

    public RepositoryBase (JavaPlugin plugin){
        instance = this;

        try{

            connectionPool = new ConnectionPool (HOST, PORT, DATABASE, USER, PASS);
            conn = connectionPool.getConnection ();

            if (conn == null){
                Messaging.severe ("Could not create a connection to the MySQL server, stopping plugin...");
                Bukkit.getPluginManager ().disablePlugin (plugin);
            }

            consumer = new Consumer (conn);

            // Need to run consumer to allow it to run mysql every second
            plugin.getServer ().getScheduler ().runTaskTimer (plugin, consumer, 120L, 120L);

        } catch (NullPointerException exception){
            Messaging.severe ("Error while initializing: %s", new Object[]{exception.getMessage ()});
            exception.printStackTrace();
        } catch (Exception exception){
            Messaging.severe ("Error while initializing: %s", new Object[]{exception.getMessage ()});
            exception.printStackTrace();
        }
    }

    public static RepositoryBase initialize (JavaPlugin plugin){
        if (instance == null){
            instance = new RepositoryBase (plugin);
        }

        return instance;
    }

    public static RepositoryBase getInstance (){
        return instance;
    }

    public boolean entryExists (final String tableName, final String rowName, final String entryName){

        try{

            final ResultSet resultSet = conn.prepareStatement (String.format ("SELECT * FROM `%s` WHERE %s = '%s'", tableName, rowName, entryName)).executeQuery ();
            if (resultSet.next ()){
                return true;
            }
        } catch (NullPointerException e){
            e.printStackTrace ();
        } catch (SQLException e){
            e.printStackTrace ();
        }

        return false;
    }

    /**
     *
     * @return consumer task that executes all mysql
     */
    public Consumer getConsumer (){
        return consumer;
    }

    public Connection getConnection (){
        return conn;
    }
}
