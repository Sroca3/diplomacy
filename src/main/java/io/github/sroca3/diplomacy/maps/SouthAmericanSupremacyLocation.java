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
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SouthAmericanSupremacyLocation implements Location {

    AMAZONAS(LocationType.COASTAL_LAND, "Ama"),
    ANGOSTURA(LocationType.COASTAL_LAND, "Ang", true),
    ANTOFAGASTA(LocationType.COASTAL_LAND, "Ant", true),
    AQUIRIS(LocationType.LAND, "Aqu", true),
    ARAUNCANIAN(LocationType.COASTAL_LAND, "Ara"),
    AREQUIPA(LocationType.COASTAL_LAND, "Are"),
    ARICA(LocationType.COASTAL_LAND, "Ari", true),
    ASUNCION(LocationType.COASTAL_LAND, "Asu", true),
    ATACAMA(LocationType.COASTAL_LAND, "Ata"),
    ATLANTIC_OCEAN(LocationType.SEA, "AtO"),
    BARINAS(LocationType.LAND, "Bar"),
    BUENOS_AIRES(LocationType.COASTAL_LAND, "BAs", true),
    BAHIA_DE_ARICA(LocationType.SEA, "Bda"),
    BOCA_DE_NAVIOS(LocationType.SEA, "BdN"),
    BAHIA_DE_PARANAGUA(LocationType.SEA, "BdP"),
    BRITISH_GUYANA(LocationType.IMPASSABLE, "BGu"),
    BAHIA_BLANCA(LocationType.COASTAL_LAND, "BhB"),
    BOGOTA(LocationType.LAND, "Bog", true),
    BAHIA_SECHURA(LocationType.SEA, "BSc"),
    BAHIA_SAO_MARCOS(LocationType.SEA, "BSM"),
    BAHIA_DE_SAO_SEBASTAO(LocationType.SEA, "BSS"),
    BAHIA_DE_TODOS_OS_SANTOS(LocationType.SEA, "BTS"),
    CALI(LocationType.COASTAL_LAND, "Cal", true),
    CANELOS(LocationType.LAND, "Can"),
    CARTAGENA(LocationType.COASTAL_LAND, "Car", true),
    CARACAS(LocationType.COASTAL_LAND, "Crc", true),
    CHACO(LocationType.LAND, "Cha"),
    CONCEPCION(LocationType.COASTAL_LAND, "Con", true),
    COPIAPO(LocationType.COASTAL_LAND, "Cop"),
    CORDOBA(LocationType.LAND, "Cor", true),
    CORRIENTES(LocationType.COASTAL_LAND, "Crt", true),
    CUMANA(LocationType.COASTAL_LAND, "Cum", true),
    CUSCO(LocationType.LAND, "Cus"),
    DUTCH_GUYANA(LocationType.IMPASSABLE, "DGu"),
    DRAKE_PASSAGE(LocationType.SEA, "DrP"),
    FORMOSA(LocationType.LAND, "For"),
    FRENCH_GUYANA(LocationType.IMPASSABLE, "FGu"),
    ISLAS_GALAPAGOS(LocationType.COASTAL_LAND, "Gal", true, false),
    GOLFO_DE_DARIEN(LocationType.SEA, "GDa"),
    GOLFO_DE_GUAFO(LocationType.SEA, "GdG"),
    GOIAS(LocationType.LAND, "Goi"),
    GUAPORE(LocationType.LAND, "Gpe"),
    GOLFO_DE_PANAMA(LocationType.SEA, "GPn"),
    GOLFO_DE_PARIA(LocationType.SEA, "GPr"),
    GOLFO_DE_SAN_JORGE(LocationType.SEA, "GSJ"),
    GUAYAQUIL(LocationType.COASTAL_LAND, "Gua"),
    IQUITOS(LocationType.LAND, "Iqu", true),
    ISLAS_JUAN_FERNANDEZ(LocationType.COASTAL_LAND, "Jua", true, false),
    LA_PAZ(LocationType.LAND, "LaP", true),
    LIMA(LocationType.COASTAL_LAND, "Lim", true),
    LLANOS(LocationType.LAND, "Lla"),
    MATO_GROSSO(LocationType.COASTAL_LAND, "MaG"),
    ISLAS_MALVINAS(LocationType.COASTAL_LAND, "Mal", true),
    MANAUS(LocationType.COASTAL_LAND, "Man", true),
    MARACAIBO(LocationType.COASTAL_LAND, "Mar", true),
    MEDELLIN(LocationType.COASTAL_LAND, "Med"),
    MENDOZA(LocationType.LAND, "Men"),
    MINAS_GERAIS(LocationType.LAND, "MiG"),
    MISIONES(LocationType.COASTAL_LAND, "Mis"),
    MONTEVIDEO(LocationType.COASTAL_LAND, "Mon", true),
    MARANHAO(LocationType.COASTAL_LAND, "Mrh", true),
    NAZCA_SEA(LocationType.SEA, "NaS"),
    ORINOCO(LocationType.LAND, "Ori"),
    PANTANAL(LocationType.LAND, "Pan"),
    PARA(LocationType.COASTAL_LAND, "Par"),
    PUNTA_ARENAS(LocationType.COASTAL_LAND, "PAs", true),
    PATAGONIA(LocationType.COASTAL_LAND, "Pat", true),
    PERNAMBUCO(LocationType.LAND, "Per"),
    PIURA(LocationType.COASTAL_LAND, "Piu"),
    PANAMA_NC(LocationType.COAST, "Pnm NC"),
    PANAMA_SC(LocationType.COAST, "Pnm SC"),
    PANAMA(LocationType.LAND, "Pnm", true, false, List.of(PANAMA_NC, PANAMA_SC)),
    POPAYAN(LocationType.LAND, "Pop"),
    POTOSI(LocationType.LAND, "Pot", true),
    PARANA(LocationType.COASTAL_LAND, "Prn", true),
    QUITO(LocationType.COASTAL_LAND, "Qui", true),
    RIO_BRANCO(LocationType.LAND, "RBr"),
    RIO_DE_LA_PLATA(LocationType.SEA, "RdP"),
    RIBERALTA(LocationType.LAND, "Rib"),
    RIO_DE_JANEIRO(LocationType.COASTAL_LAND, "Rio", true),
    RIO_GRANDE_DO_SUL(LocationType.COASTAL_LAND, "RGS", true),
    ROSARIO(LocationType.LAND, "Ros"),
    SANTA_FE(LocationType.LAND, "SaF"),
    SALTA(LocationType.LAND, "Sal", true),
    SANTA_MARTA(LocationType.COASTAL_LAND, "SaM"),
    SANTIAGO(LocationType.COASTAL_LAND, "San", true),
    SAO_PAULO(LocationType.COASTAL_LAND, "SaP", true),
    SCOTIA_SEA(LocationType.LAND, "ScS"),
    SANTA_CRUZ(LocationType.LAND, "SCz", true),
    SALVADOR(LocationType.COASTAL_LAND, "Slv", true),
    STRAITS_OF_MAGELLAN(LocationType.SEA, "SoM"),
    SOUTH_ATLANTIC_OCEAN(LocationType.SEA, "SAO"),
    SOUTH_PACIFIC_OCEAN(LocationType.LAND, "SPO"),
    TIERRA_DEL_FUEGO(LocationType.IMPASSABLE, "TdF"),
    TRUJILLO(LocationType.COASTAL_LAND, "Tru", true),
    SAN_MIGUEL_DE_TUCUMAN(LocationType.LAND, "Tuc", true),
    VALPARAISO(LocationType.COASTAL_LAND, "Val", true),
    VERAGUA_NC(LocationType.SEA, "Ver NC"),
    VERAGUA_SC(LocationType.SEA, "Ver SC"),
    VERAGUA(LocationType.LAND, "Ver", false, false, List.of(VERAGUA_NC, VERAGUA_SC));

    private static final Map<Location, Location> coastToParent = new HashMap<>();

    static {
        EnumSet.allOf(SouthAmericanSupremacyLocation.class).stream()
               .filter(SouthAmericanSupremacyLocation::hasCoasts)
               .forEach(location -> {
                   location.getCoasts().forEach(coast -> coastToParent.put(coast, location));
               });
    }

    private static final Map<String, SouthAmericanSupremacyLocation> FULL_NAMES_MAPPING =
        EnumSet.allOf(SouthAmericanSupremacyLocation.class).stream()
               .collect(Collectors.toMap(s -> s.name(), Function.identity()));

    private static final Map<String, SouthAmericanSupremacyLocation> SHORT_NAME_MAPPINGS =
        EnumSet.allOf(SouthAmericanSupremacyLocation.class).stream()
               .collect(Collectors.toMap(s -> s.shortName.toUpperCase(Locale.ENGLISH), Function.identity()));

    private final LocationType locationType;
    private final String shortName;
    private final boolean supportsConvoy;
    private final boolean isSupplyCenter;
    private final List<Location> coasts;

    SouthAmericanSupremacyLocation(LocationType locationType, String shortName) {
        this(
            locationType,
            shortName,
            false,
            locationType.supportsConvoy(),
            Collections.emptyList()
        );
    }

    SouthAmericanSupremacyLocation(LocationType locationType, String shortName, boolean isSupplyCenter) {
        this(locationType, shortName, isSupplyCenter, false, Collections.emptyList());
    }

    SouthAmericanSupremacyLocation(
        LocationType locationType,
        String shortName,
        boolean isSupplyCenter,
        boolean supportsConvoy
    ) {
        this(locationType, shortName, isSupplyCenter, supportsConvoy, Collections.emptyList());
    }

    SouthAmericanSupremacyLocation(
        LocationType locationType,
        String shortName,
        boolean isSupplyCenter,
        boolean supportsConvoy,
        List<Location> coasts
    ) {
        this.locationType = locationType;
        this.shortName = shortName;
        this.isSupplyCenter = isSupplyCenter;
        this.supportsConvoy = supportsConvoy;
        this.coasts = coasts;
    }

    public static Location findByName(String name) {
        Location l = FULL_NAMES_MAPPING.get(Optional.ofNullable(name).orElse("").toUpperCase(Locale.ENGLISH));
        if (l == null) {
            return findByShortName(name);
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

    public String getShortName() {
        return shortName;
    }

    @Override
    public boolean isCoast() {
        return locationType.equals(LocationType.COAST);
    }

    @Override
    public List<Location> getCoasts() {
        return coasts;
    }

    @Override
    public @Nonnull
    Location getTerritory() {
        if (isCoast()) {
            return coastToParent.get(this);
        } else  {
            return this;
        }
    }
}