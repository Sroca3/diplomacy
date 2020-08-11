package io.github.sroca3.diplomacy;

import java.util.Locale;
import java.util.Objects;

public enum UnitType {
    ARMY,
    FLEET;

    public static UnitType from(String name) {
        if (Objects.equals(name.toLowerCase(Locale.ENGLISH), ARMY.name().toLowerCase(Locale.ENGLISH)) || name.equalsIgnoreCase("A")) {
            return ARMY;
        }
        if (Objects.equals(name.toLowerCase(Locale.ENGLISH), FLEET.name().toLowerCase(Locale.ENGLISH)) || name.equalsIgnoreCase("F")) {
            return FLEET;
        }
        return null;
    }

    public boolean isFleet() {
        return this == FLEET;
    }
}
