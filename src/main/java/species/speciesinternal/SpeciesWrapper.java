package species.speciesinternal;

import species.Cat;
import species.Fox;
import species.Human;

import java.util.HashMap;
import java.util.UUID;

public class SpeciesWrapper {
    // Define the different species that a player can be
    public static final int HUMAN = 0;
    public static final int CAT = 1;
    public static final int FOX = 2;

    public HashMap<UUID, Integer> playerSpecies;

    public SpeciesWrapper() {
        // Initialize the map
        playerSpecies = new HashMap<>();
    }

    // Method to set the species of a player
    public void setSpecies(UUID player, int species) {
        playerSpecies.put(player, species);
    }

    // Method to get the species of a player
    public String getSpecies(UUID player, Boolean obj) {
        Integer type =  playerSpecies.getOrDefault(player, HUMAN);
        if (obj == true) return type.toString();
        switch (type) {
            case CAT:
                return "cat";
            case HUMAN:
                return "human";
            case FOX:
                return "fox";
        }
        return null;
    }

    // Method to get the species object for a player
    public Species getSpeciesObject(UUID player) {
        int species = Integer.valueOf(getSpecies(player, true));
        switch (species) {
            case FOX:
                return new Fox();
            case CAT:
                return new Cat();
            case HUMAN:
                return new Human();
        }
        return null;
    }
}
