package io.github.sroca3.diplomacy;

import java.awt.*;

public enum CountryEnum implements Country {
    AUSTRIA,
    ENGLAND,
    FRANCE,
    GERMANY,
    ITALY,
    RUSSIA,
    TURKEY;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Color getColor() {
        return null;
    }
}
