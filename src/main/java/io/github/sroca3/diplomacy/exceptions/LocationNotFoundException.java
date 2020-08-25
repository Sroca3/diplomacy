package io.github.sroca3.diplomacy.exceptions;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException() {
        super("Could not find location.");
    }
}
