package io.github.sroca3.diplomacy.maps;

import io.github.sroca3.diplomacy.UnitType;

public enum LocationType {
    SEA,
    LAND,
    COASTAL_LAND,
    COAST,
    IMPASSABLE;

    public boolean supportsConvoy() {
        return this == SEA;
    }

    public boolean supports(UnitType unitType) {
        switch (unitType) {
            case ARMY:
                return this == LAND || this == COASTAL_LAND;
            case FLEET:
                return this == SEA || this == COASTAL_LAND || this == COAST;
            default:
                return false;
        }
    }
}
