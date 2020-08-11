package io.github.sroca3.diplomacy.maps;

import io.github.sroca3.diplomacy.Location;
import io.github.sroca3.diplomacy.UnitType;

import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SouthAmericanSupremacyLocation implements Location {

    AMAZONAS(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Ama"),
    ANGOSTURA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Ang", true),
    ANTOFAGASTA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Ant", true),
    AQUIRIS(new UnitType[]{UnitType.ARMY}, "Aqu"),
    ARAUNCANIAN(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Ara"),
    AREQUIPA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Are"),
    ARICA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Ari"),
    ASUNCION(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Asu", true),
    ATACAMA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Ata"),
    ATLANTIC_OCEAN(new UnitType[]{UnitType.FLEET}, "AtO"),
    BARINAS(new UnitType[]{UnitType.ARMY}, "Bar"),
    BUENOS_AIRES(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "BAs"),
    BAHIA_DE_ARICA(new UnitType[]{UnitType.FLEET}, "Bda"),
    BOCA_DE_NAVIOS(new UnitType[]{UnitType.FLEET}, "BdN"),
    BAHIA_DE_PARANAGUA(new UnitType[]{UnitType.FLEET}, "BdP"),
    BRITISH_GUYANA(new UnitType[]{}, "BGu"),
    BAHIA_BLANCA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "BhB"),
    BOGOTA(new UnitType[]{UnitType.ARMY}, "Bog"),
    BAHIA_SECHURA(new UnitType[]{UnitType.FLEET}, "BSc"),
    BAHIA_SAO_MARCOS(new UnitType[]{UnitType.FLEET}, "BSM"),
    BAHIA_DE_SAO_SEBASTAO(new UnitType[]{UnitType.FLEET}, "BSS"),
    BAHIA_DE_TODOS_OS_SANTOS(new UnitType[]{UnitType.FLEET}, "BTS"),
    CALI(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Cal"),
    CANELOS(new UnitType[]{UnitType.ARMY}, "Can"),
    CARTAGENA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Car"),
    CARACAS(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Crc"),
    CHACO(new UnitType[]{UnitType.ARMY}, "Cha"),
    CONCEPCION(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Con", true),
    COPIAGO(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Cop"),
    CORDOBA(new UnitType[]{UnitType.ARMY}, "Cor"),
    CORRIENTES(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Crt", true),
    CUMANA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Cum"),
    CUSCO(new UnitType[]{UnitType.ARMY}, "Cus"),
    DUTCH_GUYANA(new UnitType[]{}, "DGu"),
    DRAKE_PASSAGE(new UnitType[]{UnitType.FLEET}, "DrP"),
    FORMOSA(new UnitType[]{UnitType.ARMY}, "For"),
    FRENCH_GUYANA(new UnitType[]{}, "FGu"),
    ISLAS_GALAPAGOS(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Gal", true, false),
    GOLFO_DE_DARIEN(new UnitType[]{UnitType.FLEET}, "GDa"),
    GOLFO_DE_GUAFO(new UnitType[]{UnitType.FLEET}, "GdG"),
    GOIAS(new UnitType[]{UnitType.ARMY}, "Goi"),
    GUAPORE(new UnitType[]{UnitType.ARMY}, "Gpe"),
    GOLFO_DE_PANAMA(new UnitType[]{UnitType.FLEET}, "GPn"),
    GOLFO_DE_PARIA(new UnitType[]{UnitType.FLEET}, "GPr"),
    GOLFO_DE_SAN_JORGE(new UnitType[]{UnitType.FLEET}, "GSJ"),
    GUAYAQUIL(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Gua"),
    IQUITOS(new UnitType[]{UnitType.ARMY}, "Iqu"),
    ISLAS_JUAN_FERNANDEZ(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Jua", true, false),
    LA_PAZ(new UnitType[]{UnitType.ARMY}, "LaP"),
    LIMA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Lim"),
    LLANOS(new UnitType[]{UnitType.ARMY}, "Lla"),
    MATO_GROSSO(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "MaG"),
    ISLAS_MALVINAS(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Mal"),
    MANAUS(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Man"),
    MARACAIBO(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Mar"),
    MEDELLIN(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Med"),
    MENDOZA(new UnitType[]{UnitType.ARMY}, "Men"),
    MINAS_GERAIS(new UnitType[]{UnitType.ARMY}, "MiG"),
    MISIONES(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Mis"),
    MONTEVIDEO(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Mon"),
    MARANHAO(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Mrh"),
    NAZCA_SEA(new UnitType[]{UnitType.FLEET}, "NaS"),
    ORINOCO(new UnitType[]{UnitType.ARMY}, "Ori"),
    PANTANAL(new UnitType[]{UnitType.ARMY}, "Pan"),
    PARA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Par"),
    PUNTA_ARENAS(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "PAs"),
    PATAGONIA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Pat"),
    PERNAMBUCO(new UnitType[]{UnitType.ARMY}, "Per"),
    PIURA(new UnitType[]{UnitType.ARMY}, "Piu"),
    PANAMA(new UnitType[]{UnitType.ARMY}, "Pnm"),
    POPAYAN(new UnitType[]{UnitType.ARMY}, "Pop"),
    POTOSI(new UnitType[]{UnitType.ARMY}, "Pot"),
    PARANA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Prn"),
    QUITO(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Qui"),
    RIO_BRANCO(new UnitType[]{UnitType.ARMY}, "RBr"),
    RIO_DE_LA_PLATA(new UnitType[]{UnitType.FLEET}, "RdP"),
    RIBERALTA(new UnitType[]{UnitType.ARMY}, "Rib"),
    RIO_DE_JANEIRO(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Rio"),
    RIO_GRANDE_DO_SUL(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "RGS"),
    ROSARIO(new UnitType[]{UnitType.ARMY}, "Ros"),
    SANTA_FE(new UnitType[]{UnitType.ARMY}, "SaF"),
    SALTA(new UnitType[]{UnitType.ARMY}, "Sal"),
    SANTA_MARTA(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "SaM"),
    SANTIAGO(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "San"),
    SAO_PAULO(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "SaP"),
    SCOTIA_SEA(new UnitType[]{UnitType.ARMY}, "ScS"),
    SANTA_CRUZ(new UnitType[]{UnitType.ARMY}, "SCz"),
    SALVADOR(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Slv"),
    STRAITS_OF_MAGELLAN(new UnitType[]{UnitType.ARMY}, "SoM"),
    SOUTH_ATLANTIC_OCEAN(new UnitType[]{UnitType.FLEET}, "SAO"),
    SOUTH_PACIFIC_OCEAN(new UnitType[]{UnitType.ARMY}, "SPO"),
    TIERRA_DEL_FUEGO(new UnitType[]{}, "TdF"),
    TRUJILLO(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Tru"),
    SAN_MIGUEL_DE_TUCUMAN(new UnitType[]{UnitType.ARMY}, "Tuc"),
    VALPARAISO(new UnitType[]{UnitType.ARMY, UnitType.FLEET}, "Val"),
    VERAGUA(new UnitType[]{UnitType.ARMY}, "Ver");

    private static final Map<String, SouthAmericanSupremacyLocation> FULL_NAMES_MAPPING =
        EnumSet.allOf(SouthAmericanSupremacyLocation.class).stream()
               .collect(Collectors.toMap(s -> s.name(), Function.identity()));

    private static final Map<String, SouthAmericanSupremacyLocation> SHORT_NAME_MAPPINGS =
        EnumSet.allOf(SouthAmericanSupremacyLocation.class).stream()
               .collect(Collectors.toMap(s -> s.shortName.toUpperCase(Locale.ENGLISH), Function.identity()));

    private final Set<UnitType> unitTypes;
    private final String shortName;
    private final boolean supportsConvoy;
    private final boolean isSupplyCenter;

    SouthAmericanSupremacyLocation(UnitType[] unitTypes, String shortName) {
        this(unitTypes, shortName, false, unitTypes.length == 1 && unitTypes[0] == UnitType.FLEET);
    }

    SouthAmericanSupremacyLocation(UnitType[] unitTypes, String shortName, boolean isSupplyCenter) {
        this(unitTypes, shortName, isSupplyCenter, false);
    }

    SouthAmericanSupremacyLocation(UnitType[] unitTypes, String shortName, boolean isSupplyCenter, boolean supportsConvoy) {
        this.unitTypes = Set.of(unitTypes);
        this.shortName = shortName;
        this.isSupplyCenter = isSupplyCenter;
        this.supportsConvoy = supportsConvoy;
    }

    public static Location findByName(String name) {
        Location l = FULL_NAMES_MAPPING.get(name.toUpperCase(Locale.ENGLISH));
        if (l == null) {
            return findByShortName(name);
        }
        if (l == null){
            throw new IllegalArgumentException();
        }
        return l;
    }

    public static Location findByShortName(String name) {
        return SHORT_NAME_MAPPINGS.get(name);
    }

    @Override
    public Set<UnitType> getSupportedTypes() {
        return unitTypes;
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
}