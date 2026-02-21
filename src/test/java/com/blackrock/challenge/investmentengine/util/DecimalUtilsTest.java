package com.blackrock.challenge.investmentengine.util;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class DecimalUtilsTest {

    @Test
    void testSafeDoubleConversion() {
        BigDecimal bd = DecimalUtils.bd(0.1);
        assertTrue(bd.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testEqualsWithEpsilon() {
        BigDecimal a = new BigDecimal("10.000000001");
        BigDecimal b = new BigDecimal("10.000000002");

        assertTrue(DecimalUtils.equals(a, b));
    }

    @Test
    void testPow() {
        BigDecimal base = DecimalUtils.bd("1.10");
        BigDecimal result = DecimalUtils.pow(base, 2);

        assertTrue(
                DecimalUtils.equals(
                        result,
                        DecimalUtils.bd("1.21")
                )
        );
    }

    @Test
    void testRound() {
        BigDecimal value = DecimalUtils.bd("10.126");
        BigDecimal rounded = DecimalUtils.round(value, 2);

        assertEquals(
                new BigDecimal("10.13"),
                rounded
        );
    }
}

