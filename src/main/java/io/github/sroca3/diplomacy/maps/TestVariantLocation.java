package io.github.sroca3.diplomacy.maps;

import io.github.sroca3.diplomacy.Location;
import io.github.sroca3.diplomacy.UnitType;

import java.util.Set;

public enum TestVariantLocation implements Location {
    A(new UnitType[]{UnitType.ARMY, UnitType.FLEET}),
    B(new UnitType[]{UnitType.ARMY, UnitType.FLEET}),
    C(new UnitType[]{UnitType.FLEET}),
    D(new UnitType[]{UnitType.FLEET});

    private final Set<UnitType> unitTypes;
    private boolean supportsConvoy;

    TestVariantLocation(UnitType[] unitTypes) {
        this.unitTypes = Set.of(unitTypes);
        if (unitTypes.length == 1 && unitTypes[0] == UnitType.ARMY) {
            supportsConvoy = false;
        } else {
            supportsConvoy = true;
        }
    }

    TestVariantLocation(UnitType[] unitTypes, boolean supportsConvoy) {
        this.unitTypes = Set.of(unitTypes);
        this.supportsConvoy = supportsConvoy;
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
        return false;
    }
}
