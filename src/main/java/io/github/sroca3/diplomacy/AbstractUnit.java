package io.github.sroca3.diplomacy;

public abstract class AbstractUnit implements Unit {

    private final Country country;

    public AbstractUnit(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }
}
