package io.github.sroca3.diplomacy;

import java.util.UUID;

public interface Unit {
    UUID getId();

    Country getCountry();

    UnitType getType();
}
