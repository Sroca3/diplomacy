package io.github.sroca3.diplomacy.svg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.awt.geom.Point2D;

@JacksonXmlRootElement(localName = "circle")
public class Circle {
    @JacksonXmlProperty(isAttribute = true)
    private double cx;
    @JacksonXmlProperty(isAttribute = true)
    private double cy;
    @JacksonXmlProperty(isAttribute = true)
    private double r = 21;
    @JacksonXmlProperty(isAttribute = true)
    private String stroke = "black";
    @JacksonXmlProperty(localName = "stroke-width", isAttribute = true)
    private int strokeWidth = 2;
    @JacksonXmlProperty(isAttribute = true)
    private String fill = "none";

    public Circle(Point2D point2D) {
        this(point2D.getX(), point2D.getY());
    }

    public Circle(double cx, double cy) {
        this.cx = cx;
        this.cy = cy;
    }

    public double getCx() {
        return cx;
    }

    public double getCy() {
        return cy;
    }

    public double getR() {
        return r;
    }

    public String getStroke() {
        return stroke;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public String getFill() {
        return fill;
    }
}
