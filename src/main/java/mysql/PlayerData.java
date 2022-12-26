package mysql;

public class PlayerData {

    private String uuid;
    private int dna;
    private int species;


    public PlayerData(String uuid, int dna, int species) {
        this.uuid = uuid;
        this.dna = dna;
        this.species = species;
    }

    public int getSpecies() {return species;}

    public void setSpecies(int species) {this.species = species;}

    public int getDna() {return dna;}

    public void setDna(int dna) {this.dna = dna;}

    public String getUuid() {return uuid;}

    public void setUuid(String uuid) {this.uuid = uuid;}
}
