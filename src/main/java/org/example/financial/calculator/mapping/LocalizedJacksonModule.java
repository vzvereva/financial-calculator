package org.example.financial.calculator.mapping;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.util.Locale;

/**
 * Serialization/deserialization module that supports local numeric formats.
 */
@Component
public class LocalizedJacksonModule extends SimpleModule {

    private static final String MODULE_NAME = "localized-module";
    private static final String DECIMAL_FORMAT = "#.##";

    private final DecimalFormat decimalFormat;

    LocalizedJacksonModule(Version version, Locale locale) {
        super(MODULE_NAME, version);

        decimalFormat = new DecimalFormat(DECIMAL_FORMAT, new DecimalFormatSymbols(locale));
        decimalFormat.setParseBigDecimal(true);

        addDeserializer(BigDecimal.class, new LocalizedBigDecimalDeserializer());
        addSerializer(BigDecimal.class, new LocalizedBigDecimalSerializer());
    }

    private class LocalizedBigDecimalDeserializer extends StdDeserializer<BigDecimal> {

        LocalizedBigDecimalDeserializer() {
            super(BigDecimal.class);
        }

        @Override
        public BigDecimal deserialize(JsonParser parser, DeserializationContext context)
                throws IOException {
            ParsePosition parsePosition = new ParsePosition(0);
            Number result = decimalFormat.parse(parser.getText(), parsePosition);
            if (parsePosition.getErrorIndex() != -1 || parsePosition.getIndex() < parser.getText().length()) {
                return (BigDecimal) context.handleWeirdStringValue(_valueClass, parser.getText(),
                        "not a valid representation");
            } else {
                return (BigDecimal) result;
            }
        }
    }

    private class LocalizedBigDecimalSerializer extends StdSerializer<BigDecimal> {

        LocalizedBigDecimalSerializer() {
            super(BigDecimal.class);
        }

        @Override
        public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            gen.writeString(decimalFormat.format(value));
        }
    }
}
