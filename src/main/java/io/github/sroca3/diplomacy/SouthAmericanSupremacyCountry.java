package io.github.sroca3.diplomacy;

import java.awt.*;

public enum SouthAmericanSupremacyCountry implements Country {
    ARGENTINA(new Color(124, 104, 188)),
    BOLIVIA(Color.WHITE),
    BRASIL(Color.WHITE),
    CHILE(Color.WHITE),
    COLOMBIA(Color.WHITE),
    PARAGUAY(Color.WHITE),
    PERU(Color.WHITE),
    VENEZUELA(Color.WHITE);

    private final Color color;

    SouthAmericanSupremacyCountry(Color color) {
        this.color = color;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Color getColor() {
        return color;
    }
}
