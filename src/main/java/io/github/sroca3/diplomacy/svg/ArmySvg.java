package io.github.sroca3.diplomacy.svg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.sroca3.diplomacy.Army;
import io.github.sroca3.diplomacy.SvgLocation;
import io.github.sroca3.diplomacy.Unit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@JacksonXmlRootElement(localName = "g")
public class ArmySvg {

    @JacksonXmlProperty(localName = "path")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Object> paths = new ArrayList<>();

    @JacksonXmlProperty(isAttribute = true)
    private UUID id;

    @JacksonXmlProperty(isAttribute = true)
    private String transform;

    private static final double HEIGHT = 20;
    private static final double WIDTH = 40;

    public ArmySvg(Unit unit, SvgLocation location) {
        paths.add(new ArmyColor(unit.getCountry().getColor()));
        paths.add(new ArmyOutline());
        this.id = unit.getId();
        this.transform = String.format("translate(%s, %s)", location.getX(), location.getY());
    }

    public ArmySvg(Unit unit, double x, double y) {
        paths.add(new ArmyColor(unit.getCountry().getColor()));
        paths.add(new ArmyOutline());
        this.id = unit.getId();
        this.transform = String.format("translate(%s, %s)", x - (WIDTH/2), y - (HEIGHT/2));
    }

    public List<Object> getPaths() {
        return paths;
    }

    public UUID getId() {
        return id;
    }

    public String getTransform() {
        return transform;
    }
}
