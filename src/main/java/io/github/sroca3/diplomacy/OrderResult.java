package io.github.sroca3.diplomacy;

public class OrderResult {
    private final Order order;
    private final OrderStatus orderStatus;

    public OrderResult(Order order, OrderStatus orderStatus) {
        this.order = order;
        this.orderStatus = orderStatus;
    }

    public OrderStatus getStatus() {
        return orderStatus;
    }
}
