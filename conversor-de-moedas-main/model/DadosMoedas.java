package model;

import java.util.Map;

public class DadosMoedas {
    String result;
    String documentation;
    String terms_of_use;
    long time_last_update_unix;
    String time_last_update_utc;
    long time_next_update_unix;
    String time_next_update_utc;
    String base_code;
    Map<String, Double> conversion_rates;

    public double convert(String fromCurrency, String toCurrency, double amount) {
        if (!conversion_rates.containsKey(fromCurrency) || !conversion_rates.containsKey(toCurrency)) {
            throw new IllegalArgumentException("Invalid currency code");
        }

        double fromRate = conversion_rates.get(fromCurrency);
        double toRate = conversion_rates.get(toCurrency);

        return amount / fromRate * toRate;
    }
}

