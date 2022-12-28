package mysql;

import core.Florial;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

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

    public PlayerData findPlayerStatsbyUUID(String u) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM florial_players WHERE uuid = ?");
        statement.setString(1, u);
        ResultSet results = statement.executeQuery();
        if (results.next()){

            int species = results.getInt("species");
            int dna = results.getInt("dna");

            PlayerData playerData = new PlayerData(u, species, dna);

            statement.close();
            return playerData;
        }
        statement.close();
        return null;
    }


    public void createPlayerStats(PlayerData data) throws SQLException{

        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO florial_players(uuid, species, dna) VALUES (?, ?, ?)");

        statement.setString(1, data.getUuid());
        statement.setInt(2, data.getSpecies());
        statement.setInt(3, data.getDna());

        statement.executeUpdate();

        statement.close();

    }

    public void updatePlayerStats(PlayerData data) throws SQLException{

        PreparedStatement statement = getConnection().prepareStatement("UPDATE florial_players SET species = ?, dna = ? WHERE uuid = ?");

        statement.setInt(1, data.getSpecies());
        statement.setInt(2, data.getDna());
        statement.setString(3, data.getUuid());

        statement.executeUpdate();

        statement.close();

    }

    public PlayerData getPlayerStatsfromDatabase(UUID u) throws SQLException{

        PlayerData data = plugin.getDatabase().findPlayerStatsbyUUID(u.toString());
        if (data == null){
            data = new PlayerData(u.toString(), 0, 0);
            plugin.getDatabase().createPlayerStats(data);
            return data;
        } else {
            return data;
        }


    }

    public void deletePlayerStats(String u) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM florial_players WHERE uuid = ?");
        statement.setString(1, u);
    }

    public void closeConnection(){
        try{
            if (this.connection != null) {
                this.connection.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
