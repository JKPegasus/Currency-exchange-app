package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.*;

public class CurrencyDatabase {

    private static final ArrayList<ArrayList<String>> currencies_list = new ArrayList<>();
    private static final ArrayList<String> currency = new ArrayList<>();

    public static String baseRate;

    // Get the list of currencies from the website
    public static ArrayList<ArrayList<String>> getCurrencies() {
        try {
            String url = "https://www.jgk.cc/";
            Document doc = Jsoup.connect(url).get();

            // extract currency from the website
            Elements spans = doc.select("span");

            for (int i = 0; i < spans.size(); i++) {

                String text = spans.get(i).text();
                if ((i + 1) % 3 != 0 && (text.contains("/") || text.contains("."))) {
                    currency.add(text.replace("/CNY", ""));
                }

                if (currency.size() == 2) {

                    if (currency.get(0).equals("AUD")) {
                        baseRate = currency.get(1);
                    }

                    currencies_list.add(new ArrayList<>(currency));
                    currency.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currencies_list;
    }

    // covert into the correct currency based on AUD
    public static String correctedCurrencyRate(String currencyRate) {
        double rate = Double.parseDouble(currencyRate);
        double base = Double.parseDouble(baseRate);
        return String.format("%.3f", base / rate);
    }

    // initialize the correct currency in the database
    public static ArrayList<ArrayList<String>> initializeCurrencies() {
        ArrayList<ArrayList<String>> currencies = getCurrencies();
        for (ArrayList<String> currency : currencies) {

            if (currency.get(0).equals("AUD")) {
                currency.set(0, "CNY");
                currency.set(1, String.format("%.3f", Double.parseDouble(baseRate)));
                continue;
            }

            String newRate = correctedCurrencyRate(currency.get(1));
            currency.set(1, newRate);
        }

        // add AUD to the list
        currencies.add(0, new ArrayList<>(Arrays.asList("AUD", "1.000")));
        return currencies;
    }

    public static void main(String[] args) {
        System.out.println(initializeCurrencies());
    }
}
