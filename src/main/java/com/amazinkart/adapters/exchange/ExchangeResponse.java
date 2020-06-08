package com.amazinkart.adapters.exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExchangeResponse {

  private Map<String, Double> rates;
  private String base;
  private String date;

  public double getExchangeRate(String currency) {
    return rates.get(currency.toUpperCase());
  }

  public double getINRExchangeRate() {
    return rates.get("INR");
  }
}
