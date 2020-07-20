package com.shopper.ecommerce.controllers;

import com.shopper.ecommerce.exceptions.AddressValidationException;
import com.shopper.ecommerce.models.Address;
import com.shopper.ecommerce.utils.NumberUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;

/**
 * Address Controller takes care of anything related to addresses across the API
 */
@Controller
public class AddressController {

    NumberUtil numberUtil = new NumberUtil();

    /**
     * This method validates addresses for any model (customer, order, etc)
     * @param address
     * @return
     */
    @SneakyThrows
    Address validate(Address address) {

        if(address == null)
            throw new AddressValidationException("Invalid address");

        if(address.getPostalCode() == null)
            throw new AddressValidationException("Invalid address postal code");

        String postalCode = numberUtil.replaceEverythingButNumbers(address.getPostalCode());

        if(postalCode.length() != 8)
            throw new AddressValidationException("Invalid address postal code length");

        // TODO: implement viacep.com.br validation for addresses

        if(address.getState() == null || address.getState().length() != 2)
            throw new AddressValidationException("Invalid address state code");

        if(address.getCity() == null || address.getState().length() < 2)
            throw new AddressValidationException("Invalid address state code");

        if(address.getDistrict() == null || address.getDistrict().length() < 2)
            throw new AddressValidationException("Invalid address district");

        if(address.getAddress() == null || address.getAddress().length() < 10)
            throw new AddressValidationException("Invalid address");

        if(address.getNumber() == null || address.getNumber().length() < 2)
            throw new AddressValidationException("Invalid address number");

        address.setPostalCode(postalCode);

        address.setState(address.getPostalCode().trim());

        address.setCity(address.getCity().trim());

        address.setDistrict(address.getDistrict().trim());

        address.setAddress(address.getAddress().trim());

        address.setNumber(address.getNumber().trim());

        if(address.getComplement() != null) {

            address.setComplement(address.getComplement());

        }
        return address;

    }

}
