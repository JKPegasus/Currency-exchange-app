package org.example;

public class CurrencyConverter {

    public static String converter(String from, String to, String amount) {

        double fromRate = Double.parseDouble(QueryTool.currencyRateQuery(from));
        double toRate = Double.parseDouble(QueryTool.currencyRateQuery(to));
        double amountValue = Double.parseDouble(amount);

        double result = (amountValue / fromRate) * toRate;

        return String.format("%.3f", result);
    }
}
