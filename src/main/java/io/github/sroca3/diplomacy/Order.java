package io.github.sroca3.diplomacy;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.UUID;

public class Order {
    private final UUID id;
    private final Country country;
    private final Unit unit;
    private final OrderType orderType;
    private final Location currentLocation;
    private final Location fromLocation;
    private Location toLocation;
    private int strength = 1;
    private OrderStatus status = OrderStatus.UNRESOLVED;

    public Order(Unit unit, Location currentLocation) {
        this(unit, currentLocation, OrderType.HOLD, currentLocation, currentLocation);
    }

    public Order(Unit unit, Location currentLocation, OrderType orderType, Location fromLocation, Location toLocation) {
        this.id = UUID.randomUUID();
        this.country = unit.getCountry();
        this.unit = unit;
        this.orderType = orderType;
        this.currentLocation = currentLocation;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    public Order(Order other) {
        this.id = other.id;
        this.country = other.country;
        this.unit = other.unit;
        this.orderType = other.orderType;
        this.currentLocation = other.currentLocation;
        this.fromLocation = other.fromLocation;
        this.toLocation = other.toLocation;
        this.strength = other.strength;
        this.status = other.status;
    }

    public UUID getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public Location getToLocation() {
        return toLocation;
    }

    public void addSupport() {
        this.strength += 1;
    }

    public void removeSupport() {
        this.strength -= 1;
    }

    public int getStrength() {
        return strength;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void resolve() {
        status = OrderStatus.RESOLVED;
    }

    public Unit getUnit() {
        return unit;
    }

    public void convertIllegalMoveToHold() {
        status = OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD;
    }

    public void bounce() {
        status = OrderStatus.BOUNCED;
    }

    public void dislodge() {
        status = OrderStatus.DISLODGED;
    }

    public void failed() {
        if (getOrderType().isSupport()) {
            status = OrderStatus.SUPPORT_FAILED;
        } else if (getOrderType().isConvoy() || getOrderType().isMove()) {
            status = OrderStatus.CONVOY_FAILED;
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("country=" + country)
            .add("unit=" + unit)
            .add("orderType=" + orderType)
            .add("currentLocation=" + currentLocation)
            .add("fromLocation=" + fromLocation)
            .add("toLocation=" + toLocation)
            .add("strength=" + strength)
            .add("status=" + status)
            .toString();
    }


    public String getDescription() {
        String description = String.join(
            " ",
            lowercaseAndCapitalize(getUnit().getType().name()),
            capitalizeEach(currentLocation.toString()),
            lowercaseAndCapitalize(orderType.name())
        );
        if (getOrderType().isMove()) {
            description = String.join(" ", description, capitalizeEach(toLocation.toString()));
        } else if (getOrderType().isSupport() && fromLocation == toLocation) {
            description = String.join(" ", description, capitalizeEach(fromLocation.toString()), "to hold");
        } else if (!getOrderType().isHold()) {
            description = String.join(
                " ",
                description,
                capitalizeEach(fromLocation.toString()),
                "to",
                capitalizeEach(toLocation.toString())
            );
        }
        if (!getStatus().isUnresolved()) {
            description = String.join(" -> ", description, lowercaseAndCapitalize(status.name()));
        }
        return description;
    }

    private String lowercaseAndCapitalize(String s) {
        return capitalize(s.toLowerCase());
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private String capitalizeEach(String s) {
        String[] parts = s.split("_");
        return capitalize(
            Arrays.stream(parts)
                  .map(part -> part.length() > 2 ? lowercaseAndCapitalize(part) : part.toLowerCase())
                  .reduce((x, y) -> x + " " + y).orElse("")
        );
    }

    public void process() {
        status = OrderStatus.PROCESSING;
    }

    public void cut() {
        status = OrderStatus.SUPPORT_CUT;
    }

    public void specifyCoast(Location location) {
        if (toLocation.getCoasts().contains(location)) {
            this.toLocation = location;
        }
    }
}
