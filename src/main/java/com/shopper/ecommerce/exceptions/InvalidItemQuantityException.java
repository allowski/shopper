package com.shopper.ecommerce.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidItemQuantityException extends ShopperException {
    public InvalidItemQuantityException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidItemQuantityException(String errorMessage, String errorCode) {
        super(errorMessage, errorCode);
    }

    public InvalidItemQuantityException(String errorMessage, String errorCode, HttpStatus status) {
        super(errorMessage, errorCode, status);
    }

    public InvalidItemQuantityException(String errorMessage, String errorCode, int status) {
        super(errorMessage, errorCode, status);
    }
}
