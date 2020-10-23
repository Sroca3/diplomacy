package io.github.sroca3.diplomacy.exceptions;

public class OrderTypeParseException extends RuntimeException {
    public OrderTypeParseException(String orderInput) {
        super("Could not parse order type from order: " + orderInput);
    }
}
