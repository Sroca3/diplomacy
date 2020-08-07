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
        return FLEET;
    }
}
