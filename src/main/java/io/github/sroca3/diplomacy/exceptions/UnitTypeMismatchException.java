package io.github.sroca3.diplomacy.exceptions;

public class UnitTypeMismatchException extends RuntimeException {
    public UnitTypeMismatchException() {
        super("Unit type specified but does not match location.");
    }
}
