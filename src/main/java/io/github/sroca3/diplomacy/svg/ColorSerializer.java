package io.github.sroca3.diplomacy.svg;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.awt.*;
import java.io.IOException;

public class ColorSerializer extends StdSerializer<Color> {

    public ColorSerializer() {
        this(null);
    }

    protected ColorSerializer(Class<Color> t) {
        super(t);
    }

    @Override
    public void serialize(
        Color color, JsonGenerator jsonGenerator, SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeString(String.format("rgb(%s,%s,%s)", color.getRed(), color.getGreen(), color.getBlue()));
    }
}
