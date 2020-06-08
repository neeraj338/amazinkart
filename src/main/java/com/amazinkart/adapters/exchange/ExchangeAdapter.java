package com.amazinkart.adapters.exchange;

import com.amazinkart.exception.DownstreamException;
import com.amazinkart.exception.DownstreamTimeoutException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class ExchangeAdapter implements ExchangeApiPort {

  private final ExchangeApiClient exchangeApiClient;

  private Optional<ExchangeResponse> exchangeResponse;

  public Optional<ExchangeResponse> getRates() {
    if (!exchangeResponse.isPresent()) {
      try {
        Response<ExchangeResponse> execute = exchangeApiClient.getRates().execute();
        ExchangeResponse response = execute.body();
        exchangeResponse = Optional.ofNullable(response);
      } catch (SocketTimeoutException e) {
        log.error("Call to api timed out for {}", e);
        throw new DownstreamTimeoutException("timeout :: https://api.exchangeratesapi.io");
      } catch (IOException e) {
        log.error("IOException exception occurred {}", e);
        throw new DownstreamException(e.getMessage());
      }
    }
    return exchangeResponse;
  }

  @Override
  public double toINR(String currencyFrom, double amount) {
    if ("INR".equalsIgnoreCase(currencyFrom)) {
      return amount;
    }
    ExchangeResponse exchangeBaseRUR =
        this.getRates().orElseThrow(() -> new RuntimeException("Unable to fetch exchange rates."));

    return 1
        / exchangeBaseRUR.getExchangeRate(currencyFrom)
        * amount
        * exchangeBaseRUR.getINRExchangeRate();
  }
}
