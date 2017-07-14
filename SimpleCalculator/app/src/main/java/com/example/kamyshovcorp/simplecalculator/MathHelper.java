package com.example.kamyshovcorp.simplecalculator;

public class MathHelper {

    public static String executeOperation(double x, double y, String operation) {
        Double result = 0d;
        if ("+".equals(operation)) {
            result = x + y;
        } else if ("-".equals(operation)) {
            result = x - y;
        } else if ("*".equals(operation)) {
            result = x * y;
        } else if ("/".equals(operation)) {
            // На ноль делить нельзя
            if (Double.compare(0d, y) == 0) {
                result = 0d;
            } else {
                result = x / y;
            }
        }
        return isInteger(result) ? String.valueOf(result.intValue()) : String.valueOf(result);
    }

    public static boolean isInteger(double x) {
        // Если остаток по модулю 1 равен 0, то это целое число
        return Double.compare(0d, x % 1) == 0;
    }
}
