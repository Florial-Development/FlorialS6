package net.florial.features.upgrades;

import dev.morphia.annotations.Transient;
import lombok.Getter;
import lombok.Setter;

public enum Upgrade {

    HASTE(1, false),
    DOUBLEHEALTH(2, false);
    @Getter private final int upgrade;

    @Transient @Setter  @Getter private boolean has;

    Upgrade(int upgrade, boolean has) {

        this.upgrade = upgrade;
        this.has = has;
    }

}
