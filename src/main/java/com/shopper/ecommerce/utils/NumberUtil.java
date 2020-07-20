package com.shopper.ecommerce.utils;

import org.springframework.stereotype.Component;

@Component
public class NumberUtil {

    public static final String NUMBERS_ONLY = "\\D";

    public String replaceEverythingButNumbers(String str){

        if(str == null) return "";

        return str.trim().replaceAll(NUMBERS_ONLY, "");

    }

}
