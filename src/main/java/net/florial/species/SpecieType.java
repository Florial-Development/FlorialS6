package net.florial.species;

import lombok.Getter;
import net.florial.species.impl.Cat;
import net.florial.species.impl.Fox;
import net.florial.species.impl.Human;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SpecieType {

    //base species

    NONE(0, null),
    CAT(1, new Cat(1)),
    FOX(2, new Fox(2)),
    HUMAN(3, new Human(3)),

    // user made species
    ENDN(4, null),
    DD(5, null),
    WILLOWWISP(6, null),
    SQUEAKY(7, null),
    DRACONIC(8, null),
    CACTUS(9, null),
    THUNDERBAT(10, null),
    LIVINGMATTER(11, null),
    NATUREFOX(12, null),
    BASSBEAST(13, null),
    URA(14, null),
    DRYAD(15, null),
    HONEYBUG(16, null),
    MER(17, null),
    LYNX(18, null),
    BASILISK(19, null),
    NEKORYU(20, null),
    COMPUTER(21, null);
    //end




    @Getter private final int id;
    @Getter private final Species specie;

    SpecieType(int id, Species specie) {
        this.id = id;
        this.specie = specie;
    }

    public static SpecieType fromID(int id) {
        for (SpecieType e : values())
            if (e.id == id) return e;

        return null;
    }

    public static List<Species> getAllSpecies() {
        return Arrays.stream(values()).map(SpecieType::getSpecie).collect(Collectors.toList());
    }
}
