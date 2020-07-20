package com.shopper.ecommerce.exceptions;

import org.springframework.http.HttpStatus;

public class CheckoutException extends ShopperException {

    public CheckoutException(final String errorMessage) {
        super(errorMessage);
    }

    public CheckoutException(final String errorMessage, final String errorCode) {
        super(errorMessage, errorCode);
    }

    public CheckoutException(final String errorMessage, final String errorCode, final HttpStatus status) {
        super(errorMessage, errorCode, status);
    }

    public CheckoutException(final String errorMessage, final String errorCode, final int status) {
        super(errorMessage, errorCode, status);
    }
}
