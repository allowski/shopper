package com.shopper.ecommerce.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberUtilTest {

    @Test
    void test_Should_Replace_Everything_But_Numbers() {

        NumberUtil numberUtil = new NumberUtil();

        assertEquals("", numberUtil.replaceEverythingButNumbers(null));
        assertEquals("123456789", numberUtil.replaceEverythingButNumbers(" 1.2.3.4.5.6.7.8.9    "));
        assertEquals("123456789", numberUtil.replaceEverythingButNumbers("$ad1sdsdsd2dsd3re45.6./7.89E "));
        assertEquals("123456789", numberUtil.replaceEverythingButNumbers("$ad1sdsdsd2dsd3re45.6./7.89e "));
        assertEquals("0123", numberUtil.replaceEverythingButNumbers(" A:0B:1_2=3"));

    }


}
