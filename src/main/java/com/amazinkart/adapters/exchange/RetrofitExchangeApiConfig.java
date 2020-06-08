package com.amazinkart.adapters.exchange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitExchangeApiConfig {

  private final String baseUrl;

  public RetrofitExchangeApiConfig(@Value("${api.exchange-rates.base-url}") String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Bean
  public ExchangeApiClient getExchangeClient() {

    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    return retrofit.create(ExchangeApiClient.class);
  }
}
