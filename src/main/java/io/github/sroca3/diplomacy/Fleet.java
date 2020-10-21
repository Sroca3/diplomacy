package io.github.sroca3.diplomacy;

import java.util.UUID;

public class Fleet extends AbstractUnit {

    private final UUID id = UUID.randomUUID();
    public Fleet(Country country) {
        super(country);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UnitType getType() {
        return UnitType.FLEET;
    }
}
