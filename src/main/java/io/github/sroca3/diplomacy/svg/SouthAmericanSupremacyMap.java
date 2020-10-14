package io.github.sroca3.diplomacy.svg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSExpression;
import com.helger.css.reader.CSSReaderDeclarationList;
import com.helger.css.writer.CSSWriterSettings;
import io.github.sroca3.diplomacy.Army;
import io.github.sroca3.diplomacy.SouthAmericanSupremacyCountry;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyLocation;
import javafx.geometry.Bounds;
import javafx.scene.shape.SVGPath;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGOMGElement;
import org.apache.batik.anim.dom.SVGOMPathElement;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.AbstractSVGPathSegList;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class SouthAmericanSupremacyMap {

    String fileName = "src/main/resources/maps/south_american_supremacy.svg";
    SVGDocument document;
    private File file;

    public SouthAmericanSupremacyMap() {
    }

    private void dummy() throws Exception {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
        Document document = factory.createDocument(
            "src/main/resources/maps/south_american_supremacy.svg");
//        Element element = document.getElementById("layer13");
//        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder;
//        try {
//            builder = f.newDocumentBuilder();
//            ArmySvg armySvg = new ArmySvg(
//                new Army(SouthAmericanSupremacyCountry.ARGENTINA),
//                SouthAmericanSupremacyLocation.CORDOBA
//            );
//            ObjectMapper objectMapper = new XmlMapper();
//            String x = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(armySvg);
//            Document d = builder.parse(new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + x)));
//            element.appendChild(document.importNode(d.getFirstChild(), true));
////        element.appendChild(d.getFirstChild());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        var x = CSSReaderDeclarationList.readFromString(
//            document.getElementById(SouthAmericanSupremacyLocation.PATAGONIA.getName()).getAttribute("style"),
//            ECSSVersion.CSS30
//        );
//        x.getDeclarationOfPropertyName("fill").setExpression(CSSExpression.createSimple("url(#Peru)"));
//        document.getElementById(SouthAmericanSupremacyLocation.PATAGONIA.getName())
//                .setAttribute("style", x.getAsCSSString(new CSSWriterSettings(ECSSVersion.CSS30, true)));
//        LOGGER.error(x.getAsCSSString(new CSSWriterSettings(ECSSVersion.CSS30, true)));
        NodeList nodeList = document.getElementsByTagName("path");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String attribute = element.getAttributeNS("http://www.inkscape.org/namespaces/inkscape", "label");
            element.setAttribute("id", attribute.toUpperCase().replace(" ", "_"));
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        //for pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(document);

        //write to console or file
        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(new File(
            "src/main/resources/maps/latest.svg"));

        //write data
//        transformer.transform(source, console);
        transformer.transform(source, file);
    }

    private SVGDocument getDocument() {
        if (document != null) {
            return document;
        }
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
        SVGDocument document = null;
        try {
            document = factory.createSVGDocument(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.document = document;
        return document;
    }

    private Point2D calculateCenterPoint(Element element) {
        SVGPath svgPath = new SVGPath();
        if (element == null) {
            return new Point2D.Double(0,0);
        }
        svgPath.setContent(element.getAttribute("d"));
        Bounds bounds = svgPath.getBoundsInParent();
        return new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
    }

    public void drawUnits() {
        SVGDocument document = getDocument();
        SVGOMGElement element = (SVGOMGElement) document.getElementById("units");
        for (SouthAmericanSupremacyLocation territory : SouthAmericanSupremacyLocation.values()) {
            Element element1 = document.getElementById(territory.name());
            if (element1 == null) {
                System.out.println(territory.name());
            }
            Point2D point = calculateCenterPoint(element1);
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = f.newDocumentBuilder();
                FleetSvg unit = new FleetSvg(
                    new Army(SouthAmericanSupremacyCountry.ARGENTINA),
                    point.getX(), point.getY()
                );
                ObjectMapper objectMapper = new XmlMapper();
                String x = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(unit);
                Document d = builder.parse(new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + x)));
                element.appendChild(document.importNode(d.getFirstChild(), true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void colorTerritories() {

    }

    public void drawArrows() {

    }

    public void generateMap() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        //for pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(document);
        StreamResult file = new StreamResult(new File("src/main/resources/maps/latest.svg"));
        try {
            transformer.transform(source, file);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
