package io.github.sroca3.diplomacy.maps;

import io.github.sroca3.diplomacy.Location;
import io.github.sroca3.diplomacy.UnitType;

public enum TestVariantLocation implements Location {
    A(LocationType.COASTAL_LAND),
    B(LocationType.COASTAL_LAND),
    C(LocationType.SEA),
    D(LocationType.SEA);

    private final LocationType locationType;

    TestVariantLocation(LocationType locationType) {
        this.locationType = locationType;
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
        return false;
    }

    @Override
    public boolean isSea() {
        return false;
    }
}
