package io.github.sroca3.diplomacy;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public interface MapVariant {
    SortedSet<Country> getCountries();

    Map<Location, Set<Location>> getMovementGraph();

    Location parseLocation(String name);

    Map<Location, Set<Location>> getMovementGraph(UnitType unitType);

    long getStartingYear();
}