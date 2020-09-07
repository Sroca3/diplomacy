package io.github.sroca3.diplomacy.games.south_american_supremacy;

import io.github.sroca3.diplomacy.Country;
import io.github.sroca3.diplomacy.Diplomacy;
import io.github.sroca3.diplomacy.Order;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyMapVariant;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class Game01 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game01.class);

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

        File countryAssignments =
            Paths.get("src/main/resources/games/south_american_supremacy/game_01/country_assignments.txt").toFile()
        ;
//        String parser = XMLResourceDescriptor.getXMLParserClassName();
//        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory( parser );
//        Document document = factory.createDocument( "src/main/resources/games/south_american_supremacy/game_01/latest.svg" );
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
            Map<Country, String> playerAssignments;
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(countryAssignments))) {
                // Skip preamble
                bufferedReader.readLine();
                playerAssignments = new EnumMap<>(Country.class);
                for (Country country : diplomacy.getMapVariant().getCountries()) {
                    String line = bufferedReader.readLine();
                    String player = line.substring(line.lastIndexOf(" to ") + 4);
                    playerAssignments.put(country, player);
                }
            }
            diplomacy.setPlayerAssignments(playerAssignments);
        }
        diplomacy.addStandardStartingUnits();
        diplomacy.beginFirstPhase();
        processOrdersAndGenerateArtifacts(diplomacy);
    }

    private static void parseOrders(Diplomacy diplomacy, String filename) {
        try {
            diplomacy.addOrders(diplomacy.parseOrders(
                "src/main/resources/games/south_american_supremacy/game_01/" + diplomacy.getYear() + "/" + filename + ".txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getFileName(Diplomacy diplomacy, int counter) {
        String fileNumber = StringUtils.leftPad(String.valueOf(counter), 2, '0');
        String filePhaseName = diplomacy.getPhaseDescription().replace(" ", "_");
        return String.join("_", fileNumber, filePhaseName);
    }

    private static String getFileNameForPreviousPhase(Diplomacy diplomacy, int counter) {
        String fileNumber = StringUtils.leftPad(String.valueOf(counter), 2, '0');
        String filePhaseName = diplomacy.getPreviousPhase().getPhaseDescription().replace(" ", "_");
        return String.join("_", fileNumber, filePhaseName);
    }

    private static void processOrdersAndGenerateArtifacts(Diplomacy diplomacy) throws IOException {
        int counter = 1;
        LOGGER.debug("Skip number for map.");
        if (diplomacy.isFirstPhase()) {
            counter++;
            generateStatus(diplomacy, getFileName(diplomacy, counter));
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
            getFileName(diplomacy, counter) + ".txt"
        );

        while (Paths.get(fileWithPath).toFile().exists()) {
            parseOrders(diplomacy, getFileName(diplomacy, counter));
            diplomacy.adjudicate();
            counter++;
            generateResults(diplomacy, getFileNameForPreviousPhase(diplomacy, counter));
            counter++;
            LOGGER.debug("Skip results map.");
            counter++;
            if (diplomacy.getPreviousPhase().getPhaseName().isBuildPhase()) {
                counter = 1;
            }
            LOGGER.debug("Skip next phase map.");
            counter++;
            generateStatus(diplomacy, getFileName(diplomacy, counter));
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
                getFileName(diplomacy, counter) + ".txt"
            );
        }
    }

    private static void generateStatus(Diplomacy diplomacy, String filePrefix) throws IOException {
        generateStatus(diplomacy, filePrefix, false);
    }

    private static void generateResults(Diplomacy diplomacy, String filePrefix) throws IOException {
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
                            writer.write(country.name() + " (" + diplomacy.getPlayer(country) + "):\n");
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

    private static void generateStatus(Diplomacy diplomacy, String filePrefix, boolean isLatest) throws IOException {
        SortedSet<Country> countries = diplomacy.getMapVariant().getCountries();
        if(!isLatest) {
                filePrefix = diplomacy.getYear() + "/" + filePrefix;
        }
        File statusFile = Paths.get("src/main/resources/games/south_american_supremacy/game_01/" + filePrefix + "_Status.txt")
                               .toFile();
        if (statusFile.exists()) {
            Files.delete(statusFile.toPath());
        }
        statusFile.createNewFile();
        try (FileWriter writer = new FileWriter(statusFile)) {
            writer.write(diplomacy.getPhaseDescription() + " Status\n");
            writer.write("--------------------------------\n");
            countries.forEach(
                country -> {
                    try {
                        writer.write("\n");
                        writer.write(country.name() + "\n");
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
