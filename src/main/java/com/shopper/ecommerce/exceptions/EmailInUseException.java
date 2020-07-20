package com.shopper.ecommerce.exceptions;

import org.springframework.http.HttpStatus;

public class EmailInUseException extends ShopperException {
    public EmailInUseException(String errorMessage) {
        super(errorMessage);
    }

    public EmailInUseException(String errorMessage, String errorCode) {
        super(errorMessage, errorCode);
    }

    public EmailInUseException(String errorMessage, String errorCode, HttpStatus status) {
        super(errorMessage, errorCode, status);
    }

    public EmailInUseException(String errorMessage, String errorCode, int status) {
        super(errorMessage, errorCode, status);
    }
}
