package io.github.sroca3.diplomacy;

public class Fleet extends AbstractUnit {

    public Fleet(Country country) {
        super(country);
    }

    @Override
    public UnitType getType() {
        return UnitType.FLEET;
    }
}
