package io.github.sroca3.diplomacy.maps;

import io.github.sroca3.diplomacy.Location;
import io.github.sroca3.diplomacy.UnitType;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum StandardVariantLocation implements Location {

    ADRIATIC_SEA(LocationType.SEA, "Adr"),
    AEGEAN_SEA(LocationType.SEA, "Aeg"),
    ALBANIA(LocationType.COASTAL_LAND, "Alb"),
    ANKARA(LocationType.COASTAL_LAND, "Ank", true),
    APULIA(LocationType.COASTAL_LAND, "Apu"),
    ARMENIA(LocationType.SEA, "Arm"),
    BALTIC_SEA(LocationType.SEA, "Bal"),
    BARENTS_SEA(LocationType.SEA, "Bar"),
    BELGIUM(LocationType.COASTAL_LAND, "Bel"),
    BERLIN(LocationType.COASTAL_LAND, "Ber", true),
    BLACK_SEA(LocationType.SEA, "Bla"),
    BOHEMIA(LocationType.SEA, "Boh"),
    BREST(LocationType.SEA, "Bre", true),
    BUDAPEST(LocationType.LAND, "Bud", true),
    BULGARIA_EC(LocationType.SEA, "Bul EC", true),
    BULGARIA_SC(LocationType.SEA, "Bul SC", true),
    BULGARIA(LocationType.COASTAL_LAND, "Bul", true, List.of(BULGARIA_EC, BULGARIA_SC), false),
    BURGUNDY(LocationType.COASTAL_LAND, "Bur"),
    CLYDE(LocationType.SEA, "Cly"),
    CONSTANTINOPLE(LocationType.COASTAL_LAND, "Con", true),
    DENMARK(LocationType.SEA, "Den"),
    EASTERN_MEDITERRANEAN(LocationType.SEA, "Eas"),
    EDINBURGH(LocationType.COASTAL_LAND, "Edi", true),
    ENGLISH_CHANNEL(LocationType.SEA, "ECh"),
    FINLAND(LocationType.COASTAL_LAND, "Fin"),
    GALICIA(LocationType.SEA, "Gal"),
    GASCONY(LocationType.COASTAL_LAND, "Gas"),
    GREECE(LocationType.COASTAL_LAND, "Gre", true),
    GULF_OF_BOTHNIA(LocationType.SEA, "GoB"),
    GULF_OF_LYON(LocationType.SEA, "GoL"),
    HELGOLAND_BIGHT(LocationType.SEA, "Hel"),
    HOLLAND(LocationType.COASTAL_LAND, "Hol", true),
    IONIAN_SEA(LocationType.SEA, "Ion"),
    IRISH_SEA(LocationType.SEA, "Iri"),
    KIEL(LocationType.COASTAL_LAND, "Kie", true),
    LIVERPOOL(LocationType.COASTAL_LAND, "Lvp", true),
    LIVONIA(LocationType.SEA, "Lvn"),
    LONDON(LocationType.COASTAL_LAND, "Lon", true),
    MARSEILLES(LocationType.COASTAL_LAND, "Mar", true),
    MID_ATLANTIC_OCEAN(LocationType.SEA, "MAO"),
    MOSCOW(LocationType.LAND, "Mos", true),
    MUNICH(LocationType.LAND, "Mun", true),
    NAPLES(LocationType.SEA, "Nap", true),
    NORTH_AFRICA(LocationType.SEA, "NAf"),
    NORTH_ATLANTIC_OCEAN(LocationType.SEA, "NAO"),
    NORTH_SEA(LocationType.SEA, "Nth"),
    NORWAY(LocationType.SEA, "Nor", true),
    NORWEGIAN_SEA(LocationType.SEA, "Nwg"),
    PARIS(LocationType.LAND, "Par", true),
    PICARDY(LocationType.SEA, "Pic"),
    PIEDMONT(LocationType.SEA, "Pie"),
    PORTUGAL(LocationType.SEA, "Por", true),
    PRUSSIA(LocationType.COASTAL_LAND, "Pru"),
    ROME(LocationType.COASTAL_LAND, "Rom", true),
    RUHR(LocationType.LAND, "Ruh"),
    RUMANIA(LocationType.SEA, "Rum", true),
    SERBIA(LocationType.COASTAL_LAND, "Ser", true),
    SEVASTOPOL(LocationType.SEA, "Sev", true),
    SILESIA(LocationType.COASTAL_LAND, "Sil"),
    SKAGERRAK(LocationType.SEA, "Ska"),
    SMYRNA(LocationType.COASTAL_LAND, "Smy", true),
    SPAIN_NC(LocationType.SEA, "Spa NC", true),
    SPAIN_SC(LocationType.SEA, "Spa SC", true),
    SPAIN(LocationType.COASTAL_LAND, "Spa", true, List.of(SPAIN_NC, SPAIN_SC), false),
    ST_PETERSBURG_NC(LocationType.SEA, "StP NC", true),
    ST_PETERSBURG_SC(LocationType.SEA, "StP SC", true),
    ST_PETERSBURG(LocationType.SEA, "StP", true, List.of(ST_PETERSBURG_NC, ST_PETERSBURG_SC), false),
    SWEDEN(LocationType.COASTAL_LAND, "Swe"),
    SYRIA(LocationType.SEA, "Syr"),
    TRIESTE(LocationType.COASTAL_LAND, "Tri", true),
    TUNIS(LocationType.SEA, "Tun", true),
    TUSCANY(LocationType.SEA, "Tus"),
    TYROLIA(LocationType.COASTAL_LAND, "Tyr"),
    TYRRHENIAN_SEA(LocationType.SEA, "TyS"),
    UKRAINE(LocationType.SEA, "Ukr"),
    VENICE(LocationType.COASTAL_LAND, "Ven", true),
    VIENNA(LocationType.COASTAL_LAND, "Vie", true),
    WALES(LocationType.COASTAL_LAND, "Wal"),
    WARSAW(LocationType.COASTAL_LAND, "War", true),
    WESTERN_MEDITERRANEAN(LocationType.SEA, "Wes"),
    YORKSHIRE(LocationType.COASTAL_LAND, "Yor");

    private static final Map<String, StandardVariantLocation> FULL_NAMES_MAPPING =
        EnumSet.allOf(StandardVariantLocation.class).stream()
               .collect(Collectors.toMap(s -> s.name(), Function.identity()));
    private static final Map<String, StandardVariantLocation> SHORT_NAME_MAPPINGS =
        EnumSet.allOf(StandardVariantLocation.class).stream()
               .collect(Collectors.toMap(s -> s.shortName.toUpperCase(Locale.ENGLISH), Function.identity()));
    private static final Map<Location, Location> coastToParent = new HashMap<>();

    static {
        EnumSet.allOf(StandardVariantLocation.class).stream()
               .filter(StandardVariantLocation::hasCoasts)
               .forEach(location -> {
                   location.getCoasts().forEach(coast -> coastToParent.put(coast, location));
               });
    }

    private final LocationType locationType;
    private final String shortName;
    private final boolean isSupplyCenter;
    private final boolean supportsConvoy;
    private final List<Location> coasts;

    StandardVariantLocation(LocationType locationType, String shortName) {
        this(
            locationType,
            shortName,
            false,
            Collections.emptyList(),
            locationType.supportsConvoy()
        );
    }

    StandardVariantLocation(LocationType locationType, String shortName, boolean isSupplyCenter) {
        this(locationType, shortName, isSupplyCenter, Collections.emptyList(), false);
    }

    StandardVariantLocation(
        LocationType locationType,
        String shortName,
        boolean isSupplyCenter,
        List<Location> coasts,
        boolean supportsConvoy
    ) {
        this.locationType = locationType;
        this.shortName = shortName;
        this.coasts = coasts;
        this.isSupplyCenter = isSupplyCenter;
        this.supportsConvoy = supportsConvoy;
    }

    public static Location findByName(String name) {
        if (null == name || "".equals(name)) {
            return null;
        }
        Location l = FULL_NAMES_MAPPING.get(name.toUpperCase(Locale.ENGLISH));
        if (l == null) {
            return findByShortName(name);
        }
        if (l == null) {
            throw new IllegalArgumentException();
        }
        return l;
    }

    public static Location findByShortName(String name) {
        return SHORT_NAME_MAPPINGS.get(name);
    }

    @Override
    public boolean supports(UnitType unitType) {
        return locationType.supports(unitType);
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public boolean isSupplyCenter() {
        return isSupplyCenter;
    }

    @Override
    public boolean hasCoasts() {
        return !coasts.isEmpty();
    }

    @Override
    public List<Location> getCoasts() {
        return coasts;
    }

    @Override
    public boolean isCoast() {
        return coastToParent.containsKey(this);
    }

    @Override
    public @Nonnull Location getTerritory() {
        if (isCoast()) {
            return coastToParent.get(this);
        } else  {
            return this;
        }
    }
}
