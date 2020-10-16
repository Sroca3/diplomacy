package io.github.sroca3.diplomacy.svg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "g")
public class Arrow {

    @JacksonXmlProperty(localName = "line")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Line> lines = new ArrayList<>();

    public Arrow(Point2D from, Point2D to) {
        lines.add(new Line(from.getX(), to.getX(), from.getY(), to.getY(), "black", 2));
        lines.add(new Line(from.getX(), to.getX(), from.getY(), to.getY(), "green", 1, "url(#arrow)"));
    }

    public List<Line> getLines() {
        return lines;
    }
}
