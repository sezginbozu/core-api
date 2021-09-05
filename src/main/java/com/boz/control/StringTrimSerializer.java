package com.boz.control;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializerBase;

public class StringTrimSerializer extends ToStringSerializerBase {
    public static final ToStringSerializer instance = new ToStringSerializer();

    public StringTrimSerializer() {
        super(Object.class);
    }

    public StringTrimSerializer(Class<?> handledType) {
        super(handledType);
    }

    public final String valueToString(Object value) {
        return value.toString().trim();
    }
}
