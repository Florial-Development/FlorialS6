package mysql;

import core.Florial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import species.speciesinternal.SpeciesEnum;
import upgrades.Skill;

import java.sql.*;
import java.util.*;

public class FlorialDatabase {

    public Florial plugin;
    public FlorialDatabase(Florial plugin){
        this.plugin = plugin;
    }

    private HashMap<Skill, Integer> skillmap = new HashMap<>();

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
        String sql = "CREATE TABLE IF NOT EXISTS florial_players(uuid varchar(36) primary key, species int, dna int, skills varchar(36))";
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
            String skills = results.getString("skills");

            PlayerData playerData = new PlayerData(u, species, dna, fetchSkills(skills));

            statement.close();
            return playerData;
        }
        statement.close();
        return null;
    }


    public void createPlayerStats(PlayerData data) throws SQLException{


        ArrayList<Integer> indexes = new ArrayList<>(Arrays.asList(0,0,0,0,0));
        ArrayList<Skill> newskills = createSkills(indexes);

        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO florial_players(uuid, species, dna, skills) VALUES (?, ?, ?, ?)");

        statement.setString(1, data.getUuid());
        statement.setInt(2, data.getSpecies());
        statement.setInt(3, data.getDna());
        statement.setString(4, SkillsConvert(newskills));

        statement.executeUpdate();

        statement.close();

    }

    public void updatePlayerStats(PlayerData data) throws SQLException{
        new BukkitRunnable() {
            @Override
            public void run() {
                PreparedStatement statement = null;
                try {
                    statement = Florial.getInstance().getDatabase().getConnection().prepareStatement("UPDATE florial_players SET species = ?, dna = ?, skills = ? WHERE uuid = ?");
                    statement.setInt(1, data.getSpecies());
                    statement.setInt(2, data.getDna());
                    statement.setString(3, SkillsConvert(data.getSkills()));
                    statement.setString(4, data.getUuid());

                    statement.executeUpdate();

                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskAsynchronously(Florial.getInstance());
    }

    public String SkillsConvert(ArrayList<Skill> skills){
        ArrayList<Integer> skilllvls =  new ArrayList<>(Arrays.asList(
                skills.get(0).getSkillLevel(), skills.get(1).getSkillLevel(),
                skills.get(2).getSkillLevel(), skills.get(3).getSkillLevel(),
                skills.get(4).getSkillLevel()
        ));
        System.out.println(" new skills array converted to integers from levels " + skilllvls);

        String commaSeparatedString = String.join(",", skilllvls.toString());

        System.out.println(" skills integers converted to separated string " + commaSeparatedString);

        return commaSeparatedString;
    }

    public ArrayList<Skill> createSkills(List<Integer> ints) {

        ArrayList skillarray = new ArrayList<Skill>();
        int index = 0;
        for (int i = 0; i < 5; i++){
            index++;
            Skill skill = Skill.getSkillsId(index);
            skill.setLevel(0);
            skillarray.add(skill);
        }
        return skillarray;
    }

    public ArrayList<Skill> fetchSkills(String result) throws SQLException {

        result = result.replaceAll("\\[", "");
        result = result.replaceAll("\\]", "");
        result = result.replaceAll(" ", "");
        String[] values = result.split(",");
        ArrayList skillarray = new ArrayList<Skill>();

        int index = 0;

        for (String str : values){
            index++;
            Skill skill = Skill.getSkillsId(index);
            skill.setLevel(Integer.valueOf(str));
            skillarray.add(skill);
        }
        return skillarray;

    }

    public PlayerData getPlayerStatsfromDatabase(UUID u) throws SQLException{
        List<Integer> indexes;
        PlayerData data = plugin.getDatabase().findPlayerStatsbyUUID(u.toString());
        if (data == null){
            indexes = Arrays.asList(0,0,0,0,0);
            data = new PlayerData(u.toString(), 0, 0, createSkills(indexes));
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
