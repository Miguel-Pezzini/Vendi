package com.vendi.shared.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money {

    public static final int SCALE = 2;
    public static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    private Money() {
    }

    public static BigDecimal zero() {
        return BigDecimal.ZERO.setScale(SCALE, ROUNDING);
    }

    public static BigDecimal of(BigDecimal value) {
        if (value == null) {
            return zero();
        }
        return value.setScale(SCALE, ROUNDING);
    }

    public static BigDecimal of(double value) {
        return BigDecimal.valueOf(value).setScale(SCALE, ROUNDING);
    }

    public static BigDecimal of(String value) {
        return new BigDecimal(value).setScale(SCALE, ROUNDING);
    }

    public static BigDecimal multiply(BigDecimal unitPrice, int quantity) {
        return of(unitPrice).multiply(BigDecimal.valueOf(quantity)).setScale(SCALE, ROUNDING);
    }

    public static long toCents(BigDecimal amount) {
        return of(amount).movePointRight(SCALE).setScale(0, ROUNDING).longValueExact();
    }
}
