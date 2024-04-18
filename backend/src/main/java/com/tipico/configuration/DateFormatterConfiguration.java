package com.tipico.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Locale;

import static com.fasterxml.jackson.databind.DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS;
import static java.time.format.DateTimeFormatter.*;

@Configuration
@AllArgsConstructor
public class DateFormatterConfiguration implements WebMvcConfigurer {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.serializers(new LocalTimeSerializer(ISO_LOCAL_TIME));
            builder.serializers(new LocalDateSerializer(ISO_LOCAL_DATE));
            builder.serializers(new ZonedDateTimeSerializer(ISO_OFFSET_DATE_TIME));
            builder.serializers(NumberSerializer.bigDecimalAsStringSerializer());


            ObjectMapper m = new ObjectMapper();
            m = m.disable(USE_BIG_DECIMAL_FOR_FLOATS)
                    .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
                    .enable(USE_BIG_DECIMAL_FOR_FLOATS)
                    .setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));

            builder.configure(m);
            builder.deserializers(NumberDeserializers.BigDecimalDeserializer.instance);
        };
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalTime.class, localTimeFormatter());
        registry.addFormatterForFieldType(LocalDate.class, localDateFormatter());
        registry.addFormatterForFieldType(ZonedDateTime.class, zonedDateTimeFormatter());
    }

    private Formatter<LocalTime> localTimeFormatter() {
        var parser = (Parser<LocalTime>) (s, locale) -> LocalTime.parse(s, ISO_LOCAL_TIME);
        var printer = (Printer<LocalTime>) (localTime, locale) -> localTime.format(ISO_LOCAL_TIME);
        return new FormatterImpl<>(parser, printer);
    }

    private Formatter<LocalDate> localDateFormatter() {
        var parser = (Parser<LocalDate>) (s, locale) -> LocalDate.parse(s, ISO_LOCAL_DATE);
        var printer = (Printer<LocalDate>) (localDate, locale) -> localDate.format(ISO_LOCAL_DATE);
        return new FormatterImpl<>(parser, printer);
    }

    private Formatter<ZonedDateTime> zonedDateTimeFormatter() {
        var parser = (Parser<ZonedDateTime>) (s, locale) -> ZonedDateTime.parse(s, ISO_DATE_TIME);
        var printer = (Printer<ZonedDateTime>) (zonedDateTime, locale) -> zonedDateTime.format(ISO_DATE_TIME);
        return new FormatterImpl<>(parser, printer);
    }

    @Getter
    @RequiredArgsConstructor
    public static class FormatterImpl<T> implements Formatter<T> {

        private final Parser<T> parser;
        private final Printer<T> printer;

        @NonNull
        @Override
        public T parse(@NonNull String s, @NonNull Locale locale) throws ParseException {
            return parser.parse(s, locale);
        }

        @NonNull
        @Override
        public String print(@NonNull T t, @NonNull Locale locale) {
            return printer.print(t, locale);
        }
    }
}
