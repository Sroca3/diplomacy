package io.github.sroca3.diplomacy;

import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyLocation;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyMapVariant;
import io.github.sroca3.diplomacy.svg.SouthAmericanSupremacyMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class MapEditingTest {

    @Test
    public void addUnitToMap() {
        Diplomacy diplomacy = new Diplomacy(SouthAmericanSupremacyMapVariant.getInstance());
        diplomacy.addStandardStartingUnits();
        diplomacy.beginFirstPhase();
        diplomacy.addOrder(new Order(
            diplomacy.getUnitLocations().get(SouthAmericanSupremacyLocation.CORDOBA),
            SouthAmericanSupremacyLocation.CORDOBA,
            OrderType.MOVE,
            SouthAmericanSupremacyLocation.CORDOBA,
            SouthAmericanSupremacyLocation.MENDOZA
        ));
        diplomacy.adjudicate();
        SouthAmericanSupremacyMap southAmericanSupremacyMap = new SouthAmericanSupremacyMap();
        assertDoesNotThrow(() -> southAmericanSupremacyMap.drawUnits(diplomacy.getUnitLocations()));
        assertDoesNotThrow(() -> southAmericanSupremacyMap.drawArrows(diplomacy.getPreviousPhase().getOrders()));
        assertDoesNotThrow(() -> southAmericanSupremacyMap.colorTerritories(diplomacy.getLocationOwnership()));
        assertDoesNotThrow(southAmericanSupremacyMap::generateMap);
    }
}
