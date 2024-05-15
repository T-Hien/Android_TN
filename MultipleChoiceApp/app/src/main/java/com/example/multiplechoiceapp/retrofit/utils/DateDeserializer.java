package com.example.multiplechoiceapp.retrofit.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer implements JsonDeserializer<Date> {

    private final SimpleDateFormat dateFormat;

    public DateDeserializer(String dateFormat) {
        this.dateFormat = new SimpleDateFormat(dateFormat);
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String dateStr = json.getAsJsonPrimitive().getAsString();
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new JsonParseException("Error parsing date: " + dateStr, e);
        }
    }
}

