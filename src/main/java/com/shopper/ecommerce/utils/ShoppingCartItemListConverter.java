package com.shopper.ecommerce.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopper.ecommerce.models.ShoppingCartItem;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import java.util.List;

public class ShoppingCartItemListConverter implements AttributeConverter<List<ShoppingCartItem>, String> {

    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<ShoppingCartItem> attribute) {

        if(attribute != null){

            return objectMapper.writeValueAsString(attribute);

        }

        return null;
    }

    @SneakyThrows
    @Override
    public List<ShoppingCartItem> convertToEntityAttribute(String dbData) {

        if(dbData != null){

            return objectMapper.readValue(dbData, new TypeReference<List<ShoppingCartItem>>(){});

        }

        return null;
    }
}
