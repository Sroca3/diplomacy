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

    public Arrow(Point2D from, Point2D to, ArrowType arrowType) {
                lines.add(new Line(from.getX(), to.getX(), from.getY(), to.getY(), "black", 2));
        switch (arrowType){
            case MOVE:
                lines.add(new Line(from.getX(), to.getX(), from.getY(), to.getY(), "black", 1, "url(#move-arrow)"));
                break;
            case BOUNCE:
                lines.add(new Line(from.getX(), to.getX(), from.getY(), to.getY(), "red", 1, "url(#bounce-arrow)"));
                break;
            case SUPPORT:
                lines.add(new Line(from.getX(), to.getX(), from.getY(), to.getY(), "green", 1, "url(#support-arrow)"));
                break;
            case SUPPORT_CUT:
                lines.add(new Line(from.getX(), to.getX(), from.getY(), to.getY(), "yellow", 1, "url(#support-cut-arrow)"));
                break;
            case SOURCE:
                lines.add(new Line(from.getX(), to.getX(), from.getY(), to.getY(), "white", 1, "url(#source-arrow)"));
                break;
            default:
                throw new RuntimeException("ArrowType not found.");
        }
    }

    public List<Line> getLines() {
        return lines;
    }
}
