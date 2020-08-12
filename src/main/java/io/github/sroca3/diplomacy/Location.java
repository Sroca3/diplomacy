package io.github.sroca3.diplomacy;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface Location {
    default boolean supports(Unit unit) {
        return getSupportedTypes().contains(unit.getType());
    }

    Set<UnitType> getSupportedTypes();

    String getName();

    boolean isSupplyCenter();

    default boolean hasCoasts() {
        return false;
    }

    default List<Location> getCoasts() {
        return Collections.emptyList();
    }

    default boolean isCoast() {
        return false;
    }

    default Location getTerritory() {
        return this;
    }
}
