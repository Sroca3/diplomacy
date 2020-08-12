package io.github.sroca3.diplomacy.maps;

import io.github.sroca3.diplomacy.Location;
import io.github.sroca3.diplomacy.Unit;
import io.github.sroca3.diplomacy.UnitType;

import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum StandardVariantLocation implements Location {

    ADRIATIC_SEA(new UnitType[]{UnitType.FLEET}, "Adr"),
    AEGEAN_SEA(new UnitType[]{UnitType.FLEET}, "Aeg"),
    ALBANY(new UnitType[]{UnitType.FLEET, UnitType.ARMY}, "Alb"),
    ANKARA(new UnitType[]{UnitType.FLEET}, "Ank", true),
    APULIA(new UnitType[]{UnitType.FLEET, UnitType.ARMY}, "Apu"),
    ARMENIA(new UnitType[]{UnitType.FLEET}, "Arm"),
    BALTIC_SEA(new UnitType[]{UnitType.FLEET}, "Bal"),
    BARENTS_SEA(new UnitType[]{UnitType.FLEET}, "Bar"),
    BELGIUM(new UnitType[]{UnitType.FLEET}, "Bel"),
    BERLIN(new UnitType[]{UnitType.FLEET}, "Ber", true),
    BLACK_SEA(new UnitType[]{UnitType.FLEET}, "Bla"),
    BOHEMIA(new UnitType[]{UnitType.FLEET}, "Boh"),
    BREST(new UnitType[]{UnitType.FLEET}, "Bre", true),
    BUDAPEST(new UnitType[]{UnitType.FLEET}, "Bud", true),
    BULGARIA(new UnitType[]{UnitType.FLEET}, "Bul", true),
    BULGARIA_EC(new UnitType[]{UnitType.FLEET}, "Bul EC", true),
    BULGARIA_SC(new UnitType[]{UnitType.FLEET}, "Bul SC", true),
    BURGUNDY(new UnitType[]{UnitType.FLEET}, "Bur"),
    CLYDE(new UnitType[]{UnitType.FLEET}, "Cly"),
    CONSTANTINOPLE(new UnitType[]{UnitType.FLEET}, "Con", true),
    DENMARK(new UnitType[]{UnitType.FLEET}, "Den"),
    EASTERN_MEDITERRANEAN(new UnitType[]{UnitType.FLEET}, "Eas"),
    EDINBURGH(new UnitType[]{UnitType.FLEET}, "Edi", true),
    ENGLISH_CHANNEL(new UnitType[]{UnitType.FLEET}, "ECh"),
    FINLAND(new UnitType[]{UnitType.FLEET}, "Fin"),
    GALICIA(new UnitType[]{UnitType.FLEET}, "Gal"),
    GASCONY(new UnitType[]{UnitType.FLEET}, "Gas"),
    GREECE(new UnitType[]{UnitType.FLEET}, "Gre", true),
    GULF_OF_BOTHNIA(new UnitType[]{UnitType.FLEET}, "GoB"),
    GULF_OF_LYON(new UnitType[]{UnitType.FLEET}, "GoL"),
    HELGOLAND_BIGHT(new UnitType[]{UnitType.FLEET}, "Hel"),
    HOLLAND(new UnitType[]{UnitType.FLEET}, "Hol"),
    IONIAN_SEA(new UnitType[]{UnitType.FLEET}, "Ion"),
    IRISH_SEA(new UnitType[]{UnitType.FLEET}, "Iri"),
    KIEL(new UnitType[]{UnitType.FLEET}, "Kie", true),
    LIVERPOOL(new UnitType[]{UnitType.FLEET, UnitType.ARMY}, "Lvp", true),
    LIVONIA(new UnitType[]{UnitType.FLEET}, "Lvn"),
    LONDON(new UnitType[]{UnitType.FLEET, UnitType.ARMY}, "Lon", true),
    MARSEILLES(new UnitType[]{UnitType.FLEET}, "Mar", true),
    MID_ATLANTIC_OCEAN(new UnitType[]{UnitType.FLEET}, "MAO"),
    MOSCOW(new UnitType[]{UnitType.FLEET}, "Mos"),
    MUNICH(new UnitType[]{UnitType.FLEET, UnitType.ARMY}, "Mun", true),
    NAPLES(new UnitType[]{UnitType.FLEET}, "Nap", true),
    NORTH_AFRICA(new UnitType[]{UnitType.FLEET}, "NAf"),
    NORTH_ATLANTIC_OCEAN(new UnitType[]{UnitType.FLEET}, "NAO"),
    NORTH_SEA(new UnitType[]{UnitType.FLEET}, "Nth"),
    NORWAY(new UnitType[]{UnitType.FLEET}, "Nor", true),
    NORWEGIAN_SEA(new UnitType[]{UnitType.FLEET}, "Nwg"),
    PARIS(new UnitType[]{UnitType.ARMY}, "Par", true),
    PICARDY(new UnitType[]{UnitType.FLEET}, "Pic"),
    PIEDMONT(new UnitType[]{UnitType.FLEET}, "Pie"),
    PORTUGAL(new UnitType[]{UnitType.FLEET}, "Por", true),
    PRUSSIA(new UnitType[]{UnitType.FLEET}, "Pru"),
    ROME(new UnitType[]{UnitType.FLEET, UnitType.ARMY}, "Rom", true),
    RUHR(new UnitType[]{UnitType.FLEET}, "Ruh"),
    RUMANIA(new UnitType[]{UnitType.FLEET}, "Rum", true),
    SERBIA(new UnitType[]{UnitType.FLEET}, "Ser", true),
    SEVASTOPOL(new UnitType[]{UnitType.FLEET}, "Sev", true),
    SILESIA(new UnitType[]{UnitType.FLEET}, "Sil"),
    SKAGERRAK(new UnitType[]{UnitType.FLEET}, "Ska"),
    SMYRNA(new UnitType[]{UnitType.FLEET}, "Smy", true),
    SPAIN(new UnitType[]{UnitType.FLEET}, "Spa", true),
    SPAIN_NC(new UnitType[]{UnitType.FLEET}, "Spa NC", true),
    SPAIN_SC(new UnitType[]{UnitType.FLEET}, "Spa SC", true),
    ST_PETERSBURG(new UnitType[]{UnitType.FLEET}, "StP", true),
    ST_PETERSBURG_NC(new UnitType[]{UnitType.FLEET}, "StP NC", true),
    ST_PETERSBURG_SC(new UnitType[]{UnitType.FLEET}, "StP SC", true),
    SWEDEN(new UnitType[]{UnitType.FLEET}, "Swe"),
    SYRIA(new UnitType[]{UnitType.FLEET}, "Syr"),
    TRIESTE(new UnitType[]{UnitType.FLEET, UnitType.ARMY}, "Tri", true),
    TUNIS(new UnitType[]{UnitType.FLEET}, "Tun", true),
    TUSCANY(new UnitType[]{UnitType.FLEET}, "Tus"),
    TYROLIA(new UnitType[]{UnitType.FLEET, UnitType.ARMY}, "Tyr"),
    TYRRHENIAN_SEA(new UnitType[]{UnitType.FLEET}, "TyS"),
    UKRAINE(new UnitType[]{UnitType.FLEET}, "Ukr"),
    VENICE(new UnitType[]{UnitType.FLEET, UnitType.ARMY}, "Ven", true),
    VIENNA(new UnitType[]{UnitType.FLEET, UnitType.ARMY}, "Vie", true),
    WALES(new UnitType[]{UnitType.FLEET, UnitType.ARMY}, "Wal"),
    WARSAW(new UnitType[]{UnitType.FLEET}, "War", true),
    WESTERN_MEDITERRANEAN(new UnitType[]{UnitType.FLEET}, "Wes"),
    YORKSHIRE(new UnitType[]{UnitType.FLEET, UnitType.ARMY}, "Yor");


    private static final Map<String, StandardVariantLocation> FULL_NAMES_MAPPING =
        EnumSet.allOf(StandardVariantLocation.class).stream()
               .collect(Collectors.toMap(s -> s.name(), Function.identity()));
    private static final Map<String, StandardVariantLocation> SHORT_NAME_MAPPINGS =
        EnumSet.allOf(StandardVariantLocation.class).stream()
               .collect(Collectors.toMap(s -> s.shortName.toUpperCase(Locale.ENGLISH), Function.identity()));
    private final Set<UnitType> unitTypes;
    private final String shortName;
    private final boolean isSupplyCenter;
    private final boolean supportsConvoy;

    StandardVariantLocation(UnitType[] unitTypes, String shortName) {
        this(unitTypes, shortName, false, unitTypes.length == 1 && unitTypes[0] == UnitType.FLEET);
    }

    StandardVariantLocation(UnitType[] unitTypes, String shortName, boolean isSupplyCenter) {
        this(unitTypes, shortName, isSupplyCenter, false);
    }

    StandardVariantLocation(UnitType[] unitTypes, String shortName, boolean isSupplyCenter, boolean supportsConvoy) {
        this.unitTypes = Set.of(unitTypes);
        this.shortName = shortName;
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
    public Set<UnitType> getSupportedTypes() {
        return this.unitTypes;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isSupplyCenter() {
        return false;
    }
}
