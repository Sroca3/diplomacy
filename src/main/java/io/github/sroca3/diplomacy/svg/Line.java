package io.github.sroca3.diplomacy.svg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Line {

    @JacksonXmlProperty(isAttribute = true)
    private double x1;
    @JacksonXmlProperty(isAttribute = true)
    private double x2;
    @JacksonXmlProperty(isAttribute = true)
    private double y1;
    @JacksonXmlProperty(isAttribute = true)
    private double y2;
    @JacksonXmlProperty(isAttribute = true)
    private String stroke;
    @JacksonXmlProperty(localName = "stroke-width", isAttribute = true)
    private int strokeWidth;
    @JacksonXmlProperty(localName = "marker-end", isAttribute = true)
    private String markerEnd;

    public Line(double x1, double x2, double y1, double y2, String stroke, int strokeWidth) {
        this(x1, x2, y1, y2, stroke, strokeWidth, null);
    }

    public Line(double x1, double x2, double y1, double y2, String stroke, int strokeWidth, String markerEnd) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.stroke = stroke;
        this.strokeWidth = strokeWidth;
        this.markerEnd = markerEnd;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public String getStroke() {
        return stroke;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public String getMarkerEnd() {
        return markerEnd;
    }
}
