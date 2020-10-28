package io.github.sroca3.diplomacy.games.south_american_supremacy;

import io.github.sroca3.diplomacy.Country;
import io.github.sroca3.diplomacy.Diplomacy;
import io.github.sroca3.diplomacy.Order;
import io.github.sroca3.diplomacy.SouthAmericanSupremacyCountry;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyMapVariant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class Game01 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game01.class);

    public static void main(String... args) throws IOException {
        Diplomacy diplomacy = new Diplomacy(SouthAmericanSupremacyMapVariant.getInstance());
        diplomacy.setBaseDirectory("src/main/resources/games/south_american_supremacy/game_01/");
        diplomacy.assignAndSaveCountries(List.of(
            "AKFD",
            "Jordan767",
            "FloridaMan",
            "Don Juan of Austria",
            "VGhost",
            "bratsffl",
            "Antigonos",
            "RolynTrotter"
        ));
        diplomacy.addStandardStartingUnits();
        diplomacy.beginFirstPhase();
        processOrdersAndGenerateArtifacts(diplomacy);
    }

    private static void parseOrders(Diplomacy diplomacy, int counter) {
        String filename = diplomacy.getFileName(counter);
        try {
            diplomacy.addOrders(diplomacy.parseOrders(
                "src/main/resources/games/south_american_supremacy/game_01/" + diplomacy.getYear() + "/" + filename + ".txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processOrdersAndGenerateArtifacts(Diplomacy diplomacy) throws IOException {
        int counter = 1;
        LOGGER.debug("Skip number for map.");
        if (diplomacy.isFirstPhase()) {
            counter++;
            generateStatus(diplomacy, counter);
        }
        counter++;
        String fileWithPath = String.join(
            File.separator,
            "src",
            "main",
            "resources",
            "games",
            "south_american_supremacy",
            "game_01",
            String.valueOf(diplomacy.getYear()),
            diplomacy.getFileName(counter) + ".txt"
        );

        while (Paths.get(fileWithPath).toFile().exists()) {
            if (diplomacy.getCurrentPhase().getPhaseName().isSpringOrders() && diplomacy.getYear() == 1838) {
                diplomacy.replaceCountry(SouthAmericanSupremacyCountry.ARGENTINA, "haroonriaz");
            }
            parseOrders(diplomacy, counter);
            diplomacy.adjudicate();
            counter++;
            generateResults(diplomacy, counter);
            counter++;
            LOGGER.debug("Skip results map.");
            counter++;
            if (diplomacy.getPreviousPhase().getPhaseName().isBuildPhase()) {
                counter = 1;
            }
            LOGGER.debug("Skip next phase map.");
            counter++;
            generateStatus(diplomacy, counter);
            counter++;
            fileWithPath = String.join(
                File.separator,
                "src",
                "main",
                "resources",
                "games",
                "south_american_supremacy",
                "game_01",
                String.valueOf(diplomacy.getYear()),
                diplomacy.getFileName(counter) + ".txt"
            );
        }
    }

    private static void generateResults(Diplomacy diplomacy, int counter) throws IOException {
        String filePrefix = diplomacy.getFileNameForPreviousPhase(counter);
        SortedSet<Country> countries = diplomacy.getMapVariant().getCountries();
        if (diplomacy.getPreviousPhase() != null && diplomacy.getPreviousPhase().getPhaseName().isBuildPhase()) {
            filePrefix = (diplomacy.getYear() - 1) + "/" + filePrefix;
        } else {
            filePrefix = diplomacy.getYear() + "/" + filePrefix;
        }
        File resultsFile = Paths.get("src/main/resources/games/south_american_supremacy/game_01/" + filePrefix + "_Results.txt")
                                .toFile();
        if (resultsFile.exists()) {
            Files.delete(resultsFile.toPath());
        }
        resultsFile.createNewFile();
        try (FileWriter writer = new FileWriter(resultsFile)) {
            writer.write(diplomacy.getPreviousPhase().getPhaseDescription() + " Results\n");
            writer.write("--------------------------------\n");
            countries.forEach(
                country -> {
                    try {
                        if (!diplomacy.getPreviousPhase().getOrdersByCountry(country).isEmpty()) {
                            writer.write("\n");
                            writer.write(country.getName() + " (" + diplomacy.getPlayer(country) + "):\n");
                            for (Order order : diplomacy.getPreviousPhase()
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

    private static void generateStatus(Diplomacy diplomacy, int counter) throws IOException {
        String filePrefix = diplomacy.getYear() + File.separator + diplomacy.getFileName(counter);
        SortedSet<Country> countries = diplomacy.getMapVariant().getCountries();
        File statusFile = Paths.get("src/main/resources/games/south_american_supremacy/game_01/" + filePrefix + "_Status.txt")
                               .toFile();
        if (statusFile.exists()) {
            Files.delete(statusFile.toPath());
        }
        File parentDirectory = statusFile.getParentFile();
        if (!parentDirectory.exists()) {
            Files.createDirectory(parentDirectory.toPath());
        }
        statusFile.createNewFile();
        try (FileWriter writer = new FileWriter(statusFile)) {
            writer.write(diplomacy.getPhaseDescription() + " Status\n");
            writer.write("--------------------------------\n");
            countries.forEach(
                country -> {
                    try {
                        writer.write("\n");
                        writer.write(country.getName() + "\n");
                        writer.write("(" + diplomacy.getPlayer(country) + ")\n");
                        writer.write(diplomacy.getArmyCount(country) + " army units\n");
                        writer.write(diplomacy.getFleetCount(country) + " fleet units\n");
                        writer.write(diplomacy.getSupplyCenterCount(country) + " centers\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            );
        }
    }
}
