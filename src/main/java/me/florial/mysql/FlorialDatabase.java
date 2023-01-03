package me.florial.mysql;

import me.florial.Florial;
import me.florial.models.PlayerData;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.UUID;

public class FlorialDatabase {

    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection != null) return connection;

        String url = Florial.getInstance().getConfig().getString("url");
        String password = Florial.getInstance().getConfig().getString("password");
        String user = "root";
        
        if (url == null || password == null) {
            System.out.println("Please set the url and password in the config.yml");
            return null;
        }

        this.connection = DriverManager.getConnection(url, user, password);

        System.out.println("Connected to SparkedHost Database!");
        return this.connection;
    }

    public void initializeDatabase() throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS florial_players(uuid varchar(36) primary key, species int, dna int)");
        statement.execute();

        System.out.println("Created table!");
    }

    public PlayerData findPlayerStatsbyUUID(String u) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM florial_players WHERE uuid = ?");
        statement.setString(1, u);
        ResultSet results = statement.executeQuery();
        if (results.next()){

            int species = results.getInt("me/florial/species");
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
        statement.setInt(2, data.getSpecieId());
        statement.setInt(3, data.getDna());

        statement.executeUpdate();

        statement.close();

    }

    public void updatePlayerStats(PlayerData data) throws SQLException{
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement statement = Florial.getInstance().getDatabase().getConnection().prepareStatement("UPDATE florial_players SET species = ?, dna = ? WHERE uuid = ?");
                    statement.setInt(1, data.getSpecieId());
                    statement.setInt(2, data.getDna());
                    statement.setString(3, data.getUuid());

                    statement.executeUpdate();

                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(Florial.getInstance());
    }

    public PlayerData getPlayerStatsfromDatabase(UUID u) throws SQLException{

        PlayerData data = Florial.getInstance().getDatabase().findPlayerStatsbyUUID(u.toString());
        if (data == null){
            data = new PlayerData(u.toString(), 0, 0);
            Florial.getInstance().getDatabase().createPlayerStats(data);
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
