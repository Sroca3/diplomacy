package io.github.sroca3.diplomacy;

public class Army extends AbstractUnit {

    public Army(Country country) {
        super(country);
    }

    @Override
    public UnitType getType() {
        return UnitType.ARMY;
    }
}
