package com.shopper.ecommerce.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopper.ecommerce.models.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeConverter;

@Getter
@NoArgsConstructor
public class AddressConverter implements AttributeConverter<Address, String> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Address addressObject) {

        String addressJson = null;
        try {
            addressJson = objectMapper.writeValueAsString(addressObject);
        } catch (final JsonProcessingException e) {

        }

        return addressJson;
    }

    @Override
    public Address convertToEntityAttribute(String addressjson) {

        ObjectMapper objectMapper = new ObjectMapper();

        Address address = null;
        try {
            address = objectMapper.readValue(addressjson, Address.class);
        } catch (final Exception e) {

        }

        return address;
    }

}
