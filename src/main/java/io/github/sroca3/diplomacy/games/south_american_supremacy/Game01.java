package io.github.sroca3.diplomacy.games.south_american_supremacy;

import io.github.sroca3.diplomacy.Country;
import io.github.sroca3.diplomacy.Diplomacy;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyMapVariant;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
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
        }
        diplomacy.addStandardStartingUnits();
        diplomacy.beginFirstPhase();
        generateStatus(diplomacy, "02_Spring_1835_Orders");

        /*
           Because font metrics is based on a graphics context, we need to create
           a small, temporary image so we can ascertain the width and height
           of the final image
         */
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 12);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth("--------------------------------");
        int height = fm.getHeight()*diplomacy.getMapVariant().getCountries().size() * 5;
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        g2d.drawString("Status:", 0, fm.getAscent());
        g2d.drawString("--------------------------------", 0, fm.getAscent() * 2);
        SortedSet<Country> countries = diplomacy.getMapVariant().getCountries();
        int line = 4;
        for (Country country : countries) {
                g2d.drawString(country.name(), 0, fm.getAscent() * line++);
                g2d.drawString(diplomacy.getArmyCount(country) + " army units", 0, fm.getAscent() * line++);
                g2d.drawString(diplomacy.getFleetCount(country) + " fleet units", 0, fm.getAscent() * line++);
                g2d.drawString(diplomacy.getSupplyCenterCount(country) + " centers", 0, fm.getAscent() * line++);
                line++;
        }
        g2d.dispose();
        try {
            ImageIO.write(img, "png", new File("src/main/resources/games/south_american_supremacy/game_01/Latest_Status.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        generateStatus(diplomacy, "Latest", true);
    }

    private static void generateStatus(Diplomacy diplomacy, String filePrefix) throws IOException {
        generateStatus(diplomacy, filePrefix, false);
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
