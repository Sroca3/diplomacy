package io.github.sroca3.diplomacy;

public enum PhaseName {
    SPRING_ORDERS,
    SPRING_RETREAT,
    FALL_ORDERS,
    FALL_RETREAT,
    WINTER_BUILD;

    public boolean isOrderPhase() {
        return this == SPRING_ORDERS || this == FALL_ORDERS;
    }

    public boolean isRetreatPhase() {
        return this == SPRING_RETREAT || this == FALL_RETREAT;
    }

    public boolean isBuildPhase() {
        return this == WINTER_BUILD;
    }

    public boolean isSpringRetreat() {
        return SPRING_RETREAT.equals(this);
    }

    public boolean isFallRetreat() {
        return FALL_RETREAT.equals(this);
    }

    public boolean isSpringOrders() {
        return SPRING_ORDERS.equals(this);
    }
}
