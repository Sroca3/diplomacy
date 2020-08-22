package io.github.sroca3.diplomacy.games.south_american_supremacy;

import io.github.sroca3.diplomacy.Country;
import io.github.sroca3.diplomacy.Diplomacy;
import io.github.sroca3.diplomacy.Order;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyMapVariant;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public class Game01 {
    public static void main(String... args) throws IOException {
        Diplomacy diplomacy = new Diplomacy(SouthAmericanSupremacyMapVariant.getInstance());
        Map<Country, String> assignments = diplomacy.assignCountries(List.of(
            "AKFD",
            "Jordan767",
            "FloridaMan",
            "Don Juan of Austria",
            "VGhost",
            "bratsffl",
            "Antigonos",
            "RolynTrotter"
        ));
        ;
        File countryAssignments =
            Paths.get("src/main/resources/games/south_american_supremacy/game_01/country_assignments.txt").toFile()
        ;
        boolean assign = countryAssignments.createNewFile();
        if (assign) {
            try (FileWriter writer = new FileWriter(countryAssignments)) {
                writer.write("Country Assignments:\n");
                assignments.entrySet()
                           .stream()
                           .sorted(Comparator.comparing(entry -> entry.getKey().name()))
                           .forEach(entry -> {
                               try {
                                   writer.write(entry.getKey().name() + " assigned to " + entry.getValue() + "\n");
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           });
            }
        } else {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(countryAssignments));
            // Skip preamble
            bufferedReader.readLine();
            Map<Country, String> playerAssignments = new HashMap<>();
            for (Country country: diplomacy.getMapVariant().getCountries()) {
                String line = bufferedReader.readLine();
                String player = line.substring(line.lastIndexOf(" to ") + 4);
                playerAssignments.put(country, player);
            }
            diplomacy.setPlayerAssignments(playerAssignments);
        }
        diplomacy.addStandardStartingUnits();
        diplomacy.beginFirstPhase();
        generateStatus(diplomacy, "02_Spring_1835_Orders");

        diplomacy.parseOrders("src/main/resources/games/south_american_supremacy/game_01/1835/03_Spring_1835_Orders.txt");
        diplomacy.adjudicate();
        generateResults(diplomacy, "04_Spring_1835_Orders");

        generateStatus(diplomacy, "Latest", true);
    }



    private static void generateStatus(Diplomacy diplomacy, String filePrefix) throws IOException {
        generateStatus(diplomacy, filePrefix, false);
    }

    private static void generateResults(Diplomacy diplomacy, String filePrefix) throws IOException {
        SortedSet<Country> countries = diplomacy.getMapVariant().getCountries();
        filePrefix = diplomacy.getYear() + "/" + filePrefix;
        File resultsFile = Paths.get("src/main/resources/games/south_american_supremacy/game_01/" + filePrefix + "_Results.txt")
                               .toFile();
        if (resultsFile.exists()) {
            resultsFile.delete();
        }
        resultsFile.createNewFile();
        try (FileWriter writer = new FileWriter(resultsFile)) {
            writer.write("Results\n");
            writer.write("--------------------------------\n");
            countries.forEach(
                country -> {
                    try {
                        writer.write("\n");
                        writer.write(country.name() + " (" + diplomacy.getPlayer(country) + "):\n");
                        for (Order order : diplomacy.getPreviousPhase().getOrdersByCountry(country)) {
                            writer.write(order.getDescription() + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            );
        }
    }

    private static void generateStatus(Diplomacy diplomacy, String filePrefix, boolean isLatest) throws IOException {
        SortedSet<Country> countries = diplomacy.getMapVariant().getCountries();
        if(!isLatest) {
            filePrefix = diplomacy.getYear() + "/" + filePrefix;
        }
        File statusFile = Paths.get("src/main/resources/games/south_american_supremacy/game_01/" + filePrefix + "_Status.txt")
                               .toFile();
        if (statusFile.exists()) {
            statusFile.delete();
        }
        statusFile.createNewFile();
        try (FileWriter writer = new FileWriter(statusFile)) {
            writer.write("Status\n");
            writer.write("--------------------------------\n");
            countries.forEach(
                country -> {
                    try {
                        writer.write("\n");
                        writer.write(country.name() + "\n");
                        writer.write("(" + diplomacy.getPlayer(country) + ")\n");
                        writer.write(diplomacy.getArmyCount(Country.ARGENTINA) + " army units\n");
                        writer.write(diplomacy.getFleetCount(Country.ARGENTINA) + " fleet units\n");
                        writer.write(diplomacy.getSupplyCenterCount(Country.ARGENTINA) + " centers\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            );
        }
    }
}
