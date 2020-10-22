package io.github.sroca3.diplomacy.svg;

import io.github.sroca3.diplomacy.Order;

public enum ArrowType {
    BOUNCE,
    SUPPORT,
    MOVE,
    SUPPORT_CUT,
    SOURCE;

    public static ArrowType from(Order order) {
        if (order.getOrderType().isMove()) {
            if (order.getStatus().isResolved()) {
                return MOVE;
            } else {
                return BOUNCE;
            }
        } else if (order.getOrderType().isSupport()) {
            if (order.getStatus().isResolved()) {
                return SUPPORT;
            } else {
                return SUPPORT_CUT;
            }
        }
        return SOURCE;
    }
}
