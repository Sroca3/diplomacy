package io.github.sroca3.diplomacy;

import io.github.sroca3.diplomacy.svg.SouthAmericanSupremacyMap;
import org.junit.jupiter.api.Test;

public class MapEditingTest {

    @Test
    public void addUnitToMap() {
        SouthAmericanSupremacyMap southAmericanSupremacyMap = new SouthAmericanSupremacyMap();
        southAmericanSupremacyMap.drawUnits();
        southAmericanSupremacyMap.drawArrows();
        southAmericanSupremacyMap.colorTerritories();
        southAmericanSupremacyMap.generateMap();
    }
}
