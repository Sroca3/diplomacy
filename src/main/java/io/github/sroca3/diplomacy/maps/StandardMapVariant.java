package io.github.sroca3.diplomacy.maps;

import io.github.sroca3.diplomacy.Country;
import io.github.sroca3.diplomacy.Location;
import io.github.sroca3.diplomacy.MapVariant;
import io.github.sroca3.diplomacy.UnitType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class StandardMapVariant implements MapVariant {
    private static final Map<UnitType, Map<Location, Set<Location>>> graphs = Map.of(
        UnitType.ARMY,
        new HashMap<>(),
        UnitType.FLEET,
        new HashMap<>()
    );
    private static final StandardMapVariant INSTANCE = new StandardMapVariant();
    public static MapVariant getInstance() {
        return INSTANCE;
    }

    public StandardMapVariant() {
        var armyGraph = graphs.get(UnitType.ARMY);
        addEdge(armyGraph, StandardVariantLocation.ST_PETERSBURG, StandardVariantLocation.BARENTS_SEA);
        addEdge(armyGraph, StandardVariantLocation.ST_PETERSBURG, StandardVariantLocation.NORWAY);
        addEdge(armyGraph, StandardVariantLocation.ST_PETERSBURG, StandardVariantLocation.FINLAND);
        addEdge(armyGraph, StandardVariantLocation.ST_PETERSBURG, StandardVariantLocation.LIVONIA);
        addEdge(armyGraph, StandardVariantLocation.ST_PETERSBURG, StandardVariantLocation.MOSCOW);

        addEdge(armyGraph, StandardVariantLocation.NORWAY, StandardVariantLocation.SWEDEN);
        addEdge(armyGraph, StandardVariantLocation.NORWAY, StandardVariantLocation.FINLAND);

        addEdge(armyGraph, StandardVariantLocation.SWEDEN, StandardVariantLocation.DENMARK);
        addEdge(armyGraph, StandardVariantLocation.SWEDEN, StandardVariantLocation.FINLAND);

        addEdge(armyGraph, StandardVariantLocation.LIVONIA, StandardVariantLocation.MOSCOW);
        addEdge(armyGraph, StandardVariantLocation.LIVONIA, StandardVariantLocation.WARSAW);
        addEdge(armyGraph, StandardVariantLocation.LIVONIA, StandardVariantLocation.PRUSSIA);

        addEdge(armyGraph, StandardVariantLocation.MOSCOW, StandardVariantLocation.WARSAW);
        addEdge(armyGraph, StandardVariantLocation.MOSCOW, StandardVariantLocation.UKRAINE);
        addEdge(armyGraph, StandardVariantLocation.MOSCOW, StandardVariantLocation.SEVASTOPOL);

        addEdge(armyGraph, StandardVariantLocation.WARSAW, StandardVariantLocation.PRUSSIA);
        addEdge(armyGraph, StandardVariantLocation.WARSAW, StandardVariantLocation.SILESIA);
        addEdge(armyGraph, StandardVariantLocation.WARSAW, StandardVariantLocation.GALICIA);
        addEdge(armyGraph, StandardVariantLocation.WARSAW, StandardVariantLocation.UKRAINE);

        addEdge(armyGraph, StandardVariantLocation.PRUSSIA, StandardVariantLocation.BERLIN);
        addEdge(armyGraph, StandardVariantLocation.PRUSSIA, StandardVariantLocation.SILESIA);

        addEdge(armyGraph, StandardVariantLocation.UKRAINE, StandardVariantLocation.GALICIA);
        addEdge(armyGraph, StandardVariantLocation.UKRAINE, StandardVariantLocation.RUMANIA);
        addEdge(armyGraph, StandardVariantLocation.UKRAINE, StandardVariantLocation.SEVASTOPOL);

        addEdge(armyGraph, StandardVariantLocation.SEVASTOPOL, StandardVariantLocation.ARMENIA);

        addEdge(armyGraph, StandardVariantLocation.SILESIA, StandardVariantLocation.BERLIN);
        addEdge(armyGraph, StandardVariantLocation.SILESIA, StandardVariantLocation.MUNICH);
        addEdge(armyGraph, StandardVariantLocation.SILESIA, StandardVariantLocation.BOHEMIA);
        addEdge(armyGraph, StandardVariantLocation.SILESIA, StandardVariantLocation.GALICIA);

        addEdge(armyGraph, StandardVariantLocation.GALICIA, StandardVariantLocation.VIENNA);
        addEdge(armyGraph, StandardVariantLocation.GALICIA, StandardVariantLocation.BUDAPEST);
        addEdge(armyGraph, StandardVariantLocation.GALICIA, StandardVariantLocation.RUMANIA);

        addEdge(armyGraph, StandardVariantLocation.BERLIN, StandardVariantLocation.KIEL);
        addEdge(armyGraph, StandardVariantLocation.BERLIN, StandardVariantLocation.MUNICH);

        addEdge(armyGraph, StandardVariantLocation.DENMARK, StandardVariantLocation.KIEL);

        addEdge(armyGraph, StandardVariantLocation.MUNICH, StandardVariantLocation.KIEL);
        addEdge(armyGraph, StandardVariantLocation.MUNICH, StandardVariantLocation.RUHR);
        addEdge(armyGraph, StandardVariantLocation.MUNICH, StandardVariantLocation.BURGUNDY);
        addEdge(armyGraph, StandardVariantLocation.MUNICH, StandardVariantLocation.TYROLIA);

        addEdge(armyGraph, StandardVariantLocation.BOHEMIA, StandardVariantLocation.MUNICH);

        addEdge(armyGraph, StandardVariantLocation.VIENNA, StandardVariantLocation.TYROLIA);
        addEdge(armyGraph, StandardVariantLocation.VIENNA, StandardVariantLocation.TRIESTE);
        addEdge(armyGraph, StandardVariantLocation.VIENNA, StandardVariantLocation.BUDAPEST);

        addEdge(armyGraph, StandardVariantLocation.BUDAPEST, StandardVariantLocation.TRIESTE);
        addEdge(armyGraph, StandardVariantLocation.BUDAPEST, StandardVariantLocation.RUMANIA);
        addEdge(armyGraph, StandardVariantLocation.BUDAPEST, StandardVariantLocation.SERBIA);

        addEdge(armyGraph, StandardVariantLocation.RUMANIA, StandardVariantLocation.SERBIA);
        addEdge(armyGraph, StandardVariantLocation.RUMANIA, StandardVariantLocation.BULGARIA);

        addEdge(armyGraph, StandardVariantLocation.KIEL, StandardVariantLocation.HOLLAND);
        addEdge(armyGraph, StandardVariantLocation.KIEL, StandardVariantLocation.RUHR);

        addEdge(armyGraph, StandardVariantLocation.SERBIA, StandardVariantLocation.BULGARIA);
        addEdge(armyGraph, StandardVariantLocation.SERBIA, StandardVariantLocation.GREECE);
        addEdge(armyGraph, StandardVariantLocation.SERBIA, StandardVariantLocation.ALBANIA);
        addEdge(armyGraph, StandardVariantLocation.SERBIA, StandardVariantLocation.TRIESTE);

        addEdge(armyGraph, StandardVariantLocation.ALBANIA, StandardVariantLocation.GREECE);

        addEdge(armyGraph, StandardVariantLocation.BULGARIA, StandardVariantLocation.GREECE);
        addEdge(armyGraph, StandardVariantLocation.BULGARIA, StandardVariantLocation.CONSTANTINOPLE);

        addEdge(armyGraph, StandardVariantLocation.CONSTANTINOPLE, StandardVariantLocation.ANKARA);
        addEdge(armyGraph, StandardVariantLocation.CONSTANTINOPLE, StandardVariantLocation.SMYRNA);

        addEdge(armyGraph, StandardVariantLocation.ANKARA, StandardVariantLocation.SMYRNA);
        addEdge(armyGraph, StandardVariantLocation.ANKARA, StandardVariantLocation.ARMENIA);

        addEdge(armyGraph, StandardVariantLocation.SMYRNA, StandardVariantLocation.SYRIA);
        addEdge(armyGraph, StandardVariantLocation.SMYRNA, StandardVariantLocation.ARMENIA);

        addEdge(armyGraph, StandardVariantLocation.HOLLAND, StandardVariantLocation.BELGIUM);
        addEdge(armyGraph, StandardVariantLocation.HOLLAND, StandardVariantLocation.RUHR);

        addEdge(armyGraph, StandardVariantLocation.BELGIUM, StandardVariantLocation.RUHR);
        addEdge(armyGraph, StandardVariantLocation.BELGIUM, StandardVariantLocation.PICARDY);
        addEdge(armyGraph, StandardVariantLocation.BELGIUM, StandardVariantLocation.BURGUNDY);

        addEdge(armyGraph, StandardVariantLocation.RUHR, StandardVariantLocation.BURGUNDY);

        addEdge(armyGraph, StandardVariantLocation.BURGUNDY, StandardVariantLocation.PARIS);
        addEdge(armyGraph, StandardVariantLocation.BURGUNDY, StandardVariantLocation.PICARDY);
        addEdge(armyGraph, StandardVariantLocation.BURGUNDY, StandardVariantLocation.MARSEILLES);

        addEdge(armyGraph, StandardVariantLocation.PARIS, StandardVariantLocation.BREST);
        addEdge(armyGraph, StandardVariantLocation.PARIS, StandardVariantLocation.PICARDY);
        addEdge(armyGraph, StandardVariantLocation.PARIS, StandardVariantLocation.GASCONY);

        addEdge(armyGraph, StandardVariantLocation.BREST, StandardVariantLocation.PICARDY);
        addEdge(armyGraph, StandardVariantLocation.BREST, StandardVariantLocation.GASCONY);

        addEdge(armyGraph, StandardVariantLocation.GASCONY, StandardVariantLocation.SPAIN);
        addEdge(armyGraph, StandardVariantLocation.GASCONY, StandardVariantLocation.MARSEILLES);

        addEdge(armyGraph, StandardVariantLocation.SPAIN, StandardVariantLocation.PORTUGAL);

        addEdge(armyGraph, StandardVariantLocation.MARSEILLES, StandardVariantLocation.PIEDMONT);
        addEdge(armyGraph, StandardVariantLocation.MARSEILLES, StandardVariantLocation.SPAIN);

        addEdge(armyGraph, StandardVariantLocation.PIEDMONT, StandardVariantLocation.TYROLIA);
        addEdge(armyGraph, StandardVariantLocation.PIEDMONT, StandardVariantLocation.VENICE);
        addEdge(armyGraph, StandardVariantLocation.PIEDMONT, StandardVariantLocation.TUSCANY);

        addEdge(armyGraph, StandardVariantLocation.TYROLIA, StandardVariantLocation.VENICE);
        addEdge(armyGraph, StandardVariantLocation.TYROLIA, StandardVariantLocation.TRIESTE);
        addEdge(armyGraph, StandardVariantLocation.TYROLIA, StandardVariantLocation.BOHEMIA);

        addEdge(armyGraph, StandardVariantLocation.VENICE, StandardVariantLocation.TUSCANY);
        addEdge(armyGraph, StandardVariantLocation.VENICE, StandardVariantLocation.APULIA);
        addEdge(armyGraph, StandardVariantLocation.VENICE, StandardVariantLocation.ROME);
        addEdge(armyGraph, StandardVariantLocation.VENICE, StandardVariantLocation.TRIESTE);

        addEdge(armyGraph, StandardVariantLocation.TUSCANY, StandardVariantLocation.ROME);

        addEdge(armyGraph, StandardVariantLocation.ROME, StandardVariantLocation.APULIA);
        addEdge(armyGraph, StandardVariantLocation.ROME, StandardVariantLocation.NAPLES);

        addEdge(armyGraph, StandardVariantLocation.NORTH_AFRICA, StandardVariantLocation.TUNIS);

        addEdge(armyGraph, StandardVariantLocation.CLYDE, StandardVariantLocation.EDINBURGH);
        addEdge(armyGraph, StandardVariantLocation.CLYDE, StandardVariantLocation.LIVERPOOL);

        addEdge(armyGraph, StandardVariantLocation.EDINBURGH, StandardVariantLocation.LIVERPOOL);
        addEdge(armyGraph, StandardVariantLocation.EDINBURGH, StandardVariantLocation.YORKSHIRE);

        addEdge(armyGraph, StandardVariantLocation.YORKSHIRE, StandardVariantLocation.LIVERPOOL);
        addEdge(armyGraph, StandardVariantLocation.YORKSHIRE, StandardVariantLocation.LONDON);
        addEdge(armyGraph, StandardVariantLocation.YORKSHIRE, StandardVariantLocation.WALES);

        addEdge(armyGraph, StandardVariantLocation.LIVERPOOL, StandardVariantLocation.WALES);

        addEdge(armyGraph, StandardVariantLocation.WALES, StandardVariantLocation.LONDON);

        armyGraph.forEach((key, value) -> this.addEdge(armyGraph, key, key));

        var fleetGraph = graphs.get(UnitType.FLEET);

        addEdge(fleetGraph, StandardVariantLocation.ST_PETERSBURG_NC, StandardVariantLocation.BARENTS_SEA);
        addEdge(fleetGraph, StandardVariantLocation.ST_PETERSBURG_NC, StandardVariantLocation.NORWAY);

        addEdge(fleetGraph, StandardVariantLocation.BARENTS_SEA, StandardVariantLocation.NORWEGIAN_SEA);
        addEdge(fleetGraph, StandardVariantLocation.BARENTS_SEA, StandardVariantLocation.NORWAY);

        addEdge(fleetGraph, StandardVariantLocation.NORWAY, StandardVariantLocation.SWEDEN);
        addEdge(fleetGraph, StandardVariantLocation.NORWAY, StandardVariantLocation.NORWEGIAN_SEA);

        addEdge(fleetGraph, StandardVariantLocation.NORWEGIAN_SEA, StandardVariantLocation.NORTH_ATLANTIC_OCEAN);
        addEdge(fleetGraph, StandardVariantLocation.NORWEGIAN_SEA, StandardVariantLocation.CLYDE);
        addEdge(fleetGraph, StandardVariantLocation.NORWEGIAN_SEA, StandardVariantLocation.EDINBURGH);
        addEdge(fleetGraph, StandardVariantLocation.NORWEGIAN_SEA, StandardVariantLocation.NORTH_SEA);

        addEdge(fleetGraph, StandardVariantLocation.SWEDEN, StandardVariantLocation.SKAGERRAK);
        addEdge(fleetGraph, StandardVariantLocation.SWEDEN, StandardVariantLocation.DENMARK);
        addEdge(fleetGraph, StandardVariantLocation.SWEDEN, StandardVariantLocation.BALTIC_SEA);
        addEdge(fleetGraph, StandardVariantLocation.SWEDEN, StandardVariantLocation.GULF_OF_BOTHNIA);
        addEdge(fleetGraph, StandardVariantLocation.SWEDEN, StandardVariantLocation.FINLAND);

        addEdge(fleetGraph, StandardVariantLocation.FINLAND, StandardVariantLocation.GULF_OF_BOTHNIA);
        addEdge(fleetGraph, StandardVariantLocation.FINLAND, StandardVariantLocation.ST_PETERSBURG_SC);

        addEdge(fleetGraph, StandardVariantLocation.BALTIC_SEA, StandardVariantLocation.DENMARK);
        addEdge(fleetGraph, StandardVariantLocation.BALTIC_SEA, StandardVariantLocation.GULF_OF_BOTHNIA);
        addEdge(fleetGraph, StandardVariantLocation.BALTIC_SEA, StandardVariantLocation.KIEL);
        addEdge(fleetGraph, StandardVariantLocation.BALTIC_SEA, StandardVariantLocation.BERLIN);
        addEdge(fleetGraph, StandardVariantLocation.BALTIC_SEA, StandardVariantLocation.PRUSSIA);
        addEdge(fleetGraph, StandardVariantLocation.BALTIC_SEA, StandardVariantLocation.LIVONIA);

        addEdge(fleetGraph, StandardVariantLocation.GULF_OF_BOTHNIA, StandardVariantLocation.ST_PETERSBURG_SC);

        addEdge(fleetGraph, StandardVariantLocation.GULF_OF_BOTHNIA, StandardVariantLocation.ST_PETERSBURG_SC);

        addEdge(fleetGraph, StandardVariantLocation.ST_PETERSBURG_SC, StandardVariantLocation.LIVONIA);

        addEdge(fleetGraph, StandardVariantLocation.LIVONIA, StandardVariantLocation.PRUSSIA);

        addEdge(fleetGraph, StandardVariantLocation.PRUSSIA, StandardVariantLocation.BERLIN);

        addEdge(fleetGraph, StandardVariantLocation.BERLIN, StandardVariantLocation.KIEL);

        addEdge(fleetGraph, StandardVariantLocation.KIEL, StandardVariantLocation.DENMARK);
        addEdge(fleetGraph, StandardVariantLocation.KIEL, StandardVariantLocation.HOLLAND);
        addEdge(fleetGraph, StandardVariantLocation.KIEL, StandardVariantLocation.HELGOLAND_BIGHT);

        addEdge(fleetGraph, StandardVariantLocation.DENMARK, StandardVariantLocation.SKAGERRAK);
        addEdge(fleetGraph, StandardVariantLocation.DENMARK, StandardVariantLocation.NORTH_SEA);
        addEdge(fleetGraph, StandardVariantLocation.DENMARK, StandardVariantLocation.HELGOLAND_BIGHT);

        addEdge(fleetGraph, StandardVariantLocation.SKAGERRAK, StandardVariantLocation.NORTH_SEA);

        addEdge(fleetGraph, StandardVariantLocation.HELGOLAND_BIGHT, StandardVariantLocation.NORTH_SEA);
        addEdge(fleetGraph, StandardVariantLocation.HELGOLAND_BIGHT, StandardVariantLocation.HOLLAND);

        addEdge(fleetGraph, StandardVariantLocation.NORTH_SEA, StandardVariantLocation.EDINBURGH);
        addEdge(fleetGraph, StandardVariantLocation.NORTH_SEA, StandardVariantLocation.YORKSHIRE);
        addEdge(fleetGraph, StandardVariantLocation.NORTH_SEA, StandardVariantLocation.LONDON);
        addEdge(fleetGraph, StandardVariantLocation.NORTH_SEA, StandardVariantLocation.ENGLISH_CHANNEL);
        addEdge(fleetGraph, StandardVariantLocation.NORTH_SEA, StandardVariantLocation.BELGIUM);
        addEdge(fleetGraph, StandardVariantLocation.NORTH_SEA, StandardVariantLocation.HOLLAND);

        addEdge(fleetGraph, StandardVariantLocation.EDINBURGH, StandardVariantLocation.YORKSHIRE);
        addEdge(fleetGraph, StandardVariantLocation.EDINBURGH, StandardVariantLocation.CLYDE);

        addEdge(fleetGraph, StandardVariantLocation.YORKSHIRE, StandardVariantLocation.LONDON);

        addEdge(fleetGraph, StandardVariantLocation.LONDON, StandardVariantLocation.WALES);
        addEdge(fleetGraph, StandardVariantLocation.LONDON, StandardVariantLocation.ENGLISH_CHANNEL);

        addEdge(fleetGraph, StandardVariantLocation.WALES, StandardVariantLocation.LIVERPOOL);
        addEdge(fleetGraph, StandardVariantLocation.WALES, StandardVariantLocation.ENGLISH_CHANNEL);
        addEdge(fleetGraph, StandardVariantLocation.WALES, StandardVariantLocation.IRISH_SEA);

        addEdge(fleetGraph, StandardVariantLocation.LIVERPOOL, StandardVariantLocation.CLYDE);
        addEdge(fleetGraph, StandardVariantLocation.LIVERPOOL, StandardVariantLocation.NORTH_ATLANTIC_OCEAN);

        addEdge(fleetGraph, StandardVariantLocation.NORTH_ATLANTIC_OCEAN, StandardVariantLocation.IRISH_SEA);
        addEdge(fleetGraph, StandardVariantLocation.NORTH_ATLANTIC_OCEAN, StandardVariantLocation.MID_ATLANTIC_OCEAN);

        addEdge(fleetGraph, StandardVariantLocation.IRISH_SEA, StandardVariantLocation.MID_ATLANTIC_OCEAN);
        addEdge(fleetGraph, StandardVariantLocation.IRISH_SEA, StandardVariantLocation.ENGLISH_CHANNEL);

        addEdge(fleetGraph, StandardVariantLocation.ENGLISH_CHANNEL, StandardVariantLocation.BELGIUM);
        addEdge(fleetGraph, StandardVariantLocation.ENGLISH_CHANNEL, StandardVariantLocation.PICARDY);
        addEdge(fleetGraph, StandardVariantLocation.ENGLISH_CHANNEL, StandardVariantLocation.BREST);
        addEdge(fleetGraph, StandardVariantLocation.ENGLISH_CHANNEL, StandardVariantLocation.MID_ATLANTIC_OCEAN);

        addEdge(fleetGraph, StandardVariantLocation.HOLLAND, StandardVariantLocation.BELGIUM);

        addEdge(fleetGraph, StandardVariantLocation.BELGIUM, StandardVariantLocation.PICARDY);

        addEdge(fleetGraph, StandardVariantLocation.PICARDY, StandardVariantLocation.BREST);

        addEdge(fleetGraph, StandardVariantLocation.BREST, StandardVariantLocation.MID_ATLANTIC_OCEAN);
        addEdge(fleetGraph, StandardVariantLocation.BREST, StandardVariantLocation.GASCONY);

        addEdge(fleetGraph, StandardVariantLocation.GASCONY, StandardVariantLocation.SPAIN_NC);

        addEdge(fleetGraph, StandardVariantLocation.SPAIN_NC, StandardVariantLocation.PORTUGAL);

        addEdge(fleetGraph, StandardVariantLocation.PORTUGAL, StandardVariantLocation.SPAIN_SC);

        addEdge(fleetGraph, StandardVariantLocation.MID_ATLANTIC_OCEAN, StandardVariantLocation.GASCONY);
        addEdge(fleetGraph, StandardVariantLocation.MID_ATLANTIC_OCEAN, StandardVariantLocation.SPAIN_NC);
        addEdge(fleetGraph, StandardVariantLocation.MID_ATLANTIC_OCEAN, StandardVariantLocation.PORTUGAL);
        addEdge(fleetGraph, StandardVariantLocation.MID_ATLANTIC_OCEAN, StandardVariantLocation.SPAIN_SC);
        addEdge(fleetGraph, StandardVariantLocation.MID_ATLANTIC_OCEAN, StandardVariantLocation.NORTH_AFRICA);
        addEdge(fleetGraph, StandardVariantLocation.MID_ATLANTIC_OCEAN, StandardVariantLocation.WESTERN_MEDITERRANEAN);

        addEdge(fleetGraph, StandardVariantLocation.SPAIN_SC, StandardVariantLocation.MARSEILLES);
        addEdge(fleetGraph, StandardVariantLocation.SPAIN_SC, StandardVariantLocation.GULF_OF_LYON);
        addEdge(fleetGraph, StandardVariantLocation.SPAIN_SC, StandardVariantLocation.WESTERN_MEDITERRANEAN);

        addEdge(fleetGraph, StandardVariantLocation.MARSEILLES, StandardVariantLocation.PIEDMONT);

        addEdge(fleetGraph, StandardVariantLocation.PIEDMONT, StandardVariantLocation.TUSCANY);

        addEdge(fleetGraph, StandardVariantLocation.TUSCANY, StandardVariantLocation.ROME);

        addEdge(fleetGraph, StandardVariantLocation.ROME, StandardVariantLocation.NAPLES);

        addEdge(fleetGraph, StandardVariantLocation.NAPLES, StandardVariantLocation.APULIA);

        addEdge(fleetGraph, StandardVariantLocation.APULIA, StandardVariantLocation.VENICE);

        addEdge(fleetGraph, StandardVariantLocation.VENICE, StandardVariantLocation.TRIESTE);

        addEdge(fleetGraph, StandardVariantLocation.TRIESTE, StandardVariantLocation.ALBANIA);

        addEdge(fleetGraph, StandardVariantLocation.ALBANIA, StandardVariantLocation.GREECE);

        addEdge(fleetGraph, StandardVariantLocation.GREECE, StandardVariantLocation.BULGARIA_SC);

        addEdge(fleetGraph, StandardVariantLocation.BULGARIA_SC, StandardVariantLocation.CONSTANTINOPLE);

        addEdge(fleetGraph, StandardVariantLocation.CONSTANTINOPLE, StandardVariantLocation.SMYRNA);
        addEdge(fleetGraph, StandardVariantLocation.CONSTANTINOPLE, StandardVariantLocation.BULGARIA_EC);

        addEdge(fleetGraph, StandardVariantLocation.SMYRNA, StandardVariantLocation.SYRIA);

        addEdge(fleetGraph, StandardVariantLocation.BULGARIA_EC, StandardVariantLocation.RUMANIA);

        addEdge(fleetGraph, StandardVariantLocation.RUMANIA, StandardVariantLocation.SEVASTOPOL);

        addEdge(fleetGraph, StandardVariantLocation.SEVASTOPOL, StandardVariantLocation.ARMENIA);

        addEdge(fleetGraph, StandardVariantLocation.ARMENIA, StandardVariantLocation.ANKARA);

        addEdge(fleetGraph, StandardVariantLocation.ANKARA, StandardVariantLocation.CONSTANTINOPLE);

        addEdge(fleetGraph, StandardVariantLocation.WESTERN_MEDITERRANEAN, StandardVariantLocation.NORTH_AFRICA);
        addEdge(fleetGraph, StandardVariantLocation.WESTERN_MEDITERRANEAN, StandardVariantLocation.TUNIS);
        addEdge(fleetGraph, StandardVariantLocation.WESTERN_MEDITERRANEAN, StandardVariantLocation.GULF_OF_LYON);
        addEdge(fleetGraph, StandardVariantLocation.WESTERN_MEDITERRANEAN, StandardVariantLocation.TYRRHENIAN_SEA);
        addEdge(fleetGraph, StandardVariantLocation.WESTERN_MEDITERRANEAN, StandardVariantLocation.SPAIN_SC);

        addEdge(fleetGraph, StandardVariantLocation.GULF_OF_LYON, StandardVariantLocation.SPAIN_SC);
        addEdge(fleetGraph, StandardVariantLocation.GULF_OF_LYON, StandardVariantLocation.MARSEILLES);
        addEdge(fleetGraph, StandardVariantLocation.GULF_OF_LYON, StandardVariantLocation.PIEDMONT);
        addEdge(fleetGraph, StandardVariantLocation.GULF_OF_LYON, StandardVariantLocation.TUSCANY);
        addEdge(fleetGraph, StandardVariantLocation.GULF_OF_LYON, StandardVariantLocation.TYRRHENIAN_SEA);

        addEdge(fleetGraph, StandardVariantLocation.TYRRHENIAN_SEA, StandardVariantLocation.TUSCANY);
        addEdge(fleetGraph, StandardVariantLocation.TYRRHENIAN_SEA, StandardVariantLocation.ROME);
        addEdge(fleetGraph, StandardVariantLocation.TYRRHENIAN_SEA, StandardVariantLocation.NAPLES);
        addEdge(fleetGraph, StandardVariantLocation.TYRRHENIAN_SEA, StandardVariantLocation.IONIAN_SEA);
        addEdge(fleetGraph, StandardVariantLocation.TYRRHENIAN_SEA, StandardVariantLocation.TUNIS);

        addEdge(fleetGraph, StandardVariantLocation.IONIAN_SEA, StandardVariantLocation.TUNIS);
        addEdge(fleetGraph, StandardVariantLocation.IONIAN_SEA, StandardVariantLocation.NAPLES);
        addEdge(fleetGraph, StandardVariantLocation.IONIAN_SEA, StandardVariantLocation.APULIA);
        addEdge(fleetGraph, StandardVariantLocation.IONIAN_SEA, StandardVariantLocation.ADRIATIC_SEA);
        addEdge(fleetGraph, StandardVariantLocation.IONIAN_SEA, StandardVariantLocation.ALBANIA);
        addEdge(fleetGraph, StandardVariantLocation.IONIAN_SEA, StandardVariantLocation.GREECE);
        addEdge(fleetGraph, StandardVariantLocation.IONIAN_SEA, StandardVariantLocation.AEGEAN_SEA);
        addEdge(fleetGraph, StandardVariantLocation.IONIAN_SEA, StandardVariantLocation.EASTERN_MEDITERRANEAN);

        addEdge(fleetGraph, StandardVariantLocation.ADRIATIC_SEA, StandardVariantLocation.APULIA);
        addEdge(fleetGraph, StandardVariantLocation.ADRIATIC_SEA, StandardVariantLocation.VENICE);
        addEdge(fleetGraph, StandardVariantLocation.ADRIATIC_SEA, StandardVariantLocation.TRIESTE);
        addEdge(fleetGraph, StandardVariantLocation.ADRIATIC_SEA, StandardVariantLocation.ALBANIA);

        addEdge(fleetGraph, StandardVariantLocation.AEGEAN_SEA, StandardVariantLocation.GREECE);
        addEdge(fleetGraph, StandardVariantLocation.AEGEAN_SEA, StandardVariantLocation.BULGARIA_SC);
        addEdge(fleetGraph, StandardVariantLocation.AEGEAN_SEA, StandardVariantLocation.CONSTANTINOPLE);
        addEdge(fleetGraph, StandardVariantLocation.AEGEAN_SEA, StandardVariantLocation.SMYRNA);
        addEdge(fleetGraph, StandardVariantLocation.AEGEAN_SEA, StandardVariantLocation.EASTERN_MEDITERRANEAN);

        addEdge(fleetGraph, StandardVariantLocation.EASTERN_MEDITERRANEAN, StandardVariantLocation.SMYRNA);
        addEdge(fleetGraph, StandardVariantLocation.EASTERN_MEDITERRANEAN, StandardVariantLocation.SYRIA);

        addEdge(fleetGraph, StandardVariantLocation.BLACK_SEA, StandardVariantLocation.BULGARIA_EC);
        addEdge(fleetGraph, StandardVariantLocation.BLACK_SEA, StandardVariantLocation.RUMANIA);
        addEdge(fleetGraph, StandardVariantLocation.BLACK_SEA, StandardVariantLocation.SEVASTOPOL);
        addEdge(fleetGraph, StandardVariantLocation.BLACK_SEA, StandardVariantLocation.ARMENIA);
        addEdge(fleetGraph, StandardVariantLocation.BLACK_SEA, StandardVariantLocation.ANKARA);
        addEdge(fleetGraph, StandardVariantLocation.BLACK_SEA, StandardVariantLocation.CONSTANTINOPLE);

        fleetGraph.forEach((key, value) -> this.addEdge(fleetGraph, key, key));
    }

    private void addEdge(Map<Location, Set<Location>> graph, Location one, Location two) {
        graph.putIfAbsent(one, new HashSet<>());
        graph.putIfAbsent(two, new HashSet<>());
        graph.get(one).add(two);
        graph.get(two).add(one);
    }

    @Override
    public SortedSet<Country> getCountries() {
        return new TreeSet<>(Set.of(
            Country.FRANCE,
            Country.RUSSIA,
            Country.TURKEY,
            Country.AUSTRIA,
            Country.ENGLAND,
            Country.ITALY,
            Country.GERMANY
        ));
    }

    @Override
    public Map<Location, Set<Location>> getMovementGraph() {
        return graphs.get(UnitType.FLEET);
    }

    @Override
    public Location parseLocation(String name) {
        return StandardVariantLocation.findByName(name);
    }

    @Override
    public Map<Location, Set<Location>> getMovementGraph(UnitType unitType) {
        return graphs.get(unitType);
    }

    @Override
    public long getStartingYear() {
        return 0;
    }
}
