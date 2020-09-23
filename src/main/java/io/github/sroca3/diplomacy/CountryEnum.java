package io.github.sroca3.diplomacy;

import java.awt.*;

public enum CountryEnum implements Country {
    ARGENTINA,
    AUSTRIA,
    BOLIVIA,
    BRASIL,
    CHILE,
    COLOMBIA,
    ENGLAND,
    FRANCE,
    GERMANY,
    ITALY,
    PARAGUAY,
    PERU,
    RUSSIA,
    TURKEY,
    VENEZUELA;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Color getColor() {
        return null;
    }
}
