package io.github.sroca3.diplomacy;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Phase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Phase.class);
    private final Map<Location, Unit> startingUnitLocations;
    private final Map<Location, Unit> resultingUnitLocations;
    private final Map<Location, Set<Location>> adjacencies;
    private final MapVariant mapVariant;
    private final List<Order> orders = new LinkedList<>();
    private final PhaseName phaseName;
    private final Map<Location, Country> locationOwnership = new HashMap<>();
    private final Map<Location, Dislodgement> dislodgedUnitLocations = new HashMap<>();
    private final Set<Location> contestedLocations = new HashSet<>();
    private final long year;
    public Phase(
        Map<Location, Unit> startingUnitLocations,
        MapVariant mapVariant,
        Map<Location, Set<Location>> adjacencies,
        PhaseName phaseName,
        final Map<Location, Country> locationOwnership,
        long year
    ) {
        this(
            startingUnitLocations,
            mapVariant,
            adjacencies,
            phaseName,
            locationOwnership,
            Collections.emptyMap(),
            Collections.emptySet(),
            year
        );
    }

    public Phase(
        final Map<Location, Unit> startingUnitLocations,
        final MapVariant mapVariant,
        final Map<Location, Set<Location>> adjacencies,
        final PhaseName phaseName,
        final Map<Location, Country> locationOwnership,
        final Map<Location, Dislodgement> dislodgedUnitLocations,
        final Set<Location> contestedLocations,
        final long year
    ) {
        this.startingUnitLocations = Map.copyOf(startingUnitLocations);
        this.resultingUnitLocations = new HashMap<>(startingUnitLocations);
        this.adjacencies = adjacencies;
        this.mapVariant = mapVariant;
        this.phaseName = phaseName;
        this.locationOwnership.putAll(locationOwnership);
        this.dislodgedUnitLocations.putAll(dislodgedUnitLocations);
        this.contestedLocations.addAll(contestedLocations);
        this.year = year;
    }

    public String getPhaseDescription() {
        PhaseName phaseName = getPhaseName();
        String[] phaseParts = phaseName.name().toLowerCase(Locale.ENGLISH).split("_");
        return String.join(" ", StringUtils.capitalize(phaseParts[0]), String.valueOf(year), StringUtils.capitalize(phaseParts[1]));
    }

    public Map<Location, Unit> getStartingUnitLocations() {
        return startingUnitLocations;
    }

    public Map<Location, Unit> getResultingUnitLocations() {
        return resultingUnitLocations;
    }

    public List<Order> getOrders() {
        return List.copyOf(orders);
    }

    public void addOrder(
        Order order
    ) {
        orders.add(order);
    }

    private Order resolveOrder(Order order) {
        if (!order.getOrderType().isBuild() && !order.getOrderType()
                                                     .isConvoy() && !isOrderForAdjacentLocations(order) && !isConvoyPresent(
            order)) {
            order.convertIllegalMoveToHold();
            if (isDislodged(order)) {
                order.dislodge();
            }
        }
        if (order.getStatus().isProcessing()) {
            order.resolve();
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
                case CONVOY:
                    resolveConvoyOrder(order);
                    break;
                case RETREAT:
                    resolveRetreatOrder(order);
                    break;
                case BUILD:
                    resolveBuildOrder(order);
                    break;
                case DISBAND:
                    resolveDisbandOrder(order);
                    break;
            }
        }

        return order;
    }

    private void resolveDisbandOrder(Order order) {
        order.resolve();
    }

    public long getSupplyCenterCount(Country country) {
        return locationOwnership.entrySet()
                                .stream()
                                .filter(entry -> entry.getValue().equals(country))
                                .filter(entry -> entry.getKey().isSupplyCenter())
                                .count();
    }

    public long getUnitCount(Country country) {
        return startingUnitLocations.values().stream().filter(unit -> unit.getCountry().equals(country)).count();
    }

    private void resolveBuildOrder(Order order) {
        if (!order.getCurrentLocation().supports(order.getUnit())) {
            order.failed();
        } else if (mapVariant.getHomeCenters()
                      .get(order.getCurrentLocation())
                      .equals(order.getCountry())
            && getSupplyCenterCount(order.getCountry()) > getUnitCount(order.getCountry())
            + orders.stream()
                    .filter(o -> o.getCountry().equals(order.getCountry()))
                    .filter(o -> o.getStatus().isResolved())
                    .count()
            && startingUnitLocations.get(order.getCurrentLocation()) == null) {
            order.resolve();
        } else {
            order.failed();
        }
    }

    private boolean unitRetreatingTowardsAttacker(Order order) {
        return Optional.ofNullable(dislodgedUnitLocations.get(order.getCurrentLocation()))
                       .map(Dislodgement::getAttackingOrder)
                .filter(o -> Objects.equals(order.getToLocation(), o.getFromLocation()))
                .isPresent();
    }

    private void resolveRetreatOrder(Order order) {
        if (!dislodgedUnitLocations.containsKey(order.getCurrentLocation())){
            order.markAsIllegal();
        } else if (competingRetreatExists(order)) {
            bounce(order);
        } else if (unitRetreatingTowardsAttacker(order) || contestedLocations.contains(order.getToLocation())){
            order.disband();
        }else {
            order.resolve();
        }
    }

    private void resolveConvoyOrder(Order order) {
        if (order.getFromLocation().equals(order.getToLocation())) {
            order.convertIllegalMoveToHold();
        } else {
            findBy(OrderType.MOVE, order.getToLocation())
                .ifPresentOrElse(o -> {
                    if (o.getStatus().isUnresolved()) {
                        this.resolveOrder(o);
                    }
                    if (o.getStatus().isIllegal()) {
                        order.convertIllegalMoveToHold();
                    }
                }, order::convertIllegalMoveToHold);
        }

        if (order.getStatus().isProcessing()) {
            order.convertIllegalMoveToHold();
            if (isDislodged(order)) {
                order.dislodge();
            }
        }
    }

    private Optional<Order> findBy(OrderType orderType, Location toLocation) {
        return orders
            .stream()
            .filter(order -> order.getOrderType() == orderType)
            .filter(order -> {
                if (order.getToLocation().getTerritory().hasCoasts()) {
                    return order.getToLocation().getTerritory().equals(toLocation) ||
                        order.getToLocation().getTerritory().getCoasts().contains(toLocation);
                }
                return order.getToLocation().equals(toLocation);
            })
            .findAny();
    }

    private Optional<Order> findByCurrentLocation(Location currentLocation) {
        return orders
            .stream()
            .filter(order -> order.getCurrentLocation() == currentLocation)
            .findAny();
    }

    private Optional<Order> findByTypeAndCurrentLocation(OrderType orderType, Location currentLocation) {
        return orders
            .stream()
            .filter(order -> order.getOrderType() == orderType)
            .filter(order -> order.getCurrentLocation() == currentLocation)
            .findAny();
    }

    private Optional<Order> findBy(OrderType orderType, Location fromLocation, Location toLocation) {
        return orders
            .stream()
            .filter(order -> order.getOrderType() == orderType)
            .filter(order -> order.getFromLocation() == fromLocation)
            .filter(order -> {
                if (order.getToLocation().getTerritory().hasCoasts()) {
                    return order.getToLocation().getTerritory().equals(toLocation) ||
                        order.getToLocation().getTerritory().getCoasts().contains(toLocation);
                }
                return order.getToLocation().equals(toLocation);
            })
            .findAny();
    }

    private List<Order> findAllBy(OrderType orderType, Location toLocation) {
        return orders
            .stream()
            .filter(order -> order.getOrderType() == orderType)
            .filter(order -> {
                if (order.getToLocation().getTerritory().hasCoasts()) {
                    return order.getToLocation().getTerritory().equals(toLocation) ||
                        order.getToLocation().getTerritory().getCoasts().contains(toLocation);
                }
                return order.getToLocation().equals(toLocation);
            })
            .collect(Collectors.toList());
    }

    private List<Order> findAllBy(OrderType orderType, Location fromLocation, Location toLocation) {
        return orders
            .stream()
            .filter(order -> order.getOrderType() == orderType)
            .filter(order -> order.getFromLocation() == fromLocation)
            .filter(order -> {
                if (order.getToLocation().getTerritory().hasCoasts()) {
                    return order.getToLocation().getTerritory().equals(toLocation) ||
                        order.getToLocation().getTerritory().getCoasts().contains(toLocation);
                }
                return order.getToLocation().equals(toLocation);
            })
            .collect(Collectors.toList());
    }

    private void addSupport(Order order, Order supportOrder) {
        findByCurrentLocation(order.getToLocation())
            .ifPresentOrElse(o -> {
                if (!(order.getOrderType().isMove() && o.getCountry().equals(supportOrder.getCountry()))) {
                    order.addSupport();
                }
            }, order::addSupport);
    }

    private void resolveSupportOrder(Order order) {
        if (findBy(OrderType.MOVE, order.getCurrentLocation()).isEmpty()) {
            findBy(OrderType.MOVE, order.getFromLocation(), order.getToLocation())
                .ifPresent(o -> {
                    if (o.getStatus().isIllegal()) {
                        order.failed();
                    } else if (order.getToLocation().isCoast()
                        && o.getToLocation().isCoast()
                        && !order.getToLocation().equals(o.getToLocation())) {
                        order.failed();
                    } else {
                        addSupport(o, order);
                        order.resolve();
                    }
                    }
                );
            findBy(OrderType.HOLD, order.getFromLocation(), order.getToLocation())
                .ifPresent(o -> {
                    addSupport(o, order);
                    order.resolve();
                });
            findByTypeAndCurrentLocation(OrderType.SUPPORT, order.getToLocation())
                .ifPresent(o -> {
                    if (order.getFromLocation() == o.getCurrentLocation()) {
                        addSupport(o, order);
                        order.resolve();
                    }
                });
            findByTypeAndCurrentLocation(OrderType.CONVOY, order.getToLocation())
                .ifPresent(o -> {
                    if (order.getFromLocation() == order.getToLocation()) {
                        addSupport(o, order);
                        order.resolve();
                    }
                });
        } else if (isDislodged(order)) {
            order.dislodge();
        } else {
            boolean cutOrderExists = findAllBy(OrderType.MOVE, order.getCurrentLocation())
                .stream().anyMatch(o -> !o.getCurrentLocation().equals(order.getToLocation()));
            if (cutOrderExists) {
                order.cut();
            } else {
                findBy(OrderType.MOVE, order.getToLocation()).ifPresent(o2 -> addSupport(o2, order));
                order.resolve();
            }
        }

        if (order.getStatus().isProcessing()) {
            order.convertIllegalMoveToHold();
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
        if (order.getFromLocation().equals(order.getToLocation())) {
            order.convertIllegalMoveToHold();
        } else if (isConvoyPresent(order) && !isConvoySuccessful(order)) {
            order.failed();
        } else if (isDestinationLocationOccupied(order.getToLocation())) {
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
                findBy(OrderType.HOLD, order.getToLocation())
                    .ifPresentOrElse(o -> {
                        calculateStrengthForOrder(o);
                        if (o.getStrength() >= order.getStrength() || o.getCountry().equals(order.getCountry())) {
                            bounce(order);
                        } else {
                            order.resolve();
                        }
                    }, order::resolve);
                findByTypeAndCurrentLocation(OrderType.SUPPORT, order.getToLocation())
                    .ifPresent(o -> {
                        calculateStrengthForOrder(o);
                        if (o.getStrength() >= order.getStrength()) {
                            bounce(order);
                        } else {
                            order.resolve();
                        }
                    });
                findByTypeAndCurrentLocation(OrderType.CONVOY, order.getToLocation())
                    .ifPresent(o -> {
                        calculateStrengthForOrder(o);
                        if (o.getStrength() >= order.getStrength()) {
                            bounce(order);
                        } else {
                            order.resolve();
                        }
                    });
                findByTypeAndCurrentLocation(OrderType.MOVE, order.getToLocation())
                    .ifPresent(o -> {
                        if (o.getStatus().isBounced() && o.getCountry().equals(order.getCountry())) {
                            bounce(order);
                        }
                    });
            } else if (conflictingOrder.isEmpty() && isDestinationLocationBeingVacated(order)) {
                order.resolve();
            } else if (isDislodged(order)) {
                order.dislodge();
            } else {
                bounce(order);
            }
        } else if (isDislodged(order)) {
            Map<String, String> x = new HashMap<>();
            if (!competingMovesExist(order)) {
                dislodgedUnitLocations.remove(order.getCurrentLocation());
                order.resolve();
            } else {
                order.dislodge();
            }
        } else if (competingMovesExist(order)){
            calculateStrengthForOrder(order);
            getConflictingOrders(order)
                .stream()
                .filter(o -> o.getStrength() >= order.getStrength())
                .findAny()
                .ifPresentOrElse(o -> {
                    bounce(order);
                }, order::resolve);
        } else {
            order.resolve();
        }
    }

    private boolean competingMovesExist(Order order) {
        return orders
            .stream()
            .filter(o -> o.getToLocation().getTerritory().equals(order.getToLocation().getTerritory()))
            .filter(o -> o.getOrderType().isMove())
            .filter(o -> !o.getStatus().isIllegal())
            .count() > 1;
    }

    private boolean competingRetreatExists(Order order) {
        return orders
            .stream()
            .filter(o -> o.getId() != order.getId())
            .filter(o -> o.getToLocation().getTerritory().equals(order.getToLocation().getTerritory()))
            .anyMatch(o -> o.getOrderType().isRetreat());
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
            if (vo.getStatus().isResolved()) {
                vacatingOrder.get().resolve();
                return true;
            }
        }

        return false;
    }

    private boolean isConvoyPresent(Order order) {
        if (order.getUnit().getType().isFleet()) {
            return false;
        }
        return orders
            .stream()
            .filter(o -> o.getOrderType().isConvoy())
            .filter(o -> o.getFromLocation().equals(order.getFromLocation()))
            .anyMatch(o -> o.getToLocation().equals(order.getToLocation()));
    }

    private boolean isConvoySuccessful(Order order) {
        if (order.getUnit().getType().isFleet()) {
            return false;
        }
        List<Order> convoyOrders = orders
            .stream()
            .filter(o -> o.getOrderType().isConvoy())
            .filter(o -> o.getFromLocation().equals(order.getFromLocation()))
            .filter(o -> o.getToLocation().equals(order.getToLocation()))
            .filter(o -> {
                if (this.isNotDislodged(o)) {
                    return true;
                } else {
                    o.dislodge();
                    return false;
                }
            })
            .collect(Collectors.toList());
        if (convoyOrders.isEmpty()) {
            return false;
        } else {
            Set<Location> convoyLocations = convoyOrders.stream().map(Order::getCurrentLocation).collect(Collectors.toSet());
            if (pathExistsForConvoy(order.getFromLocation(), order.getToLocation(), convoyLocations)) {
                convoyOrders.forEach(Order::resolve);
            } else {
                convoyOrders.forEach(Order::failed);
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
        while (!locationQueue.isEmpty() && !pathExists) {
            var current = locationQueue.remove();
            if (current.hasCoasts()) {
                current.getCoasts().forEach(locationQueue::add);
                current = locationQueue.remove();
            } else {
                if (mapVariant.getMovementGraph(UnitType.FLEET).get(current).contains(end)) {
                    pathExists = true;
                    break;
                }
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
        findAllBy(OrderType.SUPPORT, order.getCurrentLocation(), order.getToLocation()).forEach(o -> {
            if (!o.getStatus().isCut() && !o.getStatus().isIllegal()) {
                o.failed();
            }
        });
    }

    private Optional<Order> getConflictingOrder(Order order) {
        List<Order> orders = getConflictingOrders(order);
        if (orders.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(orders.get(0));
        }
    }

    private List<Order> getConflictingOrders(Order order) {
        return orders
            .stream()
            .filter(o -> o.getOrderType().isMove())
            .filter(o -> {
                if (o.getToLocation().getTerritory().hasCoasts()) {
                    return o.getToLocation().getTerritory().equals(order.getToLocation()) ||
                        o.getToLocation().getTerritory().getCoasts().contains(order.getToLocation());
                }
                return o.getToLocation().equals(order.getToLocation());
            })
            .filter(o -> !o.getId().equals(order.getId()))
            .map(this::calculateStrengthForOrder)
            .collect(Collectors.toList());
    }

    private boolean isNotDislodged(Order order) {
        return !isDislodged(order);
    }

    private boolean isDislodged(Order order) {
        calculateStrengthForOrder(order);
        Optional<Order> dislodgeOrder = findAllBy(OrderType.MOVE, order.getCurrentLocation())
            .stream()
            .map(this::calculateStrengthForOrder)
            .filter(o -> !o.getCountry().equals(order.getCountry()))
            .filter(o -> o.getStrength() > order.getStrength())
            .findAny();
        dislodgeOrder.ifPresent(o -> dislodgedUnitLocations.put(order.getCurrentLocation(), new Dislodgement(o, order)));
        return dislodgeOrder.isPresent();
    }

    private boolean isOrderForAdjacentLocations(Order order) {
        if (order.getOrderType().isSupport() && order.getToLocation().hasCoasts()) {
            findBy(OrderType.MOVE, order.getToLocation()).ifPresent(o -> order.specifyCoast(o.getToLocation()));
        }

        if (order.getUnit().getType().isFleet()
            && order.getOrderType().isSupport()
            && order.getToLocation().getTerritory().hasCoasts()
        ) {
            return order.getToLocation().getTerritory().getCoasts().stream().anyMatch(location ->
                adjacent(
                    mapVariant.getMovementGraph(order.getUnit().getType()),
                    order.getCurrentLocation(),
                    location
                )
            );
        }
        return adjacent(
            mapVariant.getMovementGraph(order.getUnit().getType()),
            order.getCurrentLocation(),
            order.getToLocation()
        ) &&
            order.getToLocation().supports(order.getUnit());
    }

    private boolean adjacent(Map<Location, Set<Location>> adjacencies, Location a, Location b) {
        return adjacencies.get(a) != null && adjacencies.get(a).contains(b);
    }

    private Order calculateStrengthForOrder(Order order) {
        if (order.getOrderType().isSupport() || order.getOrderType().isConvoy()) {
            findAllBy(
                OrderType.SUPPORT,
                order.getCurrentLocation(),
                order.getCurrentLocation()
            ).forEach(this::resolveOrder);
        } else {
            findAllBy(OrderType.SUPPORT, order.getFromLocation(), order.getToLocation()).forEach(this::resolveOrder);
        }
        return order;
    }

    private void bounce(Order order) {
        order.bounce();
        contestedLocations.add(order.getToLocation());
        markSupportingUnitsAsFailed(order);
    }
    
    private boolean isDestinationLocationOccupied(Location location) {
        Location l = location.getTerritory();
        return startingUnitLocations.get(l) != null || l.getCoasts()
                                                        .stream()
                                                        .map(startingUnitLocations::get)
                                                        .anyMatch(Objects::nonNull);
    }

    public void resolve() {
        Map<Location, Order> locationToOrderMap = orders
            .stream()
            .collect(Collectors.toMap(Order::getCurrentLocation, Function.identity()));
        if (phaseName.isOrderPhase()) {
            dislodgedUnitLocations.clear();
            startingUnitLocations.keySet().forEach(location -> {
                if (locationToOrderMap.get(location) == null) {
                    addOrder(new Order(startingUnitLocations.get(location), location));
                }
            });
        }

        if (phaseName.isRetreatPhase()) {
            dislodgedUnitLocations.keySet().forEach(location -> {
                if (locationToOrderMap.get(location) == null) {
                    addOrder(new Order(dislodgedUnitLocations.get(location).getDislodgedOrder().getUnit(), location, OrderType.DISBAND));
                }
            });
        }

        if (getPhaseName().isRetreatPhase()) {
            orders.removeAll(orders.stream()
                                   .filter(o -> !Set.of(OrderType.RETREAT, OrderType.DISBAND)
                                                    .contains(o.getOrderType()))
                                   .collect(Collectors.toList()));
        }
        orders.forEach(this::resolveOrder);
        orders.forEach(this::updateLocations);
        orders.forEach(o -> LOGGER.debug(o.getDescription()));
    }

    private void updateLocations(Order order) {
        if (order.getOrderType().isMove() && order.getStatus().isResolved()) {
            Unit unit = resultingUnitLocations.get(order.getCurrentLocation());
            if (unit != null && unit.getId().equals(order.getUnit().getId())) {
                resultingUnitLocations.remove(order.getCurrentLocation());
            }
            resultingUnitLocations.put(order.getToLocation(), order.getUnit());
        }

        if (order.getOrderType().isRetreat() && order.getStatus().isBounced()) {
            resultingUnitLocations.remove(order.getCurrentLocation());
        }

        if (order.getOrderType().isRetreat() && order.getStatus().isResolved()) {
            resultingUnitLocations.put(order.getToLocation(), order.getUnit());
        }

        if (order.getOrderType().isBuild() && order.getStatus().isResolved()) {
            LOGGER.debug("Build unit in {}", order.getCurrentLocation());
            resultingUnitLocations.put(order.getCurrentLocation(), order.getUnit());
        }

        if (order.getOrderType().isDisband() && phaseName.isBuildPhase()) {
            resultingUnitLocations.remove(order.getCurrentLocation());
        }
    }

    public Order getOrderById(UUID id) {
        return orders.stream().collect(Collectors.toMap(Order::getId, Function.identity())).get(id);
    }

    public List<Order> getOrdersByCountry(Country country) {
        return orders.stream()
                     .filter(order -> order.getCountry() == country)
                     .collect(Collectors.toList());
    }

    public PhaseName getPhaseName() {
        return this.phaseName;
    }

    public Map<Location, Dislodgement> getDislodgedInfo() {
        return dislodgedUnitLocations;
    }

    public Set<Location> getContestedLocations() {
        return contestedLocations;
    }
}
