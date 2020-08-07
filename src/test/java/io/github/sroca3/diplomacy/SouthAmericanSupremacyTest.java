package io.github.sroca3.diplomacy;

import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyLocation;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyMapVariant;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SouthAmericanSupremacyTest {

    @Test
    public void gameSetup() {
        Diplomacy diplomacy = new Diplomacy(SouthAmericanSupremacyMapVariant.getInstance());
        diplomacy.addStandardStartingUnits();
        assertEquals(UnitType.ARMY, diplomacy.getUnitLocations().get(SouthAmericanSupremacyLocation.SALTA).getType());
        assertEquals(Country.ARGENTINA, diplomacy.getUnitLocations().get(SouthAmericanSupremacyLocation.SALTA).getCountry());
        assertEquals(UnitType.FLEET, diplomacy.getUnitLocations().get(SouthAmericanSupremacyLocation.SALVADOR).getType());
        assertEquals(Country.BRASIL, diplomacy.getUnitLocations().get(SouthAmericanSupremacyLocation.SALVADOR).getCountry());
        diplomacy.beginFirstPhase();
        diplomacy.addOrder(diplomacy.parseOrder("F Salvador move Bahia de Todos os Santos"));
        diplomacy.adjudicate();
        assertEquals(PhaseName.FALL.toString(), diplomacy.getPhaseName());
    }

    @Test
    public void gameInformation() {
        Diplomacy diplomacy = new Diplomacy(SouthAmericanSupremacyMapVariant.getInstance());
        diplomacy.addStandardStartingUnits();
        diplomacy.beginFirstPhase();
        assertEquals(2L, diplomacy.getSupplyCenterCount(Country.VENEZUELA));
        assertEquals(4L, diplomacy.getSupplyCenterCount(Country.BRASIL));
        assertEquals(2L, diplomacy.getUnitCount(Country.VENEZUELA));
        assertEquals(1835L, diplomacy.getYear());
        assertEquals("SPRING", diplomacy.getPhaseName());
    }
}
