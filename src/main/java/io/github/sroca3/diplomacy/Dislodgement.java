package io.github.sroca3.diplomacy;

public class Dislodgement {
    private final Order attackingOrder;
    private final Order dislodgedOrder;

    public Dislodgement(Order attackingOrder, Order dislodgedOrder) {
        this.attackingOrder = attackingOrder;
        this.dislodgedOrder = dislodgedOrder;
    }

    public Order getAttackingOrder() {
        return attackingOrder;
    }

    public Order getDislodgedOrder() {
        return dislodgedOrder;
    }
}
