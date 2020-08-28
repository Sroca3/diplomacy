package io.github.sroca3.diplomacy.exceptions;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(String locationString) {
        super(String.format("Could not find location for '%s'.", locationString));
    }
}
