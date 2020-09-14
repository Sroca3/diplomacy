package io.github.sroca3.diplomacy.games.south_american_supremacy;

import io.github.sroca3.diplomacy.Country;
import io.github.sroca3.diplomacy.Diplomacy;
import io.github.sroca3.diplomacy.Order;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyMapVariant;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
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

    private static String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            System.out.println("nodeToString Transformer Exception");
        }
        return sw.toString();
    }

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
            Paths.get("src/main/resources/games/south_american_supremacy/game_01/country_assignments.txt").toFile();
//        String parser = XMLResourceDescriptor.getXMLParserClassName();
//        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
//        Document document = factory.createDocument(
//            "src/main/resources/games/south_american_supremacy/game_01/latest.svg");
//        Document armySvg = factory.createDocument( "src/main/resources/games/south_american_supremacy/game_01/unit.svg" );
//        var x =CSSReaderDeclarationList.readFromString(document.getElementById("path1306").getAttribute("style"), ECSSVersion.CSS30);
//        x.getDeclarationOfPropertyName("fill").setExpression(CSSExpression.createSimple("url(#Peru)"));
//        document.getElementById("path1306").setAttribute("style", x.getAsCSSString(new CSSWriterSettings(ECSSVersion.CSS30, true)));
//        LOGGER.error(x.getAsCSSString(new CSSWriterSettings(ECSSVersion.CSS30, true)));
//        Army army = new Army(Country.PARAGUAY);
//        Element element = armySvg.getElementById("army");
//        element.setAttribute("id", army.getId().toString());
//        Node newNode = document.importNode(element.getParentNode(), true);
//        document.getElementById("layer13").appendChild(newNode);
//        document.getElementById(army.getId().toString()).setAttribute("transform", "translate(476,555)");
//        NodeList nodeList = document.getElementsByTagName("path");
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Element element = (Element) nodeList.item(i);
//            String attribute = element.getAttributeNS("http://www.inkscape.org/namespaces/inkscape", "label");
//            element.setAttribute("id", attribute.toUpperCase().replaceAll(" ", "_"));
//        }
//        SVGGraphics2D graphics = new SVGGraphics2D(document);
//
//        try (Writer out = new OutputStreamWriter(new FileOutputStream(
//            "src/main/resources/games/south_american_supremacy/game_01/latest.svg"), "UTF-8")) {
//            graphics.stream(document.getDocumentElement(), out, true, false);
//        } catch (UnsupportedEncodingException | FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (SVGGraphics2DIOException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
