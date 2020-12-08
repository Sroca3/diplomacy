
package io.github.sroca3.diplomacy;

import io.github.sroca3.diplomacy.exceptions.CountryOrderMismatchException;
import io.github.sroca3.diplomacy.exceptions.LocationNotFoundException;
import io.github.sroca3.diplomacy.exceptions.OrderTypeParseException;
import io.github.sroca3.diplomacy.exceptions.UnitTypeMismatchException;
import io.github.sroca3.diplomacy.svg.SouthAmericanSupremacyMap;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Diplomacy {

    private static final Logger LOGGER = LoggerFactory.getLogger(Diplomacy.class);
    private static final String UNIT_TYPE_REGEX_STRING = "(ARMY|FLEET| A | F\\b|^A |^F )";
    private static final Pattern UNIT_TYPE_REGEX = Pattern.compile(UNIT_TYPE_REGEX_STRING);
    private static final String UNIT_TYPE_REGEX_FOR_BUILD_STRING = " (ARMY|FLEET|A|F) ";
    private static final Pattern UNIT_TYPE_FOR_BUILD_REGEX = Pattern.compile(UNIT_TYPE_REGEX_FOR_BUILD_STRING);
    private static final String ORDER_TYPE_REGEX_STRING = "( R |^BUILD |^DISBAND|^DESTROY |^REMOVE | MOVE TO | MOVES TO | HOLD$| HOLDS$| -> | - | TO | MOVE | RETREAT TO | RETREAT | RETREATS TO | SUPPORT | CONVOY | CONVOYS | SUPPORTS | S | H$)";
    private static final Pattern ORDER_TYPE_REGEX = Pattern.compile(ORDER_TYPE_REGEX_STRING);
    private final MapVariant mapVariant;
    private final Set<RuleVariant> ruleVariants;
    private final Map<Location, Unit> unitLocations = new HashMap<>();
    private final Map<Location, Country> locationOwnership = new HashMap<>();
    private Phase currentPhase;
    private Phase previousPhase;
    Map<Country, String> playerAssignments;
    Map<String, Map<Country, String>> replacementPlayers = new HashMap<>();
    private long gameYearCounter;
    private String baseDirectory;
    private boolean autoGenerateMaps;

    public Diplomacy(MapVariant mapVariant) {
        this(mapVariant, Collections.emptySet());
    }

    public Diplomacy(MapVariant mapVariant, Set<RuleVariant> ruleVariants) {
        this.mapVariant = mapVariant;
        this.ruleVariants = Set.copyOf(ruleVariants);
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public Map<Country, String> assignAndSaveCountries(List<String> players) throws IOException {
        if (StringUtils.isBlank(baseDirectory)) {
            throw new IllegalArgumentException("base directory not set.");
        }
        Map<Country, String> assignments = assignCountries(List.of(
            "AKFD",
            "Jordan767",
            "FloridaMan",
            "Don Juan of Austria",
            "VGhost",
            "bratsffl",
            "Antigonos",
            "RolynTrotter"
        ));

        File countryAssignments =
            Paths.get(baseDirectory + "/country_assignments.txt").toFile();
        boolean assign = countryAssignments.createNewFile();
        if (assign) {
            try (FileWriter writer = new FileWriter(countryAssignments)) {
                writer.write("Country Assignments:\n");
                assignments.entrySet()
                           .stream()
                           .sorted(Comparator.comparing(entry -> entry.getKey().getName()))
                           .forEach(entry -> {
                               try {
                                   writer.write(entry.getKey().getName() + " assigned to " + entry.getValue() + "\n");
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           });
            }
        } else {
            Map<Country, String> playerAssignments;
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(countryAssignments))) {
                // Skip preamble
                bufferedReader.readLine();
                playerAssignments = new HashMap<>();
                for (Country country : getMapVariant().getCountries()) {
                    String line = bufferedReader.readLine();
                    String player = line.substring(line.lastIndexOf(" to ") + 4);
                    playerAssignments.put(country, player);
                }
            }
            setPlayerAssignments(playerAssignments);
        }
        return playerAssignments;
    }

    public void generateStatus(int counter) throws IOException {
        String filePrefix = getYear() + File.separator + getFileName(counter);
        SortedSet<Country> countries = getMapVariant().getCountries();
        File statusFile = Paths.get(this.baseDirectory + "/" + filePrefix + "_Status.txt").toFile();
        if (statusFile.exists()) {
            Files.delete(statusFile.toPath());
        }
        File parentDirectory = statusFile.getParentFile();
        if (!parentDirectory.exists()) {
            Files.createDirectory(parentDirectory.toPath());
        }
        statusFile.createNewFile();
        try (FileWriter writer = new FileWriter(statusFile)) {
            writer.write(getPhaseDescription() + " Status\n");
            writer.write("--------------------------------\n");
            countries.forEach(
                country -> {
                    if (getSupplyCenterCount(country) > 0 || getUnitCount(country) > 0) {
                        try {
                            writer.write("\n");
                            writer.write(country.getName() + "\n");
                            writer.write("(" + getPlayer(country) + ")\n");
                            writer.write(getArmyCount(country) + " army units\n");
                            writer.write(getFleetCount(country) + " fleet units\n");
                            writer.write(getSupplyCenterCount(country) + " centers\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            );
        }
    }

    public String getFileName(int counter) {
        String fileNumber = StringUtils.leftPad(String.valueOf(counter), 2, '0');
        String filePhaseName = getPhaseDescription().replace(" ", "_");
        return String.join("_", fileNumber, filePhaseName);
    }

    public String getFileNameForPreviousPhase(int counter) {
        String fileNumber = StringUtils.leftPad(String.valueOf(counter), 2, '0');
        String filePhaseName = getPreviousPhase().getPhaseDescription().replace(" ", "_");
        return String.join("_", fileNumber, filePhaseName);
    }

    public Map<Location, Country> getLocationOwnership() {
        return locationOwnership;
    }

    public Map<Country, String> assignCountries(List<String> playersArg) {
        List<String> players = new LinkedList<>(playersArg);
        List<Country> countries = new LinkedList<>(getMapVariant().getCountries());
        if (players.size() != countries.size()) {
            throw new IllegalArgumentException("Not enough or too many players for variant. " +
                "Need " + mapVariant.getCountries().size() + " players.");
        }
        Map<Country, String> assignments = new HashMap<>();
        while (!countries.isEmpty()) {
            assignments.put(
                countries.remove(new Random().nextInt(countries.size())),
                players.remove(new Random().nextInt(players.size()))
            );
        }
        playerAssignments = assignments;
        return assignments;
    }

    public void setPlayerAssignments(Map<Country, String> playerAssignments) {
        this.playerAssignments = playerAssignments;
    }

    public void replaceCountry(Country country, String player) {
        this.playerAssignments.put(country, player);
    }

    public void replaceCountry(PhaseName phase, int year, Country country, String player) {
        String key = String.valueOf(phase) + year;

        if (!this.replacementPlayers.containsKey(key)) {
            this.replacementPlayers.put(key, new HashMap<>());
        }

        this.replacementPlayers.get(phase.name() + year).put(country, player);
    }

    public String getPlayer(Country country) {
        return playerAssignments.get(country);
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
            throw new IllegalArgumentException(String.format(
                "Territory, %s, does not support unit type, %s.",
                location.getName(),
                unit.getType().name()
            ));
        }
        unitLocations.put(location, unit);
    }

    public void addUnitAndClaimLocation(Location location, Unit unit) {
        addUnit(location, unit);
        locationOwnership.put(location.getTerritory(), unit.getCountry());
    }

    public void beginFirstPhase() {
        currentPhase = new Phase(unitLocations, mapVariant, mapVariant.getMovementGraph(), getNextPhaseName(), locationOwnership, getYear());
    }

    public boolean isFirstPhase() {
        return previousPhase == null;
    }

    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public void addOrders(List<Order> orders) {
        orders.forEach(this::addOrder);
    }

    public void addOrder(Order order) {
        if (!order.getOrderType().isBuild() && !order.getOrderType().isRetreat() && !order.getOrderType().isDisband()) {
            validateUnitOwnership(order.getCountry(), order.getCurrentLocation());
        }
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
            if ((nextPhaseName == PhaseName.FALL_ORDERS && !location.isSupplyCenter())
                || nextPhaseName == PhaseName.WINTER_BUILD) {
                locationOwnership.put(location.getTerritory(), unit.getCountry());
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
            nextPhaseName,
            locationOwnership,
            previousPhase.getDislodgedInfo(),
            previousPhase.getContestedLocations(),
            getYear()
        );
        unitLocations.clear();
        unitLocations.putAll(currentPhase.getResultingUnitLocations());
    }

    private PhaseName getNextPhaseName() {
        boolean retreatsPresent = getCurrentPhase() != null && getCurrentPhase()
            .getOrders()
            .stream()
            .filter(o -> o.getStatus().isDislodged())
            .anyMatch(obj -> true);
        if (currentPhase != null) {
            PhaseName phaseName = currentPhase.getPhaseName();
            if (phaseName == PhaseName.SPRING_ORDERS || phaseName.isSpringRetreat()) {
                if (retreatsPresent) {
                    return PhaseName.SPRING_RETREAT;
                }
                return PhaseName.FALL_ORDERS;
            } else if (phaseName == PhaseName.FALL_ORDERS || phaseName.isFallRetreat()) {
                if (retreatsPresent) {
                    return PhaseName.FALL_RETREAT;
                }
                return PhaseName.WINTER_BUILD;
            } else if (phaseName == PhaseName.WINTER_BUILD) {
                return PhaseName.SPRING_ORDERS;
            }
        }
        return PhaseName.SPRING_ORDERS;
    }

    public String getPhaseDescription() {
        PhaseName phaseName = currentPhase.getPhaseName();
        String[] phaseParts = phaseName.name().toLowerCase(Locale.ENGLISH).split("_");
        return String.join(" ", StringUtils.capitalize(phaseParts[0]), String.valueOf(getYear()), StringUtils.capitalize(phaseParts[1]));
    }

    public Phase getPreviousPhase() {
        return previousPhase;
    }

    private Location parseLocation(String locationString) {
        return Optional.ofNullable(locationString)
                       .map(String::toUpperCase)
                       .map(String::trim)
                       .map(l -> l.replace("-", "_"))
                       .map(l -> l.replace(" ", "_"))
                       .map(l -> l.replace("(", "_"))
                       .map(l -> l.replace(")", ""))
                       .map(mapVariant::parseLocation)
                       .orElse(null);
    }

    public Order parseOrder(final String orderInput, @Nonnull final Country country) {
        String order = orderInput.toUpperCase(Locale.ENGLISH);
        if (order.startsWith("BUILD")) {
            Matcher unitTypeMatcher = UNIT_TYPE_FOR_BUILD_REGEX.matcher(String.copyValueOf(order.toCharArray()));
            Location currentLocation = parseLocation(RegExUtils.removeFirst(
                order.substring(5),
                UNIT_TYPE_REGEX_FOR_BUILD_STRING
            ).trim());
            if (unitTypeMatcher.find()) {
                UnitType unitType = UnitType.from(unitTypeMatcher.group().trim());
                if (UnitType.ARMY.equals(unitType)) {
                    return new Order(new Army(country), currentLocation, OrderType.BUILD);
                } else {
                    return new Order(new Fleet(country), currentLocation, OrderType.BUILD);
                }
            }
        }
        Order o = this.parseOrder(orderInput);
        if (!country.equals(o.getUnit().getCountry())) {
            throw new CountryOrderMismatchException();
        }
        return o;
    }

    private String preProcessOrderInput(String orderInput) {
        String order = orderInput.toUpperCase().replaceAll("\\s+", " ");
        for(var coast: Set.of("NC", "SC", "EC", "WC")) {
            order = order.replaceAll("[\\s]*\\([" + coast + "]{2}\\)", "_" + coast);
        }
        order = order.replaceAll("[()]", "");
        return order;
    }

    public Order parseOrder(final String orderInput) {
        String order = preProcessOrderInput(orderInput);
        Matcher orderTypeMatcher = ORDER_TYPE_REGEX.matcher(String.copyValueOf(order.toCharArray()));
        Matcher unitTypeMatcher = UNIT_TYPE_REGEX.matcher(String.copyValueOf(order.toCharArray()));
        String[] parts = RegExUtils.removeFirst(order, UNIT_TYPE_REGEX_STRING).split(ORDER_TYPE_REGEX_STRING);
        OrderType orderType;
        if (orderTypeMatcher.find()) {
            orderType = OrderType.from(orderTypeMatcher.group().trim(), currentPhase.getPhaseName());
        } else {
            throw new OrderTypeParseException(orderInput);
        }
        Location currentLocation;
        try {
            currentLocation = Optional.ofNullable(parseLocation(parts[orderType.isDisband() ? 1 : 0]))
                                               .orElseThrow(() -> new LocationNotFoundException(parts[0], orderInput));
        }catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }
        UnitType unitType;
        if (unitTypeMatcher.find()) {
            unitType = UnitType.from(unitTypeMatcher.group().trim());
        } else {
            unitType = unitLocations.get(currentLocation).getType();
        }
        if ((currentLocation.isCoast() || currentLocation.hasCoasts()) && unitLocations.get(currentLocation) == null) {
            Optional<Location> location = currentLocation.getTerritory().getCoasts()
                                                         .stream()
                                                         .filter(l -> unitLocations.get(l) != null)
                                                         .findAny();
            if (location.isPresent()) {
                currentLocation = location.get();
            }
        }
        Location fromLocation;
        Location toLocation;
        if (orderType.isSupport() || orderType.isConvoy()) {
            fromLocation = Optional.ofNullable(parseLocation(RegExUtils.removeFirst(parts[1], UNIT_TYPE_REGEX_STRING)))
                                   .orElseThrow(() -> new LocationNotFoundException(parts[1], orderInput));
            if (parts.length > 2) {
                toLocation = Optional.ofNullable(parseLocation(parts[2]))
                                     .orElse(null);
            } else {
                toLocation = fromLocation;
            }
            if (orderType.isSupport() && toLocation == null) {
                toLocation = fromLocation;
            }
        } else if (orderType.isHold() || orderType.isDisband()) {
            fromLocation = currentLocation;
            toLocation = currentLocation;
        } else {
            fromLocation = currentLocation;
            toLocation = Optional.ofNullable(parseLocation(parts[1]))
                                 .orElseThrow(() -> new LocationNotFoundException(parts[1], orderInput));
        }
        if (fromLocation == null) {
            throw new LocationNotFoundException("", orderInput);
        }
        if (UnitType.ARMY.equals(unitType)) {
            currentLocation = currentLocation.getTerritory();
            fromLocation = fromLocation.getTerritory();
            toLocation = toLocation.getTerritory();
        }
        Unit unit = unitLocations.get(currentLocation);
        if (unit == null) {
            LOGGER.error("Could not find unit in location: {}", currentLocation);
        }
        if (currentPhase.getPhaseName().isRetreatPhase()) {
            unit = getPreviousPhase().getStartingUnitLocations().get(currentLocation);
        }
        if (unit.getType() != unitType && !orderType.isDisband()) {
            LOGGER.error(orderInput);
            throw new UnitTypeMismatchException();
        }

        // resolve coast if only one exists
        if (unitType.isFleet() && toLocation.hasCoasts()) {
            final Location finalToLocation = toLocation;
            List<Location> coasts = mapVariant.getMovementGraph(UnitType.FLEET).get(fromLocation)
                                             .stream()
                                             .filter(location -> finalToLocation.getCoasts().contains(location))
                .collect(Collectors.toList());
            if (coasts.size() == 1) {
                toLocation = coasts.get(0);
            }
        }
        return new Order(unit, currentLocation, orderType, fromLocation, toLocation);
    }

    public List<Order> parseOrders(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<Order> orders = new LinkedList<>();
        Country country = null;
        for (String line : lines) {
            if (!StringUtils.isBlank(line) && !line.startsWith("//")) {
                if (line.contains(":")) {
                    country = this.getMapVariant().getCountry(line.replaceAll(":", "").trim().toUpperCase(Locale.ENGLISH));
                } else {
                    orders.add(this.parseOrder(line, Objects.requireNonNull(country)));
                }
            }
        }
        return orders;
    }

    public void addStandardStartingUnits() {
        mapVariant.getStartingUnits().forEach(this::addUnitAndClaimLocation);
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

    public long getArmyCount(Country country) {
        return getUnitCount(country, UnitType.ARMY);
    }

    public long getFleetCount(Country country) {
        return getUnitCount(country, UnitType.FLEET);
    }

    private long getUnitCount(Country country, UnitType unitType) {
        return unitLocations.values()
                            .stream()
                            .filter(unit -> unit.getCountry().equals(country))
                            .filter(unit -> unit.getType().equals(unitType))
                            .count();
    }

    public long getYear() {
        return mapVariant.getStartingYear() + gameYearCounter;
    }

    public String getPhaseName() {
        return currentPhase.getPhaseName().name();
    }

    public void generateResults(int counter) throws IOException {
        String filePrefix = getFileNameForPreviousPhase(counter);
        SortedSet<Country> countries = this.mapVariant.getCountries();
        if (getPreviousPhase() != null && getPreviousPhase().getPhaseName().isBuildPhase()) {
            filePrefix = (getYear() - 1) + "/" + filePrefix;
        } else {
            filePrefix = getYear() + "/" + filePrefix;
        }
        File resultsFile = Paths.get(this.baseDirectory + filePrefix + "_Results.txt")
                                .toFile();
        if (resultsFile.exists()) {
            Files.delete(resultsFile.toPath());
        }
        resultsFile.createNewFile();
        try (FileWriter writer = new FileWriter(resultsFile)) {
            writer.write(getPreviousPhase().getPhaseDescription() + " Results\n");
            writer.write("--------------------------------\n");
            countries.forEach(
                country -> {
                    try {
                        if (!getPreviousPhase().getOrdersByCountry(country).isEmpty()) {
                            writer.write("\n");
                            writer.write(country.getName() + " (" + getPlayer(country) + "):\n");
                            for (Order order : getPreviousPhase()
                                .getOrdersByCountry(country)
                                .stream()
                                .sorted(Comparator.comparing(o -> o.getCurrentLocation()
                                                                   .getName()))
                                .collect(
                                    Collectors.toList())) {
                                writer.write(order.getDescription() + "\n");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            );
        }
    }

    public void parseOrders(int counter) {
        String filename = getFileName(counter);
        try {
            addOrders(parseOrders(this.baseDirectory + getYear() + "/" + filename + ".txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processOrdersAndGenerateArtifacts() throws IOException {
        int counter = 1;
        LOGGER.debug("Skip number for map.");
        if (isFirstPhase()) {
            if (autoGenerateMaps) {
                SouthAmericanSupremacyMap southAmericanSupremacyMap = new SouthAmericanSupremacyMap();
                southAmericanSupremacyMap.drawUnits(getUnitLocations());
                southAmericanSupremacyMap.colorTerritories(getLocationOwnership());
                southAmericanSupremacyMap.generateMap("/games/south_american_supremacy/game_02/" + getYear() + "/" + getFileName(counter));
            }
            counter++;
            generateStatus(counter);
        }
        counter++;
        String fileWithPath = String.join(
            File.separator,
            this.baseDirectory,
            String.valueOf(getYear()),
            getFileName(counter) + ".txt"
        );

        while (Paths.get(fileWithPath).toFile().exists()) {
            String key = String.valueOf(getCurrentPhase().getPhaseName()) + getYear();
            if (this.replacementPlayers.containsKey(key)) {
                for (Map.Entry<Country, String> replacement : this.replacementPlayers.get(key).entrySet()) {
                    replaceCountry(replacement.getKey(), replacement.getValue());
                }
            }
            parseOrders(counter);
            adjudicate();
            counter++;
            generateResults(counter);
            counter++;
            if (autoGenerateMaps) {
                SouthAmericanSupremacyMap southAmericanSupremacyMap = new SouthAmericanSupremacyMap();
                southAmericanSupremacyMap.drawUnits(getUnitLocations());
                southAmericanSupremacyMap.drawArrows(getPreviousPhase().getOrders());
                southAmericanSupremacyMap.colorTerritories(getLocationOwnership());
                southAmericanSupremacyMap.generateMap("/games/south_american_supremacy/game_02/" + getYear() + "/" + getFileNameForPreviousPhase(counter) + "_Results");
            }
            LOGGER.debug("Skip results map.");
            counter++;
            if (getPreviousPhase().getPhaseName().isBuildPhase()) {
                counter = 1;
            }
            if (autoGenerateMaps) {
                SouthAmericanSupremacyMap southAmericanSupremacyMap = new SouthAmericanSupremacyMap();
                southAmericanSupremacyMap.drawUnits(getUnitLocations());
                southAmericanSupremacyMap.colorTerritories(getLocationOwnership());
                southAmericanSupremacyMap.generateMap("/games/south_american_supremacy/game_02/" + getYear() + "/" + getFileName(counter));
            }
            LOGGER.debug("Skip next phase map.");
            counter++;
            generateStatus(counter);
            counter++;
            fileWithPath = String.join(
                File.separator,
                this.baseDirectory,
                String.valueOf(getYear()),
                getFileName(counter) + ".txt"
            );
        }
    }

    public void setAutoGenerateMaps() {
        this.autoGenerateMaps = true;
    }
}
