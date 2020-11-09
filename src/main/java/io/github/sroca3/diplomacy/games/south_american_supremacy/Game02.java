package io.github.sroca3.diplomacy.games.south_american_supremacy;

import io.github.sroca3.diplomacy.Diplomacy;
import io.github.sroca3.diplomacy.PhaseName;
import io.github.sroca3.diplomacy.SouthAmericanSupremacyCountry;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyMapVariant;

import java.io.IOException;
import java.util.List;

public class Game02 {

    public static void main(String... args) throws IOException {
        Diplomacy diplomacy = new Diplomacy(SouthAmericanSupremacyMapVariant.getInstance());
        diplomacy.setBaseDirectory("src/main/resources/games/south_american_supremacy/game_02/");
        diplomacy.setAutoGenerateMaps();
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
        diplomacy.replaceCountry(PhaseName.SPRING_ORDERS, 1838, SouthAmericanSupremacyCountry.ARGENTINA, "haroonriaz");
        diplomacy.processOrdersAndGenerateArtifacts();
    }
}
