package io.github.sroca3.diplomacy;

public enum OrderStatus {
    BOUNCED,
    BUILD_FAILED,
    DISBANDED,
    DISLODGED,
    ILLEGAL,
    ILLEGAL_ORDER_REPLACED_WITH_HOLD,
    PROCESSING,
    RESOLVED,
    SUPPORT_CUT,
    SUPPORT_FAILED,
    CONVOY_FAILED,
    UNRESOLVED;

    public boolean isUnresolved() {
        return this == UNRESOLVED;
    }

    public boolean isResolved() {
        return this == RESOLVED;
    }

    public boolean isBounced() {
        return this == BOUNCED;
    }

    public boolean isIllegal() {
        return this == ILLEGAL_ORDER_REPLACED_WITH_HOLD;
    }

    public boolean isDislodged() {
        return this == DISLODGED;
    }

    public boolean isFailed() {
        return this == SUPPORT_FAILED || this == CONVOY_FAILED;
    }

    public boolean isProcessing() {
        return this == PROCESSING;
    }

    public boolean isCut() {
        return this == SUPPORT_CUT;
    }
}
