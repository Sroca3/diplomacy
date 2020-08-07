package io.github.sroca3.diplomacy;

public enum OrderType {
    CONVOY,
    HOLD,
    MOVE,
    SUPPORT;

    public boolean isSupport() {
        return this == SUPPORT;
    }

    public boolean isMove() {
        return this == MOVE;
    }

    public boolean isConvoy() {
        return this == CONVOY;
    }

    public boolean isHold() {
        return this == HOLD;
    }
}
