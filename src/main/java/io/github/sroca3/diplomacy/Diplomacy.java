package io.github.sroca3.diplomacy;

import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyLocation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Diplomacy {

    private final MapVariant mapVariant;
    private final Map<Location, Unit> unitLocations = new HashMap<>();
    private final Map<Location, Country> locationOwnership = new HashMap<>();
    private Phase currentPhase;
    private Phase previousPhase;
    private long gameYearCounter;

    public Diplomacy(MapVariant mapVariant) {
        this.mapVariant = mapVariant;
    }

    public MapVariant getMapVariant() {
        return mapVariant;
    }

    public Map<Location, Unit> getUnitLocations() {
        return unitLocations;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void addUnit(Location location, Unit unit) {
        if (!mapVariant.getCountries().contains(unit.getCountry())) {
            throw new IllegalArgumentException("Unit does not correspond to country available for map variant.");
        }
        if (!location.supports(unit)) {
            throw new IllegalArgumentException("Territory does not support unit type.");
        }
        unitLocations.put(location, unit);
    }

    public void addUnitAndClaimLocation(Location location, Unit unit) {
        addUnit(location, unit);
        locationOwnership.put(location, unit.getCountry());
    }

    public void beginFirstPhase() {
        currentPhase = new Phase(unitLocations, mapVariant, mapVariant.getMovementGraph(), getNextPhaseName());
    }

    public void addOrders(List<Order> orders) {
        orders.forEach(this::addOrder);
    }

    public void addOrder(Order order) {
        validateUnitOwnership(order.getCountry(), order.getCurrentLocation());
        currentPhase.addOrder(new Order(order));
    }

    private void validateUnitOwnership(Country country, Location source) {
        Optional.ofNullable(unitLocations.get(source))
                .filter(unit -> unit.getCountry() == country)
                .orElseThrow(UnitOwnershipException::new);
    }

    public void adjudicate() {
        currentPhase.resolve();
        PhaseName nextPhaseName = getNextPhaseName();
        currentPhase.getResultingUnitLocations().forEach(((location, unit) -> {
            if (nextPhaseName == PhaseName.FALL_ORDERS && !location.isSupplyCenter()) {
                locationOwnership.put(location, unit.getCountry());
            } else if (nextPhaseName == PhaseName.WINTER_BUILD) {
                locationOwnership.put(location, unit.getCountry());
            }
        }));
        previousPhase = currentPhase;
        if (previousPhase.getPhaseName() == PhaseName.WINTER_BUILD) {
            gameYearCounter += 1;
        }
        currentPhase = new Phase(
            currentPhase.getResultingUnitLocations(),
            mapVariant,
            mapVariant.getMovementGraph(),
            nextPhaseName
        );
    }

    private PhaseName getNextPhaseName() {
        if (currentPhase != null) {
            PhaseName phaseName = currentPhase.getPhaseName();
            if (phaseName == PhaseName.SPRING_ORDERS) {
                return PhaseName.FALL_ORDERS;
            } else if (phaseName == PhaseName.FALL_ORDERS) {
                return PhaseName.WINTER_BUILD;
            } else if (phaseName == PhaseName.WINTER_BUILD) {
                return PhaseName.SPRING_ORDERS;
            }
        }
        return PhaseName.SPRING_ORDERS;
    }

    public Phase getPreviousPhase() {
        return previousPhase;
    }

    private Location getLocation(Iterator<String> iterator) {
        Location location = null;
        String locationString = null;
        while (location == null && iterator.hasNext()) {
            if (locationString == null) {
                locationString = iterator.next().toUpperCase();
            } else {
                locationString = String.join("_", locationString, iterator.next().toUpperCase());
            }

            if ("-".equals(locationString) || UnitType.from(locationString) != null) {
                locationString = null;
            }
            location = mapVariant.parseLocation(locationString);
        }
        return location;
    }

    public Order parseOrder(String orderString) {
        List<String> parts = Arrays.asList(orderString.split("\\s+"));
        Iterator<String> iterator = parts.iterator();
        UnitType unitType = UnitType.from(iterator.next());
        Location currentLocation = getLocation(iterator);
        String orderTypeString = iterator.next().toUpperCase(Locale.ENGLISH);
        OrderType orderType = OrderType.from(orderTypeString);
        Location fromLocation;
        Location toLocation;
        if (orderType.isSupport() || orderType.isConvoy()) {
            fromLocation = getLocation(iterator);
            toLocation = getLocation(iterator);
            if (orderType.isSupport() && toLocation == null) {
                toLocation = fromLocation;
            }
        } else {
            fromLocation = currentLocation;
            toLocation = getLocation(iterator);
        }
        Unit unit = unitLocations.get(currentLocation);
        if (unit.getType() != unitType) {
            throw new IllegalArgumentException();
        }
        return new Order(unit, currentLocation, orderType, fromLocation, toLocation);
    }

    public List<Order> parseOrders(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<Order> orders = new LinkedList<>();
        lines.forEach(line -> {
            try {
                orders.add(this.parseOrder(line));
            } catch (Exception e) {
                System.out.println("Skipping line: " + line);
            }
        });
        return orders;
    }

    public void addStandardStartingUnits() {
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.SALTA, new Army(Country.ARGENTINA));
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.CORDOBA, new Army(Country.ARGENTINA));
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.BUENOS_AIRES, new Fleet(Country.ARGENTINA));

        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.ANTOFAGASTA, new Fleet(Country.BOLIVIA));
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.LA_PAZ, new Army(Country.BOLIVIA));

        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.SALVADOR, new Fleet(Country.BRASIL));
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.SAO_PAULO, new Fleet(Country.BRASIL));
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.RIO_DE_JANEIRO, new Army(Country.BRASIL));
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.MANAUS, new Army(Country.BRASIL));

        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.VALPARAISO, new Fleet(Country.CHILE));
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.SANTIAGO, new Army(Country.CHILE));

        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.CARTAGENA, new Fleet(Country.COLOMBIA));
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.CALI, new Fleet(Country.COLOMBIA));
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.BOGOTA, new Army(Country.COLOMBIA));

        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.ASUNCION, new Army(Country.PARAGUAY));
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.CONCEPCION, new Army(Country.PARAGUAY));

        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.LIMA, new Fleet(Country.PERU));
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.TRUJILLO, new Army(Country.PERU));

        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.CARACAS, new Fleet(Country.VENEZUELA));
        addUnitAndClaimLocation(SouthAmericanSupremacyLocation.MARACAIBO, new Army(Country.VENEZUELA));
    }

    public long getSupplyCenterCount(Country country) {
        return locationOwnership.entrySet()
                                .stream()
                                .filter(entry -> entry.getValue().equals(country))
                                .filter(entry -> entry.getKey().isSupplyCenter())
                                .count();
    }

    public long getUnitCount(Country country) {
        return unitLocations.values().stream().filter(unit -> unit.getCountry().equals(country)).count();
    }

    public long getYear() {
        return mapVariant.getStartingYear() + gameYearCounter;
    }

    public String getPhaseName() {
        return currentPhase.getPhaseName().name();
    }
}
