package io.github.sroca3.diplomacy;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public interface MapVariant {
    SortedSet<Country> getCountries();

    Map<Location, Set<Location>> getMovementGraph();

    Location parseLocation(String name);

    Map<Location, Set<Location>> getMovementGraph(UnitType unitType);

    long getStartingYear();

    default Map<Location, Unit> getStartingUnits() {
        return Collections.emptyMap();
    }

    default Map<Location, Country> getHomeCenters() {
        return Collections.emptyMap();
    }
}
