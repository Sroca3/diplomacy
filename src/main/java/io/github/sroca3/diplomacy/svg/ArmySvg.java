package io.github.sroca3.diplomacy.svg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.sroca3.diplomacy.Army;
import io.github.sroca3.diplomacy.Location;
import io.github.sroca3.diplomacy.SvgLocation;

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

    public ArmySvg(Army army, SvgLocation location) {
        paths.add(new ArmyColor(new Color(124, 104, 188)));
        paths.add(new ArmyOutline());
        this.id = army.getId();
        this.transform = String.format("translate(%s, %s)", location.getX(), location.getY());
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
