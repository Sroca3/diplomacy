package io.github.sroca3.diplomacy.exceptions;

public class CountryOrderMismatchException extends RuntimeException {
    public CountryOrderMismatchException() {
        super("Cannot issue orders for another country's unit.");
    }
}
