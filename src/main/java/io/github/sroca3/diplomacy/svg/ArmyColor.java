package io.github.sroca3.diplomacy.svg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.awt.*;
import java.util.UUID;

@JacksonXmlRootElement(localName = "path")
public class ArmyColor {

    @JacksonXmlProperty(isAttribute = true)
    private UUID id;
    @JacksonXmlProperty(isAttribute = true)
    private String fill;

    @JsonIgnore
    private Color fillColor;

    @JacksonXmlProperty(localName = "d", isAttribute = true)
    private String path = "M 2.07258,17.09747 0,14.194934 0.0318,12.415714 0.0636,10.636493 1.3237,8.811675 2.5838,6.986856 6.7919,7.223536 11,7.460214 V 6.69826 5.936304 L 8.89464,5.329805 6.78929,4.723314 7.21266,3.324158 7.63604,1.925003 11.0079,0.96249703 14.37977,0 22.21019,1.738085 30.04061,3.476161 H 35.02031 40 v 1.101595 1.101585 l -6.75,0.0442 -6.75,0.04428 5.5,2.60928 5.49999,2.60927 0.34051,1.851116 0.34051,1.851116 -2.26482,2.655695 L 33.65137,20 H 18.89826 4.14516 Z m 0,0 L 0,14.194934 0.0318,12.415714 0.0636,10.636493 1.3237,8.811675 2.5838,6.986856 6.7919,7.223536 11,7.460214 V 6.69826 5.936304 L 8.89464,5.329805 6.78929,4.723314 7.21266,3.324158 7.63604,1.925003 11.0079,0.96249703 14.37977,0 22.21019,1.738085 30.04061,3.476161 H 35.02031 40 v 1.101595 1.101585 l -6.75,0.0442 -6.75,0.04428 5.5,2.60928 5.49999,2.60927 0.34051,1.851116 0.34051,1.851116 -2.26482,2.655695 L 33.65137,20 H 18.89826 4.14516 Z";

    public ArmyColor(Color color) {
        this.fillColor = color;
        this.fill = String.format("rgb(%s,%s,%s)", color.getRed(), color.getGreen(), color.getBlue());
    }

    public UUID getId() {
        return id;
    }

    public String getFill() {
        return fill;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public String getPath() {
        return path;
    }
}
