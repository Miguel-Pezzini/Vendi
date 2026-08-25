package com.vendi.unit.shared;

import com.vendi.shared.money.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {

    @Test
    void scalesAndConvertsToCents() {
        BigDecimal amount = Money.of("19.9");

        assertEquals(new BigDecimal("19.90"), amount);
        assertEquals(new BigDecimal("39.80"), Money.multiply(amount, 2));
        assertEquals(1990L, Money.toCents(amount));
    }
}
