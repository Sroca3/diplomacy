package io.github.sroca3.diplomacy;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public enum OrderType {
    CONVOY(Set.of("convoys")),
    HOLD,
    MOVE(Set.of("-")),
    SUPPORT(Set.of("supports")),
    RETREAT();

    private static final Map<String, OrderType> NAMES_MAPPING = new HashMap<>();

    static {
        EnumSet.allOf(OrderType.class)
               .stream()
               .forEach(orderType -> {
                   NAMES_MAPPING.put(orderType.name().toLowerCase(Locale.ENGLISH), orderType);
                   orderType.getAlternateNames().forEach(name -> NAMES_MAPPING.put(name, orderType));
               });
    }

    private final Set<String> alternateNames;

    OrderType() {
        this(Set.of());
    }

    OrderType(Set<String> alternateNames) {
        this.alternateNames = alternateNames;
    }

    public static OrderType from(String name, PhaseName phaseName) {
        if (phaseName.isRetreatPhase() && name.equalsIgnoreCase("-")) {
            return RETREAT;
        }
        return NAMES_MAPPING.get(name.toLowerCase(Locale.ENGLISH));
    }

    public boolean isSupport() {
        return this == SUPPORT;
    }

    public boolean isMove() {
        return this == MOVE;
    }

    public boolean isConvoy() {
        return this == CONVOY;
    }

    public boolean isHold() {
        return this == HOLD;
    }

    public Set<String> getAlternateNames() {
        return alternateNames;
    }

    public boolean isRetreat() {
        return this == RETREAT;
    }
}
