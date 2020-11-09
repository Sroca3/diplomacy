package io.github.sroca3.diplomacy;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface Location {
    default boolean supports(Unit unit) {
        return supports(unit.getType());
    }

    boolean supports(UnitType unitType);

    String getName();

    boolean isSupplyCenter();

    default boolean hasCoasts() {
        return !getCoasts().isEmpty();
    }

    default List<Location> getCoasts() {
        return Collections.emptyList();
    }

    default boolean isCoast() {
        return false;
    }

    @Nonnull default Location getTerritory() {
        return this;
    }

    boolean isSea();
}
