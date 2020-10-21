package io.github.sroca3.diplomacy;

import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyMapVariant;
import io.github.sroca3.diplomacy.svg.SouthAmericanSupremacyMap;
import org.junit.jupiter.api.Test;

public class MapEditingTest {

    @Test
    public void addUnitToMap() {
        Diplomacy diplomacy = new Diplomacy(SouthAmericanSupremacyMapVariant.getInstance());
        diplomacy.addStandardStartingUnits();
        SouthAmericanSupremacyMap southAmericanSupremacyMap = new SouthAmericanSupremacyMap();
        southAmericanSupremacyMap.drawUnits(diplomacy.getUnitLocations());
        southAmericanSupremacyMap.drawArrows(null);
        southAmericanSupremacyMap.colorTerritories(diplomacy.getLocationOwnership());
        southAmericanSupremacyMap.generateMap();
    }
}
