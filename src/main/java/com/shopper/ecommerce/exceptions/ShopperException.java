package com.shopper.ecommerce.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public class ShopperException extends Exception {

    String errorMessage;

    String errorCode;

    HttpStatus status = HttpStatus.BAD_REQUEST;

    public ShopperException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = "0001";
    }

    public ShopperException(String errorMessage, String errorCode) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public ShopperException(String errorMessage, String errorCode, HttpStatus status) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.status = status;
    }

    public ShopperException(String errorMessage, String errorCode, int status) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.status = HttpStatus.valueOf(status);
    }

}
