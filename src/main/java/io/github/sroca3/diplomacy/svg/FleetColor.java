package io.github.sroca3.diplomacy.svg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.awt.*;
import java.util.UUID;

@JacksonXmlRootElement(localName = "path")
public class FleetColor {

    @JacksonXmlProperty(isAttribute = true)
    private UUID id;
    @JacksonXmlProperty(isAttribute = true)
    private String fill;

    @JsonIgnore
    private Color fillColor;

    @JacksonXmlProperty(localName = "d", isAttribute = true)
    private String path = "M 0.791517,3.402181 C 0.649375,3.088528 0.533075,2.825741 0.533075,2.818211 c 0,-0.0075 0.159105,0.03365 0.353568,0.09151 0.194461,0.05786 0.653764,0.177091 1.020673,0.264954 0.366908,0.08786 0.685207,0.165102 0.707331,0.171643 0.02768,0.0082 0.101844,-0.08372 0.237816,-0.294719 0.108672,-0.168638 0.204645,-0.306314 0.213273,-0.305946 0.0086,2.64e-4 0.117755,0.153471 0.242503,0.340225 l 0.226817,0.339556 0.0074,-0.420278 0.0074,-0.420277 H 3.896074 4.242292 L 4.242821,2.257997 4.24335,1.931115 4.5965,1.797365 4.949653,1.663616 l 0.01334,-0.718683 0.01333,-0.718682 0.316502,0.417385 0.3165,0.417388 0.03306,0.695656 c 0.01818,0.382611 0.03818,0.700778 0.04444,0.707038 0.0063,0.0063 0.126572,-0.279127 0.267362,-0.634193 l 0.255982,-0.64557 0.173902,-0.0078 0.1739,-0.0078 0.01971,0.65692 c 0.01085,0.361308 0.02809,0.647013 0.03835,0.634903 0.01024,-0.01212 0.08802,-0.202139 0.172822,-0.422286 0.0848,-0.220144 0.16275,-0.409448 0.173223,-0.420674 0.01047,-0.01122 0.09539,0.222927 0.188702,0.520343 l 0.16966,0.540755 0.295147,0.300196 c 0.162331,0.165111 0.305147,0.300199 0.317369,0.300199 0.01222,0 0.0802,-0.123081 0.15106,-0.273513 L 8.212853,2.731682 8.339782,3.005198 8.466712,3.278711 H 9.39662 c 0.51145,0 0.929908,0.0084 0.929908,0.01865 0,0.01027 -0.0382,0.1352 -0.0849,0.277654 l -0.0849,0.259009 -0.375406,0.0154 c -0.206474,0.0085 -1.432103,0.0282 -2.723622,0.04383 -1.291517,0.01564 -3.056682,0.03971 -3.922586,0.0535 -0.865905,0.01378 -1.689225,0.02521 -1.829602,0.0254 l -0.25523,2.64e-4 z";

    public FleetColor(Color color) {
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
