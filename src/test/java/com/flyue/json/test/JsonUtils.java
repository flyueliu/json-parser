package com.flyue.json.test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Liu Yuefei
 * @created 2018-12-14 15:11
 */
public class JsonUtils {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String TIME_FORMAT = "HH:mm:ss";

    private JsonUtils() {
        throw new RuntimeException("can't new instance..");
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addSerializer(LocalTime.class, new LocalTimeSerializer());
        simpleModule.addSerializer(Date.class, new DateSerializer());
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        simpleModule.addDeserializer(Date.class, new DateDeserializer());
        simpleModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        mapper.registerModule(simpleModule);
    }

    public final static String pojoToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final static <T> T jsonToPojo(String json, Class<T> pojoClass) {
        try {
            return mapper.readValue(json, pojoClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            return mapper.readValue(jsonData, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static final class DateDeserializer extends JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String dateStr = p.getValueAsString();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
            try {
                return sdf.parse(dateStr);
            } catch (ParseException e) {
                return null;
            }

        }
    }

    private static final class DateSerializer extends JsonSerializer<Date> {
        @Override
        public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
            String dateStr = sdf.format(value);
            gen.writeString(dateStr);
        }
    }

    private static final class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String value = p.getValueAsString();
            return LocalDate.parse(value, DateTimeFormatter.ofPattern(DATE_FORMAT));
        }
    }

    private static final class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String date = p.getValueAsString();
            return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        }
    }

    private static final class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
        @Override
        public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String date = p.getValueAsString();
            return LocalTime.parse(date, DateTimeFormatter.ofPattern(TIME_FORMAT));
        }
    }

    private static final class LocalDateSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            int year = value.getYear();
            int month = value.getMonthValue();
            int day = value.getDayOfMonth();
            gen.writeString(year + "-" + month + "-" + day);
        }
    }

    private static final class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            String date = value.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
            gen.writeString(date);
        }
    }

    private static final class LocalTimeSerializer extends JsonSerializer<LocalTime> {
        @Override
        public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            int hour = value.getHour();
            int minute = value.getMinute();
            int second = value.getSecond();
            gen.writeString(hour + ":" + minute + ":" + second);
        }
    }


}
