package com.shopper.ecommerce.exceptions;

import org.springframework.http.HttpStatus;

public class AddressValidationException extends ShopperException {
    public AddressValidationException(String errorMessage) {
        super(errorMessage);
    }

    public AddressValidationException(final String errorMessage, final String errorCode) {
        super(errorMessage, errorCode);
    }

    public AddressValidationException(final String errorMessage, final String errorCode, final HttpStatus status) {
        super(errorMessage, errorCode, status);
    }

    public AddressValidationException(final String errorMessage, final String errorCode, final int status) {
        super(errorMessage, errorCode, status);
    }
}
