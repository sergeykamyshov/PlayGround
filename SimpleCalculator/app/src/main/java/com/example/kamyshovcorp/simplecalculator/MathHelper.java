package com.example.kamyshovcorp.simplecalculator;

import java.math.BigDecimal;
import java.math.MathContext;

public class MathHelper {

    public static BigDecimal executeOperation(BigDecimal x, BigDecimal y, String operation) {
        BigDecimal result = BigDecimal.ZERO;
        if ("+".equals(operation)) {
            result = x.add(y);
        } else if ("-".equals(operation)) {
            result = x.subtract(y);
        } else if ("*".equals(operation)) {
            result = x.multiply(y);
        } else if ("/".equals(operation)) {
            // На ноль делить нельзя
            if (BigDecimal.ZERO.compareTo(y) != 0) {
                result = x.divide(y, MathContext.DECIMAL64);
            }
        }
        return result;
    }
}
