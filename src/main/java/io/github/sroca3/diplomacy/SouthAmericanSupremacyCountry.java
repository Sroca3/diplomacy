package io.github.sroca3.diplomacy;

import java.awt.*;

public enum SouthAmericanSupremacyCountry implements Country {
    ARGENTINA(new Color(124, 104, 188)),
    BOLIVIA(new Color(198, 47, 96)),
    BRASIL(new Color(47, 187, 106)),
    CHILE(new Color(171, 97, 55)),
    COLOMBIA(new Color(236, 183, 47)),
    PARAGUAY(new Color(143, 200, 219)),
    PERU(new Color(47, 88, 186)),
    VENEZUELA(new Color(238, 234, 70));

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
        return this.color;
    }
}
