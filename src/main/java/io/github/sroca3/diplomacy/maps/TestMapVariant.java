package io.github.sroca3.diplomacy.maps;

import io.github.sroca3.diplomacy.Country;
import io.github.sroca3.diplomacy.StandardCountry;
import io.github.sroca3.diplomacy.Location;
import io.github.sroca3.diplomacy.MapVariant;
import io.github.sroca3.diplomacy.UnitType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *                 +---------+
 *                 |    D    |
 *                 +---------+
 *           +---------++---------+
 *           |    A    ||    B    |
 *          +---------++---------+
 *                 +---------+
 *                 |    C    |
 *                 +---------+
 */

public class TestMapVariant implements MapVariant {
    private static final Set<Location> locations = new HashSet<>();
    private static final Map<Location, Set<Location>> graph = new HashMap<>();
    private static final TestMapVariant INSTANCE = new TestMapVariant();

    private TestMapVariant() {
        locations.add(TestVariantLocation.A);
        locations.add(TestVariantLocation.B);
        locations.add(TestVariantLocation.C);
        locations.add(TestVariantLocation.D);

        locations.forEach(location -> addEdge(location, location));
        addEdge(TestVariantLocation.A, TestVariantLocation.B);
        addEdge(TestVariantLocation.A, TestVariantLocation.C);
        addEdge(TestVariantLocation.A, TestVariantLocation.D);
        addEdge(TestVariantLocation.B, TestVariantLocation.D);
        addEdge(TestVariantLocation.B, TestVariantLocation.C);
    }

    public static MapVariant getInstance() {
        return INSTANCE;
    }

    private void addEdge(Location one, Location two) {
        graph.putIfAbsent(one, new HashSet<>());
        graph.putIfAbsent(two, new HashSet<>());
        graph.get(one).add(two);
        graph.get(two).add(one);
    }

    @Override
    public SortedSet<Country> getCountries() {
        return new TreeSet<>(Set.of(StandardCountry.FRANCE, StandardCountry.ENGLAND));
    }

    @Override
    public Map<Location, Set<Location>> getMovementGraph() {
        return graph;
    }

    @Override
    public Location parseLocation(String part) {
        return null;
    }

    @Override
    public Map<Location, Set<Location>> getMovementGraph(UnitType unitType) {
        return graph;
    }

    @Override
    public long getStartingYear() {
        return 0;
    }

    @Override
    public Country getCountry(String countryName) {
        return null;
    }
}
