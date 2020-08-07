package io.github.sroca3.diplomacy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Phase {

    private final Map<Location, Unit> startingUnitLocations;
    private final Map<Location, Unit> resultingUnitLocations;
    private final Map<Location, Set<Location>> adjacencies;
    private final MapVariant mapVariant;
    private List<Order> orders = new LinkedList<>();
    private PhaseName phaseName;

    public Phase(
        Map<Location, Unit> startingUnitLocations,
        MapVariant mapVariant,
        Map<Location, Set<Location>> adjacencies
    ) {
        this.startingUnitLocations = Map.copyOf(startingUnitLocations);
        this.resultingUnitLocations = new HashMap<>(startingUnitLocations);
        this.adjacencies = adjacencies;
        this.mapVariant = mapVariant;
    }

    public Phase(
        Map<Location, Unit> startingUnitLocations,
        MapVariant mapVariant,
        Map<Location, Set<Location>> adjacencies,
        PhaseName phaseName
    ) {
        this.startingUnitLocations = Map.copyOf(startingUnitLocations);
        this.resultingUnitLocations = new HashMap<>(startingUnitLocations);
        this.adjacencies = adjacencies;
        this.mapVariant = mapVariant;
        this.phaseName = phaseName;
    }

    public void addOrder(
        Order order
    ) {
        orders.add(order);
    }

    private Order resolveOrder(Order order) {
        if (!isOrderForAdjacentLocations(order)) {
            order.convertIllegalMoveToHold();
        }
        if (order.getStatus().isUnresolved()) {
            order.process();
            switch (order.getOrderType()) {
                case MOVE:
                    resolveMoveOrder(order);
                    break;
                case HOLD:
                    resolveHoldOrder(order);
                    break;
                case SUPPORT:
                    resolveSupportOrder(order);
                    break;
            }
        }
        return order;
    }

    private Optional<Order> findBy(OrderType orderType, Location toLocation) {
        return orders
            .stream()
            .filter(order -> order.getOrderType() == orderType)
            .filter(order -> order.getToLocation() == toLocation)
            .findAny();
    }

    private Optional<Order> findBy(OrderType orderType, Location fromLocation, Location toLocation) {
        return orders
            .stream()
            .filter(order -> order.getOrderType() == orderType)
            .filter(order -> order.getFromLocation() == fromLocation)
            .filter(order -> order.getToLocation() == toLocation)
            .findAny();
    }

    private void resolveSupportOrder(Order order) {
        if (findBy(OrderType.MOVE, order.getCurrentLocation()).isEmpty()) {
            findBy(OrderType.MOVE, order.getFromLocation(), order.getToLocation())
                .ifPresent(o -> {
                        o.addSupport();
                        order.resolve();
                    }
                );
        } else {
            order.cut();
        }
    }

    private void resolveHoldOrder(Order order) {
        if (isDislodged(order)) {
            order.dislodge();
        } else {
            order.resolve();
        }
    }

    private void resolveMoveOrder(Order order) {
        if (isDestinationLocationOccupied(order.getToLocation())) {
            calculateStrengthForOrder(order);
            Optional<Order> conflictingOrder = getConflictingOrder(order);
            if (
                order.getStrength() > 1 &&
                    conflictingOrder.isEmpty() ||
                    (
                        conflictingOrder.isPresent() &&
                            order.getStrength() > conflictingOrder.get().getStrength()
                    )
            ) {
                order.resolve();
            } else if (isDislodged(order)) {
                order.dislodge();
            } else if (isDestinationLocationBeingVacated(order)) {
                order.resolve();
            } else {
                order.bounce();
                markSupportingUnitsAsFailed(order);
            }
        } else if (isDislodged(order)) {
            order.dislodge();
        } else {
            order.resolve();
            resultingUnitLocations.remove(order.getCurrentLocation());
            resultingUnitLocations.put(order.getToLocation(), order.getUnit());
        }
    }

    private boolean isDestinationLocationBeingVacated(Order order) {
        Optional<Order> vacatingOrder = orders
            .stream()
            .filter(o -> o.getOrderType().isMove())
            .filter(o -> o.getCurrentLocation().equals(order.getToLocation()))
            .filter(o -> !o.getToLocation().equals(order.getCurrentLocation()) || isConvoyPresent(o) || isConvoyPresent(order))
            .findAny()
            .map(this::resolveOrder);

        if (vacatingOrder.isPresent()) {
            var vo = vacatingOrder.get();
            if (vo.getStatus().isProcessing() || vo.getStatus().isResolved()) {
                vacatingOrder.get().resolve();
                return true;
            }
        }

        return false;
    }

    private boolean isConvoyPresent(Order order) {
        List<Order> convoyOrders = orders
            .stream()
            .filter(o -> o.getOrderType().isConvoy())
            .filter(o -> o.getFromLocation().equals(order.getFromLocation()))
            .filter(o -> o.getToLocation().equals(order.getToLocation()))
            .filter(this::isNotDislodged)
            .collect(Collectors.toList());
        if (convoyOrders.isEmpty()) {
            return false;
        } else {
            Set<Location> convoyLocations = convoyOrders.stream().map(Order::getCurrentLocation).collect(Collectors.toSet());
            if (pathExistsForConvoy(order.getFromLocation(), order.getToLocation(), convoyLocations)) {
                convoyOrders.forEach(Order::resolve);
            }
            return convoyOrders.stream().anyMatch(o -> o.getStatus().isResolved());
        }
    }

    private boolean pathExistsForConvoy(Location start, Location end, Set<Location> convoyLocations) {
        Queue<Location> locationQueue = new LinkedList<>();
        var pathExists = false;
        Set<Location> visitedLocations = new HashSet<>();
        locationQueue.add(start);
        visitedLocations.add(start);
        while (!locationQueue.isEmpty()) {
            var current = locationQueue.remove();
            if (mapVariant.getMovementGraph(UnitType.FLEET).get(current).contains(end)) {
                pathExists = true;
                break;
            }
            for (Location adjacentLocation : adjacencies.get(current)) {
                if (convoyLocations.contains(adjacentLocation) && !visitedLocations.contains(adjacentLocation)) {
                    locationQueue.add(adjacentLocation);
                    visitedLocations.add(adjacentLocation);
                }
            }
        }
        return pathExists;
    }

    private void markSupportingUnitsAsFailed(Order order) {
        orders.forEach(o -> {
            if (o.getOrderType().isSupport() &&
                !o.getStatus().isCut() &&
                o.getFromLocation().equals(order.getCurrentLocation()) &&
                o.getToLocation().equals(order.getToLocation())) {
                o.failed();
            }
        });
    }

    private Optional<Order> getConflictingOrder(Order order) {
        return orders
            .stream()
            .filter(o -> o.getOrderType().isMove())
            .filter(o -> o.getToLocation().equals(order.getCurrentLocation()))
            .map(this::calculateStrengthForOrder)
            .findAny();
    }

    private boolean isNotDislodged(Order order) {
        return !isDislodged(order);
    }

    private boolean isDislodged(Order order) {
        Optional<Order> dislodgeOrder = orders
            .stream()
            .filter(o -> o.getOrderType().isMove())
            .filter(o -> o.getToLocation().equals(order.getCurrentLocation()))
            .findAny();
        return dislodgeOrder
            .map(this::calculateStrengthForOrder)
            .filter(o -> o.getStrength() > order.getStrength())
            .isPresent();
    }

    private boolean isOrderForAdjacentLocations(Order order) {
        return adjacent(order.getCurrentLocation(), order.getFromLocation()) &&
            adjacent(order.getCurrentLocation(), order.getToLocation()) &&
            order.getToLocation().supports(order.getUnit());
    }

    private boolean adjacent(Location a, Location b) {
        return adjacencies.get(a).contains(b);
    }

    private Order calculateStrengthForOrder(Order order) {
        orders.forEach(o -> {
            if (o.getOrderType().isSupport() &&
                o.getFromLocation().equals(order.getCurrentLocation()) &&
                o.getToLocation().equals(order.getToLocation())) {
                resolveOrder(o);
            }
        });
        return order;
    }

    private boolean isDestinationLocationOccupied(Location location) {
        return startingUnitLocations.get(location) != null;
    }

    public void resolve() {

        Map<Location, Order> locationToOrderMap = orders
            .stream()
            .collect(Collectors.toMap(Order::getCurrentLocation, Function.identity()));
        startingUnitLocations.keySet().forEach(location -> {
            if (locationToOrderMap.get(location) == null) {
                addOrder(new Order(startingUnitLocations.get(location), location));
            }
        });
        orders.forEach(this::resolveOrder);
        orders.forEach(o -> System.out.println(o.getDescription()));
    }

    public Order getOrderById(UUID id) {
        return orders.stream().collect(Collectors.toMap(Order::getId, Function.identity())).get(id);
    }

    public List<Order> getOrdersByCountry(Country country) {
        return orders.stream().filter(order -> order.getCountry() == country).collect(Collectors.toList());
    }

    public PhaseName getPhaseName() {
        return this.phaseName;
    }
}
