package io.github.sroca3.diplomacy;

public enum OrderStatus {
    BOUNCED("Bounced"),
    BUILD_FAILED("Build Failed"),
    DISBANDED("Disbanded"),
    DISLODGED("Dislodged"),
    ILLEGAL("Illegal"),
    ILLEGAL_ORDER_REPLACED_WITH_HOLD("Illegal order replaced with Hold"),
    PROCESSING("Processing"),
    RESOLVED("Resolved"),
    SUPPORT_CUT("Support cut"),
    SUPPORT_FAILED("Support failed"),
    CONVOY_FAILED("Convoy failed"),
    UNRESOLVED("Unresolved");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

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

    public String getDescription() {
        return description;
    }
}
