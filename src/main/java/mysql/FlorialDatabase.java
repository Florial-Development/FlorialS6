package mysql;

import core.Florial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class FlorialDatabase {

    public Florial plugin;
    public FlorialDatabase(Florial plugin){
        this.plugin = plugin;
    }

    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection != null) {
            return connection;
        }

        String url = plugin.getConfig().getString("url");
        String password = plugin.getConfig().getString("password");
        String user = "root";

        this.connection = DriverManager.getConnection(url, user, password);

        System.out.println("Connected to SparkedHost Database!");
        return this.connection;
    }

    public void initializeDatabase() throws SQLException {
        Statement statement = getConnection().createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS florial_players(uuid varchar(36) primary key, species int, dna int)";
        statement.execute(sql);

        System.out.println("Created table!");

    }
}
