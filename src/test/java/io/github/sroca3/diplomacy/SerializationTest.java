package io.github.sroca3.diplomacy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyLocation;
import io.github.sroca3.diplomacy.svg.ArmySvg;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class SerializationTest {

    @Test
    public void serializeArmy() throws Exception {
        ArmySvg armySvg = new ArmySvg(
            new Army(SouthAmericanSupremacyCountry.ARGENTINA),
            SouthAmericanSupremacyLocation.CORDOBA
        );
        ObjectMapper objectMapper = new XmlMapper();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(armySvg));
    }
}
