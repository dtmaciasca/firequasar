package com.mercadolibre.firequasar.jpa.convert;

import javax.persistence.AttributeConverter;
import java.util.Arrays;

public class MessageConverter implements AttributeConverter<String[], String> {

     @Override
    public String convertToDatabaseColumn(String[] message) {
        return Arrays.toString(message)
                .replace("[[","[")
                .replace("]]","]");
    }

    @Override
    public String[] convertToEntityAttribute(String message) {
        return message.split(",");
    }

}
