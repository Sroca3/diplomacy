package io.github.sroca3.diplomacy.games.south_american_supremacy;

import io.github.sroca3.diplomacy.Country;
import io.github.sroca3.diplomacy.Diplomacy;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyMapVariant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
        }
    }
}
