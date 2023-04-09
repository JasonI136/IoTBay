package iotbay.models.enums;

public enum ShipmentStatus {
    DISPATCHED("DISPATCHED"),
    IN_TRANSIT("IN_TRANSIT"),
    DELAYED("DELAYED"),
    DELIVERED("DELIVERED"),

    EXCEPTION("EXCEPTION"),

    FAILED("FAILED");

    private final String value;

    ShipmentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
