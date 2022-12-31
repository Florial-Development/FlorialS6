package species.speciesinternal;

public enum SpeciesEnum {
    NONE(0),
    CAT(1),
    FOX(2),
    HUMAN(3);

    public final int id;
    SpeciesEnum (int id) {
        this.id = id;
    }

    public static SpeciesEnum getSpeciesFromID(int id) {
        for (SpeciesEnum e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

    public static int getIdFromSpecies(SpeciesEnum species) {
        return species.id;
    }
}
