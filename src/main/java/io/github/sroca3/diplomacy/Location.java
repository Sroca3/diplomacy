package io.github.sroca3.diplomacy;

import java.util.Set;

public interface Location {
    default boolean supports(Unit unit) {
        return getSupportedTypes().contains(unit.getType());
    }

    Set<UnitType> getSupportedTypes();

    String getName();

    boolean isSupplyCenter();
}
