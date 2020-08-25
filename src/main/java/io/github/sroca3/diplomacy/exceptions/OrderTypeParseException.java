package io.github.sroca3.diplomacy.exceptions;

public class OrderTypeParseException extends RuntimeException {
    public OrderTypeParseException() {
        super("Could not parse order type.");
    }
}
