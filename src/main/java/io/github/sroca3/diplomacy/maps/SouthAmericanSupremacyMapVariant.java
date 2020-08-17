package io.github.sroca3.diplomacy.maps;

import io.github.sroca3.diplomacy.Country;
import io.github.sroca3.diplomacy.Location;
import io.github.sroca3.diplomacy.MapVariant;
import io.github.sroca3.diplomacy.UnitType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SouthAmericanSupremacyMapVariant implements MapVariant {
    private static final Map<Location, Set<Location>> armyGraph = new HashMap<>();
    private static final Map<Location, Set<Location>> fleetGraph = new HashMap<>();
    private static final SouthAmericanSupremacyMapVariant INSTANCE = new SouthAmericanSupremacyMapVariant();

    private SouthAmericanSupremacyMapVariant() {
        Arrays.stream(SouthAmericanSupremacyLocation.values()).forEach(l -> addEdge(armyGraph, l, l));
        Arrays.stream(SouthAmericanSupremacyLocation.values()).forEach(l -> addEdge(fleetGraph, l, l));

        addEdge(armyGraph, SouthAmericanSupremacyLocation.AMAZONAS, SouthAmericanSupremacyLocation.POPAYAN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AMAZONAS, SouthAmericanSupremacyLocation.RIO_BRANCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AMAZONAS, SouthAmericanSupremacyLocation.IQUITOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AMAZONAS, SouthAmericanSupremacyLocation.MANAUS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AMAZONAS, SouthAmericanSupremacyLocation.AQUIRIS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AMAZONAS, SouthAmericanSupremacyLocation.LLANOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AMAZONAS, SouthAmericanSupremacyLocation.ORINOCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AMAZONAS, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PUNTA_ARENAS, SouthAmericanSupremacyLocation.ARAUNCANIAN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CORDOBA, SouthAmericanSupremacyLocation.ROSARIO);
        addEdge(
            armyGraph,
            SouthAmericanSupremacyLocation.CORDOBA,
            SouthAmericanSupremacyLocation.SAN_MIGUEL_DE_TUCUMAN
        );
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CORDOBA, SouthAmericanSupremacyLocation.MENDOZA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.COPIAGO, SouthAmericanSupremacyLocation.VALPARAISO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.COPIAGO, SouthAmericanSupremacyLocation.ANTOFAGASTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CORRIENTES, SouthAmericanSupremacyLocation.ASUNCION);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CORRIENTES, SouthAmericanSupremacyLocation.FORMOSA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CORRIENTES, SouthAmericanSupremacyLocation.MISIONES);
        addEdge(
            armyGraph,
            SouthAmericanSupremacyLocation.CORRIENTES,
            SouthAmericanSupremacyLocation.SAN_MIGUEL_DE_TUCUMAN
        );
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CORRIENTES, SouthAmericanSupremacyLocation.SANTA_FE);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CUSCO, SouthAmericanSupremacyLocation.LA_PAZ);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CUSCO, SouthAmericanSupremacyLocation.RIBERALTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CUSCO, SouthAmericanSupremacyLocation.IQUITOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CUSCO, SouthAmericanSupremacyLocation.LIMA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CUSCO, SouthAmericanSupremacyLocation.AQUIRIS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GUAPORE, SouthAmericanSupremacyLocation.RIBERALTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GUAPORE, SouthAmericanSupremacyLocation.SANTA_CRUZ);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GUAPORE, SouthAmericanSupremacyLocation.AQUIRIS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GUAPORE, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PERNAMBUCO, SouthAmericanSupremacyLocation.SALVADOR);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PERNAMBUCO, SouthAmericanSupremacyLocation.MARANHAO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PERNAMBUCO, SouthAmericanSupremacyLocation.GOIAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PARANA, SouthAmericanSupremacyLocation.ASUNCION);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PARANA, SouthAmericanSupremacyLocation.CONCEPCION);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PARANA, SouthAmericanSupremacyLocation.MISIONES);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PARANA, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PARANA, SouthAmericanSupremacyLocation.PANTANAL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO, SouthAmericanSupremacyLocation.SALVADOR);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO, SouthAmericanSupremacyLocation.MINAS_GERAIS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO, SouthAmericanSupremacyLocation.SAO_PAULO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL, SouthAmericanSupremacyLocation.PARANA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL, SouthAmericanSupremacyLocation.SAO_PAULO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL, SouthAmericanSupremacyLocation.MISIONES);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL, SouthAmericanSupremacyLocation.MONTEVIDEO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_CRUZ, SouthAmericanSupremacyLocation.LA_PAZ);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_CRUZ, SouthAmericanSupremacyLocation.RIBERALTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_CRUZ, SouthAmericanSupremacyLocation.GUAPORE);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_CRUZ, SouthAmericanSupremacyLocation.POTOSI);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_CRUZ, SouthAmericanSupremacyLocation.CHACO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_CRUZ, SouthAmericanSupremacyLocation.PANTANAL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_CRUZ, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ASUNCION, SouthAmericanSupremacyLocation.CONCEPCION);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ASUNCION, SouthAmericanSupremacyLocation.FORMOSA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ASUNCION, SouthAmericanSupremacyLocation.CORRIENTES);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ASUNCION, SouthAmericanSupremacyLocation.PARANA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ASUNCION, SouthAmericanSupremacyLocation.MISIONES);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.QUITO, SouthAmericanSupremacyLocation.CALI);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.QUITO, SouthAmericanSupremacyLocation.GUAYAQUIL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SALVADOR, SouthAmericanSupremacyLocation.MINAS_GERAIS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SALVADOR, SouthAmericanSupremacyLocation.GOIAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SALVADOR, SouthAmericanSupremacyLocation.PERNAMBUCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SALVADOR, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.POTOSI, SouthAmericanSupremacyLocation.LA_PAZ);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.POTOSI, SouthAmericanSupremacyLocation.SALTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.POTOSI, SouthAmericanSupremacyLocation.FORMOSA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.POTOSI, SouthAmericanSupremacyLocation.CHACO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.POTOSI, SouthAmericanSupremacyLocation.SANTA_CRUZ);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.POTOSI, SouthAmericanSupremacyLocation.ANTOFAGASTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BAHIA_BLANCA, SouthAmericanSupremacyLocation.PATAGONIA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BAHIA_BLANCA, SouthAmericanSupremacyLocation.ROSARIO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BAHIA_BLANCA, SouthAmericanSupremacyLocation.MENDOZA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_MARTA, SouthAmericanSupremacyLocation.BOGOTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_MARTA, SouthAmericanSupremacyLocation.MARACAIBO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_MARTA, SouthAmericanSupremacyLocation.LLANOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_MARTA, SouthAmericanSupremacyLocation.CARTAGENA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ROSARIO, SouthAmericanSupremacyLocation.CORDOBA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ROSARIO, SouthAmericanSupremacyLocation.BAHIA_BLANCA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ROSARIO, SouthAmericanSupremacyLocation.SANTA_FE);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ROSARIO, SouthAmericanSupremacyLocation.MENDOZA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ROSARIO, SouthAmericanSupremacyLocation.BUENOS_AIRES);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.TRUJILLO, SouthAmericanSupremacyLocation.IQUITOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.TRUJILLO, SouthAmericanSupremacyLocation.LIMA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.TRUJILLO, SouthAmericanSupremacyLocation.PIURA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.PARA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.AMAZONAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.MARANHAO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.GUAPORE);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.GOIAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.MANAUS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.SANTA_CRUZ);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.AQUIRIS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.PANTANAL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PANAMA, SouthAmericanSupremacyLocation.VERAGUA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PANAMA, SouthAmericanSupremacyLocation.MEDELLIN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PANAMA, SouthAmericanSupremacyLocation.CARTAGENA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTIAGO, SouthAmericanSupremacyLocation.VALPARAISO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTIAGO, SouthAmericanSupremacyLocation.ARAUNCANIAN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTIAGO, SouthAmericanSupremacyLocation.MENDOZA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ANGOSTURA, SouthAmericanSupremacyLocation.CUMANA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ANGOSTURA, SouthAmericanSupremacyLocation.CARACAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ANGOSTURA, SouthAmericanSupremacyLocation.RIO_BRANCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SAO_PAULO, SouthAmericanSupremacyLocation.MINAS_GERAIS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SAO_PAULO, SouthAmericanSupremacyLocation.GOIAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SAO_PAULO, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SAO_PAULO, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SAO_PAULO, SouthAmericanSupremacyLocation.PANTANAL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AQUIRIS, SouthAmericanSupremacyLocation.AMAZONAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AQUIRIS, SouthAmericanSupremacyLocation.RIBERALTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AQUIRIS, SouthAmericanSupremacyLocation.IQUITOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AQUIRIS, SouthAmericanSupremacyLocation.CUSCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AQUIRIS, SouthAmericanSupremacyLocation.GUAPORE);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AQUIRIS, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GUAYAQUIL, SouthAmericanSupremacyLocation.QUITO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GUAYAQUIL, SouthAmericanSupremacyLocation.CANELOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GUAYAQUIL, SouthAmericanSupremacyLocation.PIURA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MONTEVIDEO, SouthAmericanSupremacyLocation.MISIONES);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MONTEVIDEO, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MONTEVIDEO, SouthAmericanSupremacyLocation.SANTA_FE);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MONTEVIDEO, SouthAmericanSupremacyLocation.BUENOS_AIRES);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CARACAS, SouthAmericanSupremacyLocation.MARACAIBO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CARACAS, SouthAmericanSupremacyLocation.CUMANA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CARACAS, SouthAmericanSupremacyLocation.ANGOSTURA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CARACAS, SouthAmericanSupremacyLocation.BARINAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LA_PAZ, SouthAmericanSupremacyLocation.RIBERALTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LA_PAZ, SouthAmericanSupremacyLocation.CUSCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LA_PAZ, SouthAmericanSupremacyLocation.POTOSI);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LA_PAZ, SouthAmericanSupremacyLocation.SANTA_CRUZ);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIBERALTA, SouthAmericanSupremacyLocation.LA_PAZ);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIBERALTA, SouthAmericanSupremacyLocation.GUAPORE);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIBERALTA, SouthAmericanSupremacyLocation.CUSCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIBERALTA, SouthAmericanSupremacyLocation.SANTA_CRUZ);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIBERALTA, SouthAmericanSupremacyLocation.AQUIRIS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PATAGONIA, SouthAmericanSupremacyLocation.BAHIA_BLANCA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PATAGONIA, SouthAmericanSupremacyLocation.ARAUNCANIAN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CANELOS, SouthAmericanSupremacyLocation.POPAYAN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CANELOS, SouthAmericanSupremacyLocation.CALI);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CANELOS, SouthAmericanSupremacyLocation.GUAYAQUIL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIO_BRANCO, SouthAmericanSupremacyLocation.PARA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIO_BRANCO, SouthAmericanSupremacyLocation.AMAZONAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIO_BRANCO, SouthAmericanSupremacyLocation.MANAUS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.RIO_BRANCO, SouthAmericanSupremacyLocation.ANGOSTURA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.FORMOSA, SouthAmericanSupremacyLocation.ASUNCION);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.FORMOSA, SouthAmericanSupremacyLocation.CONCEPCION);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.FORMOSA, SouthAmericanSupremacyLocation.SALTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.FORMOSA, SouthAmericanSupremacyLocation.POTOSI);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.FORMOSA, SouthAmericanSupremacyLocation.CORRIENTES);
        addEdge(
            armyGraph,
            SouthAmericanSupremacyLocation.FORMOSA,
            SouthAmericanSupremacyLocation.SAN_MIGUEL_DE_TUCUMAN
        );
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MANAUS, SouthAmericanSupremacyLocation.PARA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MANAUS, SouthAmericanSupremacyLocation.AMAZONAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MANAUS, SouthAmericanSupremacyLocation.RIO_BRANCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MANAUS, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SCOTIA_SEA, SouthAmericanSupremacyLocation.ISLAS_MALVINAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ATACAMA, SouthAmericanSupremacyLocation.ARICA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ATACAMA, SouthAmericanSupremacyLocation.ANTOFAGASTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PIURA, SouthAmericanSupremacyLocation.GUAYAQUIL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PIURA, SouthAmericanSupremacyLocation.TRUJILLO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MEDELLIN, SouthAmericanSupremacyLocation.BOGOTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MEDELLIN, SouthAmericanSupremacyLocation.CALI);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MEDELLIN, SouthAmericanSupremacyLocation.PANAMA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MEDELLIN, SouthAmericanSupremacyLocation.CARTAGENA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BOGOTA, SouthAmericanSupremacyLocation.POPAYAN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BOGOTA, SouthAmericanSupremacyLocation.CALI);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BOGOTA, SouthAmericanSupremacyLocation.SANTA_MARTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BOGOTA, SouthAmericanSupremacyLocation.LLANOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BOGOTA, SouthAmericanSupremacyLocation.CARTAGENA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BOGOTA, SouthAmericanSupremacyLocation.MEDELLIN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AREQUIPA, SouthAmericanSupremacyLocation.ARICA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.AREQUIPA, SouthAmericanSupremacyLocation.LIMA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CONCEPCION, SouthAmericanSupremacyLocation.ASUNCION);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CONCEPCION, SouthAmericanSupremacyLocation.FORMOSA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CONCEPCION, SouthAmericanSupremacyLocation.CHACO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CONCEPCION, SouthAmericanSupremacyLocation.PARANA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CONCEPCION, SouthAmericanSupremacyLocation.PANTANAL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CALI, SouthAmericanSupremacyLocation.BOGOTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CALI, SouthAmericanSupremacyLocation.QUITO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CALI, SouthAmericanSupremacyLocation.CANELOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CALI, SouthAmericanSupremacyLocation.MEDELLIN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ARICA, SouthAmericanSupremacyLocation.AREQUIPA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ARICA, SouthAmericanSupremacyLocation.ATACAMA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MARANHAO, SouthAmericanSupremacyLocation.PARA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MARANHAO, SouthAmericanSupremacyLocation.GOIAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MARANHAO, SouthAmericanSupremacyLocation.PERNAMBUCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MARANHAO, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.VERAGUA, SouthAmericanSupremacyLocation.PANAMA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CHACO, SouthAmericanSupremacyLocation.CONCEPCION);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CHACO, SouthAmericanSupremacyLocation.POTOSI);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CHACO, SouthAmericanSupremacyLocation.SANTA_CRUZ);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CHACO, SouthAmericanSupremacyLocation.PANTANAL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MISIONES, SouthAmericanSupremacyLocation.ASUNCION);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MISIONES, SouthAmericanSupremacyLocation.CORRIENTES);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MISIONES, SouthAmericanSupremacyLocation.PARANA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MISIONES, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MISIONES, SouthAmericanSupremacyLocation.SANTA_FE);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MISIONES, SouthAmericanSupremacyLocation.MONTEVIDEO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PANTANAL, SouthAmericanSupremacyLocation.CONCEPCION);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PANTANAL, SouthAmericanSupremacyLocation.CHACO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PANTANAL, SouthAmericanSupremacyLocation.PARANA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PANTANAL, SouthAmericanSupremacyLocation.SAO_PAULO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PANTANAL, SouthAmericanSupremacyLocation.SANTA_CRUZ);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PANTANAL, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ARAUNCANIAN, SouthAmericanSupremacyLocation.PUNTA_ARENAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ARAUNCANIAN, SouthAmericanSupremacyLocation.SANTIAGO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ARAUNCANIAN, SouthAmericanSupremacyLocation.PATAGONIA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.POPAYAN, SouthAmericanSupremacyLocation.BOGOTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.POPAYAN, SouthAmericanSupremacyLocation.AMAZONAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.POPAYAN, SouthAmericanSupremacyLocation.CANELOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.POPAYAN, SouthAmericanSupremacyLocation.IQUITOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.POPAYAN, SouthAmericanSupremacyLocation.LLANOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.POPAYAN, SouthAmericanSupremacyLocation.ORINOCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.IQUITOS, SouthAmericanSupremacyLocation.AMAZONAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.IQUITOS, SouthAmericanSupremacyLocation.POPAYAN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.IQUITOS, SouthAmericanSupremacyLocation.CUSCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.IQUITOS, SouthAmericanSupremacyLocation.AQUIRIS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.IQUITOS, SouthAmericanSupremacyLocation.TRUJILLO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MINAS_GERAIS, SouthAmericanSupremacyLocation.SALVADOR);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MINAS_GERAIS, SouthAmericanSupremacyLocation.GOIAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MINAS_GERAIS, SouthAmericanSupremacyLocation.SAO_PAULO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MINAS_GERAIS, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GOIAS, SouthAmericanSupremacyLocation.SALVADOR);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GOIAS, SouthAmericanSupremacyLocation.MINAS_GERAIS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GOIAS, SouthAmericanSupremacyLocation.MARANHAO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GOIAS, SouthAmericanSupremacyLocation.PERNAMBUCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GOIAS, SouthAmericanSupremacyLocation.SAO_PAULO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.GOIAS, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ISLAS_MALVINAS, SouthAmericanSupremacyLocation.SCOTIA_SEA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LLANOS, SouthAmericanSupremacyLocation.BOGOTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LLANOS, SouthAmericanSupremacyLocation.MARACAIBO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LLANOS, SouthAmericanSupremacyLocation.AMAZONAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LLANOS, SouthAmericanSupremacyLocation.POPAYAN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LLANOS, SouthAmericanSupremacyLocation.SANTA_MARTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LLANOS, SouthAmericanSupremacyLocation.BARINAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LLANOS, SouthAmericanSupremacyLocation.ORINOCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ANTOFAGASTA, SouthAmericanSupremacyLocation.COPIAGO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ANTOFAGASTA, SouthAmericanSupremacyLocation.POTOSI);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ANTOFAGASTA, SouthAmericanSupremacyLocation.ATACAMA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MENDOZA, SouthAmericanSupremacyLocation.CORDOBA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MENDOZA, SouthAmericanSupremacyLocation.SANTIAGO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MENDOZA, SouthAmericanSupremacyLocation.BAHIA_BLANCA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MENDOZA, SouthAmericanSupremacyLocation.ROSARIO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CUMANA, SouthAmericanSupremacyLocation.CARACAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CUMANA, SouthAmericanSupremacyLocation.ANGOSTURA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PARA, SouthAmericanSupremacyLocation.RIO_BRANCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PARA, SouthAmericanSupremacyLocation.MARANHAO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PARA, SouthAmericanSupremacyLocation.MANAUS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.PARA, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SALTA, SouthAmericanSupremacyLocation.POTOSI);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SALTA, SouthAmericanSupremacyLocation.FORMOSA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SALTA, SouthAmericanSupremacyLocation.SAN_MIGUEL_DE_TUCUMAN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SAN_MIGUEL_DE_TUCUMAN, SouthAmericanSupremacyLocation.SALTA);
        addEdge(
            armyGraph,
            SouthAmericanSupremacyLocation.SAN_MIGUEL_DE_TUCUMAN,
            SouthAmericanSupremacyLocation.CORDOBA
        );
        addEdge(
            armyGraph,
            SouthAmericanSupremacyLocation.SAN_MIGUEL_DE_TUCUMAN,
            SouthAmericanSupremacyLocation.FORMOSA
        );
        addEdge(
            armyGraph,
            SouthAmericanSupremacyLocation.SAN_MIGUEL_DE_TUCUMAN,
            SouthAmericanSupremacyLocation.CORRIENTES
        );
        addEdge(
            armyGraph,
            SouthAmericanSupremacyLocation.SAN_MIGUEL_DE_TUCUMAN,
            SouthAmericanSupremacyLocation.SANTA_FE
        );
        addEdge(armyGraph, SouthAmericanSupremacyLocation.VALPARAISO, SouthAmericanSupremacyLocation.SANTIAGO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.VALPARAISO, SouthAmericanSupremacyLocation.COPIAGO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ORINOCO, SouthAmericanSupremacyLocation.AMAZONAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ORINOCO, SouthAmericanSupremacyLocation.POPAYAN);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ORINOCO, SouthAmericanSupremacyLocation.BARINAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.ORINOCO, SouthAmericanSupremacyLocation.LLANOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BUENOS_AIRES, SouthAmericanSupremacyLocation.ROSARIO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BUENOS_AIRES, SouthAmericanSupremacyLocation.SANTA_FE);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BUENOS_AIRES, SouthAmericanSupremacyLocation.MONTEVIDEO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MARACAIBO, SouthAmericanSupremacyLocation.CARACAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MARACAIBO, SouthAmericanSupremacyLocation.SANTA_MARTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MARACAIBO, SouthAmericanSupremacyLocation.BARINAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.MARACAIBO, SouthAmericanSupremacyLocation.LLANOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LIMA, SouthAmericanSupremacyLocation.AREQUIPA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LIMA, SouthAmericanSupremacyLocation.CUSCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.LIMA, SouthAmericanSupremacyLocation.TRUJILLO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BARINAS, SouthAmericanSupremacyLocation.MARACAIBO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BARINAS, SouthAmericanSupremacyLocation.CARACAS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BARINAS, SouthAmericanSupremacyLocation.LLANOS);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.BARINAS, SouthAmericanSupremacyLocation.ORINOCO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_FE, SouthAmericanSupremacyLocation.CORRIENTES);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_FE, SouthAmericanSupremacyLocation.MISIONES);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_FE, SouthAmericanSupremacyLocation.ROSARIO);
        addEdge(
            armyGraph,
            SouthAmericanSupremacyLocation.SANTA_FE,
            SouthAmericanSupremacyLocation.SAN_MIGUEL_DE_TUCUMAN
        );
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_FE, SouthAmericanSupremacyLocation.MONTEVIDEO);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.SANTA_FE, SouthAmericanSupremacyLocation.BUENOS_AIRES);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CARTAGENA, SouthAmericanSupremacyLocation.BOGOTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CARTAGENA, SouthAmericanSupremacyLocation.SANTA_MARTA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CARTAGENA, SouthAmericanSupremacyLocation.PANAMA);
        addEdge(armyGraph, SouthAmericanSupremacyLocation.CARTAGENA, SouthAmericanSupremacyLocation.MEDELLIN);

        addEdge(
            fleetGraph,
            SouthAmericanSupremacyLocation.SOUTH_ATLANTIC_OCEAN,
            SouthAmericanSupremacyLocation.GOLFO_DE_SAN_JORGE
        );
        addEdge(
            fleetGraph,
            SouthAmericanSupremacyLocation.SOUTH_ATLANTIC_OCEAN,
            SouthAmericanSupremacyLocation.BAHIA_DE_PARANAGUA
        );
        addEdge(
            fleetGraph,
            SouthAmericanSupremacyLocation.SOUTH_ATLANTIC_OCEAN,
            SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA
        );
        addEdge(
            fleetGraph,
            SouthAmericanSupremacyLocation.ISLAS_JUAN_FERNANDEZ,
            SouthAmericanSupremacyLocation.DRAKE_PASSAGE
        );
        addEdge(
            fleetGraph,
            SouthAmericanSupremacyLocation.ISLAS_JUAN_FERNANDEZ,
            SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO
        );
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ATACAMA, SouthAmericanSupremacyLocation.ANTOFAGASTA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ATACAMA, SouthAmericanSupremacyLocation.ARICA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ATACAMA, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ARAUNCANIAN, SouthAmericanSupremacyLocation.SANTIAGO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ARAUNCANIAN, SouthAmericanSupremacyLocation.PUNTA_ARENAS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ARAUNCANIAN, SouthAmericanSupremacyLocation.PATAGONIA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ARAUNCANIAN, SouthAmericanSupremacyLocation.DRAKE_PASSAGE);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_SAO_SEBASTAO, SouthAmericanSupremacyLocation.BAHIA_DE_TODOS_OS_SANTOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_SAO_SEBASTAO, SouthAmericanSupremacyLocation.SAO_PAULO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_SAO_SEBASTAO, SouthAmericanSupremacyLocation.BAHIA_DE_PARANAGUA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_SAO_SEBASTAO, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_PANAMA, SouthAmericanSupremacyLocation.BAHIA_SECHURA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_PANAMA, SouthAmericanSupremacyLocation.ISLAS_GALAPAGOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_PANAMA, SouthAmericanSupremacyLocation.CALI);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_PANAMA, SouthAmericanSupremacyLocation.MEDELLIN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_TODOS_OS_SANTOS, SouthAmericanSupremacyLocation.SALVADOR);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_TODOS_OS_SANTOS, SouthAmericanSupremacyLocation.BAHIA_DE_SAO_SEBASTAO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_TODOS_OS_SANTOS, SouthAmericanSupremacyLocation.BAHIA_SAO_MARCOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_TODOS_OS_SANTOS, SouthAmericanSupremacyLocation.BAHIA_DE_PARANAGUA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_TODOS_OS_SANTOS, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.MANAUS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.AMAZONAS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.PARA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MATO_GROSSO, SouthAmericanSupremacyLocation.MARANHAO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_PARIA, SouthAmericanSupremacyLocation.CARACAS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_PARIA, SouthAmericanSupremacyLocation.CUMANA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_PARIA, SouthAmericanSupremacyLocation.ATLANTIC_OCEAN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_PARIA, SouthAmericanSupremacyLocation.BOCA_DE_NAVIOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_PARIA, SouthAmericanSupremacyLocation.GOLFO_DE_DARIEN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CARTAGENA, SouthAmericanSupremacyLocation.SANTA_MARTA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CARTAGENA, SouthAmericanSupremacyLocation.GOLFO_DE_DARIEN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CARTAGENA, SouthAmericanSupremacyLocation.MEDELLIN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BUENOS_AIRES, SouthAmericanSupremacyLocation.MONTEVIDEO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BUENOS_AIRES, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BOCA_DE_NAVIOS, SouthAmericanSupremacyLocation.CUMANA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BOCA_DE_NAVIOS, SouthAmericanSupremacyLocation.GOLFO_DE_PARIA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BOCA_DE_NAVIOS, SouthAmericanSupremacyLocation.ATLANTIC_OCEAN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BOCA_DE_NAVIOS, SouthAmericanSupremacyLocation.ANGOSTURA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BOCA_DE_NAVIOS, SouthAmericanSupremacyLocation.PARA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BOCA_DE_NAVIOS, SouthAmericanSupremacyLocation.MARANHAO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MONTEVIDEO, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MONTEVIDEO, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MONTEVIDEO, SouthAmericanSupremacyLocation.MISIONES);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MONTEVIDEO, SouthAmericanSupremacyLocation.BUENOS_AIRES);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MANAUS, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MANAUS, SouthAmericanSupremacyLocation.AMAZONAS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MANAUS, SouthAmericanSupremacyLocation.PARA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MARACAIBO, SouthAmericanSupremacyLocation.SANTA_MARTA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MARACAIBO, SouthAmericanSupremacyLocation.CARACAS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MARACAIBO, SouthAmericanSupremacyLocation.GOLFO_DE_DARIEN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.TRUJILLO, SouthAmericanSupremacyLocation.NAZCA_SEA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.TRUJILLO, SouthAmericanSupremacyLocation.LIMA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CUMANA, SouthAmericanSupremacyLocation.CARACAS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CUMANA, SouthAmericanSupremacyLocation.GOLFO_DE_PARIA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CUMANA, SouthAmericanSupremacyLocation.ANGOSTURA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CUMANA, SouthAmericanSupremacyLocation.BOCA_DE_NAVIOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_SECHURA, SouthAmericanSupremacyLocation.NAZCA_SEA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_SECHURA, SouthAmericanSupremacyLocation.ISLAS_GALAPAGOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_SECHURA, SouthAmericanSupremacyLocation.CALI);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_SECHURA, SouthAmericanSupremacyLocation.GOLFO_DE_PANAMA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ANGOSTURA, SouthAmericanSupremacyLocation.CARACAS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ANGOSTURA, SouthAmericanSupremacyLocation.CUMANA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ANGOSTURA, SouthAmericanSupremacyLocation.BOCA_DE_NAVIOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ATLANTIC_OCEAN, SouthAmericanSupremacyLocation.GOLFO_DE_PARIA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ATLANTIC_OCEAN, SouthAmericanSupremacyLocation.BAHIA_SAO_MARCOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ATLANTIC_OCEAN, SouthAmericanSupremacyLocation.BOCA_DE_NAVIOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.LIMA, SouthAmericanSupremacyLocation.TRUJILLO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.LIMA, SouthAmericanSupremacyLocation.NAZCA_SEA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.LIMA, SouthAmericanSupremacyLocation.AREQUIPA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.LIMA, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.DRAKE_PASSAGE, SouthAmericanSupremacyLocation.ISLAS_JUAN_FERNANDEZ);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.DRAKE_PASSAGE, SouthAmericanSupremacyLocation.SANTIAGO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.DRAKE_PASSAGE, SouthAmericanSupremacyLocation.ARAUNCANIAN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.DRAKE_PASSAGE, SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA, SouthAmericanSupremacyLocation.ANTOFAGASTA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA, SouthAmericanSupremacyLocation.NAZCA_SEA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA, SouthAmericanSupremacyLocation.ARICA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA, SouthAmericanSupremacyLocation.AREQUIPA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA, SouthAmericanSupremacyLocation.ATACAMA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA, SouthAmericanSupremacyLocation.LIMA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA, SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GUAYAQUIL, SouthAmericanSupremacyLocation.QUITO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MISIONES, SouthAmericanSupremacyLocation.MONTEVIDEO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MISIONES, SouthAmericanSupremacyLocation.ASUNCION);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MISIONES, SouthAmericanSupremacyLocation.PARANA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MISIONES, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MISIONES, SouthAmericanSupremacyLocation.CORRIENTES);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SANTA_MARTA, SouthAmericanSupremacyLocation.MARACAIBO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SANTA_MARTA, SouthAmericanSupremacyLocation.CARTAGENA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SANTA_MARTA, SouthAmericanSupremacyLocation.GOLFO_DE_DARIEN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ANTOFAGASTA, SouthAmericanSupremacyLocation.COPIAGO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ANTOFAGASTA, SouthAmericanSupremacyLocation.ATACAMA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ANTOFAGASTA, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ANTOFAGASTA, SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.VALPARAISO, SouthAmericanSupremacyLocation.COPIAGO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.VALPARAISO, SouthAmericanSupremacyLocation.SANTIAGO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.VALPARAISO, SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CARACAS, SouthAmericanSupremacyLocation.MARACAIBO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CARACAS, SouthAmericanSupremacyLocation.CUMANA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CARACAS, SouthAmericanSupremacyLocation.GOLFO_DE_PARIA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CARACAS, SouthAmericanSupremacyLocation.ANGOSTURA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CARACAS, SouthAmericanSupremacyLocation.GOLFO_DE_DARIEN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SAO_PAULO, SouthAmericanSupremacyLocation.BAHIA_DE_SAO_SEBASTAO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SAO_PAULO, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SAO_PAULO, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ISLAS_GALAPAGOS, SouthAmericanSupremacyLocation.NAZCA_SEA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ISLAS_GALAPAGOS, SouthAmericanSupremacyLocation.BAHIA_SECHURA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ISLAS_GALAPAGOS, SouthAmericanSupremacyLocation.GOLFO_DE_PANAMA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.AREQUIPA, SouthAmericanSupremacyLocation.ARICA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.AREQUIPA, SouthAmericanSupremacyLocation.LIMA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.AREQUIPA, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_PARANAGUA, SouthAmericanSupremacyLocation.BAHIA_DE_TODOS_OS_SANTOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_PARANAGUA, SouthAmericanSupremacyLocation.SOUTH_ATLANTIC_OCEAN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_PARANAGUA, SouthAmericanSupremacyLocation.BAHIA_DE_SAO_SEBASTAO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_DE_PARANAGUA, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.QUITO, SouthAmericanSupremacyLocation.CALI);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.QUITO, SouthAmericanSupremacyLocation.GUAYAQUIL);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MARANHAO, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MARANHAO, SouthAmericanSupremacyLocation.BAHIA_SAO_MARCOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MARANHAO, SouthAmericanSupremacyLocation.BOCA_DE_NAVIOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MARANHAO, SouthAmericanSupremacyLocation.PARA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PARANA, SouthAmericanSupremacyLocation.ASUNCION);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PARANA, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PARANA, SouthAmericanSupremacyLocation.MISIONES);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PARANA, SouthAmericanSupremacyLocation.CONCEPCION);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA, SouthAmericanSupremacyLocation.SOUTH_ATLANTIC_OCEAN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA, SouthAmericanSupremacyLocation.MONTEVIDEO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA, SouthAmericanSupremacyLocation.BAHIA_BLANCA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA, SouthAmericanSupremacyLocation.GOLFO_DE_SAN_JORGE);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA, SouthAmericanSupremacyLocation.BAHIA_DE_PARANAGUA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA, SouthAmericanSupremacyLocation.BUENOS_AIRES);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO, SouthAmericanSupremacyLocation.BAHIA_DE_TODOS_OS_SANTOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO, SouthAmericanSupremacyLocation.SAO_PAULO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO, SouthAmericanSupremacyLocation.SALVADOR);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO, SouthAmericanSupremacyLocation.BAHIA_DE_SAO_SEBASTAO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ISLAS_MALVINAS, SouthAmericanSupremacyLocation.GOLFO_DE_SAN_JORGE);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_BLANCA, SouthAmericanSupremacyLocation.PATAGONIA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_BLANCA, SouthAmericanSupremacyLocation.GOLFO_DE_SAN_JORGE);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_BLANCA, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.NAZCA_SEA, SouthAmericanSupremacyLocation.TRUJILLO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.NAZCA_SEA, SouthAmericanSupremacyLocation.BAHIA_SECHURA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.NAZCA_SEA, SouthAmericanSupremacyLocation.ISLAS_GALAPAGOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.NAZCA_SEA, SouthAmericanSupremacyLocation.LIMA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.NAZCA_SEA, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PUNTA_ARENAS, SouthAmericanSupremacyLocation.ARAUNCANIAN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ARICA, SouthAmericanSupremacyLocation.AREQUIPA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ARICA, SouthAmericanSupremacyLocation.ATACAMA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ARICA, SouthAmericanSupremacyLocation.BAHIA_DE_ARICA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_SAO_MARCOS, SouthAmericanSupremacyLocation.BAHIA_DE_TODOS_OS_SANTOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_SAO_MARCOS, SouthAmericanSupremacyLocation.SALVADOR);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_SAO_MARCOS, SouthAmericanSupremacyLocation.ATLANTIC_OCEAN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.BAHIA_SAO_MARCOS, SouthAmericanSupremacyLocation.MARANHAO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL, SouthAmericanSupremacyLocation.MONTEVIDEO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL, SouthAmericanSupremacyLocation.SAO_PAULO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL, SouthAmericanSupremacyLocation.PARANA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.RIO_GRANDE_DO_SUL, SouthAmericanSupremacyLocation.MISIONES);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PARA, SouthAmericanSupremacyLocation.MANAUS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PARA, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PARA, SouthAmericanSupremacyLocation.BOCA_DE_NAVIOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PARA, SouthAmericanSupremacyLocation.MARANHAO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ASUNCION, SouthAmericanSupremacyLocation.PARANA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ASUNCION, SouthAmericanSupremacyLocation.CORRIENTES);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ASUNCION, SouthAmericanSupremacyLocation.MISIONES);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.ASUNCION, SouthAmericanSupremacyLocation.CONCEPCION);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.COPIAGO, SouthAmericanSupremacyLocation.ANTOFAGASTA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.COPIAGO, SouthAmericanSupremacyLocation.VALPARAISO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.COPIAGO, SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PATAGONIA, SouthAmericanSupremacyLocation.BAHIA_BLANCA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PATAGONIA, SouthAmericanSupremacyLocation.ARAUNCANIAN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PATAGONIA, SouthAmericanSupremacyLocation.GOLFO_DE_SAN_JORGE);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_DARIEN, SouthAmericanSupremacyLocation.SANTA_MARTA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_DARIEN, SouthAmericanSupremacyLocation.MARACAIBO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_DARIEN, SouthAmericanSupremacyLocation.CARACAS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_DARIEN, SouthAmericanSupremacyLocation.GOLFO_DE_PARIA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_DARIEN, SouthAmericanSupremacyLocation.CARTAGENA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MEDELLIN, SouthAmericanSupremacyLocation.CARTAGENA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MEDELLIN, SouthAmericanSupremacyLocation.CALI);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.MEDELLIN, SouthAmericanSupremacyLocation.GOLFO_DE_PANAMA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SALVADOR, SouthAmericanSupremacyLocation.BAHIA_DE_TODOS_OS_SANTOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SALVADOR, SouthAmericanSupremacyLocation.BAHIA_SAO_MARCOS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SALVADOR, SouthAmericanSupremacyLocation.RIO_DE_JANEIRO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SANTIAGO, SouthAmericanSupremacyLocation.VALPARAISO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SANTIAGO, SouthAmericanSupremacyLocation.ARAUNCANIAN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SANTIAGO, SouthAmericanSupremacyLocation.DRAKE_PASSAGE);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.SANTIAGO, SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.AMAZONAS, SouthAmericanSupremacyLocation.MANAUS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.AMAZONAS, SouthAmericanSupremacyLocation.MATO_GROSSO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CALI, SouthAmericanSupremacyLocation.BAHIA_SECHURA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CALI, SouthAmericanSupremacyLocation.QUITO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CALI, SouthAmericanSupremacyLocation.MEDELLIN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CALI, SouthAmericanSupremacyLocation.GOLFO_DE_PANAMA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_SAN_JORGE, SouthAmericanSupremacyLocation.SOUTH_ATLANTIC_OCEAN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_SAN_JORGE, SouthAmericanSupremacyLocation.BAHIA_BLANCA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_SAN_JORGE, SouthAmericanSupremacyLocation.PATAGONIA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_SAN_JORGE, SouthAmericanSupremacyLocation.RIO_DE_LA_PLATA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_SAN_JORGE, SouthAmericanSupremacyLocation.ISLAS_MALVINAS);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CORRIENTES, SouthAmericanSupremacyLocation.ASUNCION);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CORRIENTES, SouthAmericanSupremacyLocation.MISIONES);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO, SouthAmericanSupremacyLocation.ANTOFAGASTA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO, SouthAmericanSupremacyLocation.VALPARAISO);
        addEdge(
            fleetGraph,
            SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO,
            SouthAmericanSupremacyLocation.ISLAS_JUAN_FERNANDEZ
        );
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO, SouthAmericanSupremacyLocation.COPIAGO);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO, SouthAmericanSupremacyLocation.SANTIAGO);
        addEdge(
            fleetGraph,
            SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO,
            SouthAmericanSupremacyLocation.DRAKE_PASSAGE
        );
        addEdge(
            fleetGraph,
            SouthAmericanSupremacyLocation.GOLFO_DE_GUAFO,
            SouthAmericanSupremacyLocation.BAHIA_DE_ARICA
        );
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CONCEPCION, SouthAmericanSupremacyLocation.ASUNCION);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.CONCEPCION, SouthAmericanSupremacyLocation.PARANA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PANAMA_NC, SouthAmericanSupremacyLocation.CARTAGENA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PANAMA_NC, SouthAmericanSupremacyLocation.VERAGUA_NC);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PANAMA_NC, SouthAmericanSupremacyLocation.GOLFO_DE_DARIEN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.VERAGUA_NC, SouthAmericanSupremacyLocation.GOLFO_DE_DARIEN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PANAMA_SC, SouthAmericanSupremacyLocation.VERAGUA_SC);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PANAMA_SC, SouthAmericanSupremacyLocation.MEDELLIN);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.PANAMA_SC, SouthAmericanSupremacyLocation.GOLFO_DE_PANAMA);
        addEdge(fleetGraph, SouthAmericanSupremacyLocation.VERAGUA_SC, SouthAmericanSupremacyLocation.GOLFO_DE_PANAMA);
    }

    public static MapVariant getInstance() {
        return INSTANCE;
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
            Country.ARGENTINA,
            Country.BOLIVIA,
            Country.BRASIL,
            Country.CHILE,
            Country.COLOMBIA,
            Country.PARAGUAY,
            Country.PERU,
            Country.VENEZUELA
        ));
    }

    @Override
    public Map<Location, Set<Location>> getMovementGraph() {
        return armyGraph;
    }

    @Override
    public Location parseLocation(String name) {
        return SouthAmericanSupremacyLocation.findByName(name);
    }

    @Override
    public Map<Location, Set<Location>> getMovementGraph(UnitType unitType) {
        if (unitType.equals(UnitType.FLEET)) {
            return fleetGraph;
        } else {
            return armyGraph;
        }
    }

    @Override
    public long getStartingYear() {
        return 1835;
    }
}
