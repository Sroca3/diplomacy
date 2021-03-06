package io.github.sroca3.diplomacy.svg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.sroca3.diplomacy.Country;
import io.github.sroca3.diplomacy.Location;
import io.github.sroca3.diplomacy.Order;
import io.github.sroca3.diplomacy.Unit;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyLocation;
import javafx.geometry.Bounds;
import javafx.scene.shape.SVGPath;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.anim.dom.SVGOMGElement;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.wmf.tosvg.WMFTranscoder;
import org.apache.batik.util.SVGConstants;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SouthAmericanSupremacyMap {

    String fileName = "src/main/resources/maps/south_american_supremacy.svg";
    SVGDocument document;
    private File file;

    private static Map<Location, Point2D> additionalPoints = new HashMap<>();
    static {
        additionalPoints.put(SouthAmericanSupremacyLocation.ISLAS_JUAN_FERNANDEZ, new Point2D.Double(94, 760));
        additionalPoints.put(SouthAmericanSupremacyLocation.ISLAS_GALAPAGOS, new Point2D.Double(20, 202));
        additionalPoints.put(SouthAmericanSupremacyLocation.ISLAS_MALVINAS, new Point2D.Double(402, 947));
    }


    public SouthAmericanSupremacyMap() {
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
        return Objects.requireNonNull(document);
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

    public void drawUnits(Map<Location, Unit> unitLocations) {
        getDocument();
        SVGOMGElement element = (SVGOMGElement) document.getElementById("units");
        for (Location territory : unitLocations.keySet()) {
            Element element1 = document.getElementById(territory.getName());
            if (element1 == null) {
                System.out.println(territory.getName());
            }
            Point2D point = calculateCenterPoint(element1);
            if (0 == point.getX()) {
                point = additionalPoints.getOrDefault(territory, new Point2D.Double(0, 0));
            }
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = f.newDocumentBuilder();
                Object unit;
                if (unitLocations.get(territory).getType().isFleet()) {
                    unit = new FleetSvg(
                        unitLocations.get(territory),
                        point.getX(), point.getY()
                    );
                } else {
                    unit = new ArmySvg(
                        unitLocations.get(territory),
                        point.getX(), point.getY()
                    );
                }
                ObjectMapper objectMapper = new XmlMapper();
                String x = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(unit);
                Document d = builder.parse(new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + x)));
                element.appendChild(document.importNode(d.getFirstChild(), true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void colorTerritories(Map<Location, Country> locationOwnership) {
        getDocument();
        for (Map.Entry<Location, Country> entry : locationOwnership.entrySet()) {
            if (!(entry.getKey().isCoast() || entry.getKey().isSea())) {
                Element element = document.getElementById(entry.getKey().getName());
                Color color = entry.getValue().getColor();
                element.setAttribute(
                    "fill",
                    String.format("rgb(%s,%s,%s)", color.getRed(), color.getGreen(), color.getBlue())
                );
            }
        }

    }

    private Point2D getCenterPoint(Location location) {
        Element element = document.getElementById(location.getName());
        return calculateCenterPoint(element);
    }

    private void drawObject(Element element, Object object) {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = f.newDocumentBuilder();
            ObjectMapper objectMapper = new XmlMapper();
            String x = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            Document d = builder.parse(new InputSource(new StringReader(
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + x)));
            element.appendChild(document.importNode(d.getFirstChild(), true));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public void drawArrows(List<Order> orders) {
        getDocument();
        Element element = document.getElementById("arrows");
        for (Order order : orders) {
            if (order.getOrderType().isMove() || order.getOrderType().isSupport()) {
                var arrow = new Arrow(
                    getCenterPoint(order.getCurrentLocation()),
                    getCenterPoint(order.getToLocation()),
                    ArrowType.from(order)
                );
                if (order.getOrderType().isSupport()) {
                    drawObject(
                        element,
                        new Arrow(
                            getCenterPoint(order.getCurrentLocation()),
                            getCenterPoint(order.getFromLocation()),
                            ArrowType.SOURCE
                        )
                    );
                }
                drawObject(element, arrow);
            }
            if (order.getOrderType().isHold()) {
                drawObject(element, new Circle(getCenterPoint(order.getCurrentLocation())));
            }
        }

    }

    public void generateMap(String path) {
        document.normalizeDocument();
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
        OutputStream pngOutputStream = null;
        try {
            pngOutputStream = new FileOutputStream("src/main/resources/" + path + ".png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        TranscoderOutput outputPngImage = new TranscoderOutput(pngOutputStream);
        TranscodingHints hints = new TranscodingHints();
hints.put(WMFTranscoder.KEY_INPUT_HEIGHT, 1000);
hints.put(WMFTranscoder.KEY_INPUT_WIDTH, 8000);
hints.put(WMFTranscoder.KEY_WIDTH, 800f);
 hints.put(WMFTranscoder.KEY_HEIGHT, 600f);
        hints.put(ImageTranscoder.KEY_DOM_IMPLEMENTATION, SVGDOMImplementation.getDOMImplementation());
        hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI, SVGConstants.SVG_NAMESPACE_URI);
        hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI,SVGConstants.SVG_NAMESPACE_URI);
        hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT, SVGConstants.SVG_SVG_TAG);
        hints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, false);
        PNGTranscoder pngTranscoder = new PNGTranscoder();
pngTranscoder.setTranscodingHints(hints);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        StreamResult streamResult = new StreamResult(byteArrayOutputStream);
        try {
            transformer.transform(source, streamResult);
        TranscoderInput transcoderInput = new TranscoderInput(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            pngTranscoder.transcode(transcoderInput, outputPngImage);
            pngOutputStream.flush();
            pngOutputStream.close();
        } catch (TransformerException | TranscoderException | IOException e) {
            e.printStackTrace();
        }
    }
}
